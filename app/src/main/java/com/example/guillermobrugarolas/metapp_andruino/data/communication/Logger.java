package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.guillermobrugarolas.metapp_andruino.data.repository.Repository;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This class Logs the Messages sent and received by the application.
 * It listens to the Repository to log the actions.
 */
public class Logger implements Repository.RepositoryListener {
    /** Variable that stores the path to the file in which logs will be stored. */
    private static final String FILENAME = "commLogs.txt";

    /** Variable that stores the context in which the class is executed. */
    private Context context;

    /** Variable that stores the instance of Logger. There can only be one Logger. */
    @SuppressLint("StaticFieldLeak")
    private static Logger instance = null;

    /**
     * Private constructor to create a Logger instance.
     *
     * @param context The context in which the Logger runs.
     * @throws IOException If an I/O error occurred.
     */
    private Logger(Context context) throws IOException {
        this.context = context;
        File file = new File(context.getFilesDir(), FILENAME);
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();
    }

    /**
     * Static method to get the instance of the Logger. If no instance currently exists one is created.
     *
     * @param context The context in which the method is executed. This should be the Application's context.
     * @return The instance of Logger.
     * @throws IOException If and I/O error occurred.
     */
    public synchronized static Logger getInstance(Context context) throws IOException {
        if(instance == null){
            instance = new Logger(context);
        }
        return instance;
    }

    /**
     * Adds a log to the log file.
     *
     * @param message The message to be stored in the log file.
     */
    public void addLog(String message){
        String log = buildLog(getCurrentTime(), MessageSender.APP.name(), message);
        append(log);
    }

    /**
     * Calculates the current date and time.
     *
     * @return The current time.
     */
    private String getCurrentTime(){
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        return format.format(currentTime);
    }

    /**
     * Builds the log message to be stored in the log file.
     *
     * @param date The date the message was sent/received.
     * @param sender The name of the sender of the message.
     * @param message The message that was sent
     * @return The log formatted to be written to the log file.
     */
    private String buildLog(String date, String sender, String message){
        String[] messages = message.split(":");
        // FIXME If we want custom Integer values in the enum we will have to use this solution:
        // https://stackoverflow.com/questions/11047756/getting-enum-associated-with-int-value/11047810#11047810
        String messageType = MessageType.values()[Integer.valueOf(messages[0])].name();
        return date + ": " + sender + " - " + messageType + " -> " + messages[1];
    }

    /**
     * Appends a new log to the log file.
     *
     * @param log The message to be logged.
     */
    private void append(String log) {
        log = addNewLine(log);
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(FILENAME, Context.MODE_APPEND);
            outputStream.write(log.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new line to the end of a String if one is not present.
     *
     * @param str The string to which a new line should be added.
     * @return The input String with a new line appended.
     */
    private String addNewLine(String str) {
        if(!str.endsWith("\n")){
            str = str + "\n";
        }
        return str;
    }

    /**
     * Reads the log file and returns its contents.
     *
     * @return The contents of the log file, ie. The logs.
     * @throws IOException If an I/O error occurred.
     */
    public ArrayList<String> getLogs() throws IOException {
        return getLogs(0);
    }

    /**
     * Reads the log file and returns its contents ignoring the lines up until startingLine.
     *
     * @param startingLine The line from which to start adding logs to the output.
     * @return The logs written to the log file after startingLine.
     * @throws IOException If an I/O error occurred.
     */
    public ArrayList<String> getLogs(int startingLine) throws IOException {
        File file = new File(context.getFilesDir(), FILENAME);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        ArrayList<String> lines = new ArrayList<>();
        int index = 0;
        String line;
        try{
            while((line = in.readLine()) != null){
                if (index >= startingLine) {
                    lines.add(line);
                }
                index++;
            }
        } catch (EOFException ignored){
        }
        return lines;
    }

    /**
     * Removes the instance of the Logger.
     */
    public void close() {
        instance = null;
    }

    @Override
    public void onMessageReceived(String message) {
        String log = buildLog(getCurrentTime(), MessageSender.ROBOT.name(), message);
        append(log);
        Debug.showLog(message.split(";")[0]);
    }


    @Override
    public void onServiceStopped() {
        close();
    }
}
