package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.guillermobrugarolas.metapp_andruino.data.repository.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Logger implements Repository.RepositoryListener {
    private static final String FILENAME = "commLogs.txt";

    private Context context;

    @SuppressLint("StaticFieldLeak")
    private static Logger instance = null;

    private Logger(Context context) throws IOException {
        this.context = context;
        File file = new File(context.getFilesDir(), FILENAME);
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();
    }

    public synchronized static Logger getInstance(Context context) throws IOException {
        if(instance == null){
            instance = new Logger(context);
        }
        return instance;
    }

    // It is important that any Object passed to this function has a toString implementation that is human readable.
    public void addLog(String log){
        log = getCurrentTime() + "->" + log;
        append(log);
    }

    private String getCurrentTime(){
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        return format.format(currentTime);
    }

    public String buildLog(String date, String sender, String type, Object... args){
        StringBuilder builder = new StringBuilder();
        builder.append(date);
        builder.append(": ");
        builder.append(sender);
        builder.append('-');
        builder.append(type);
        builder.append("->");
        for (Object obj :args) {
            builder.append(obj.toString());
        }
        return builder.toString();
    }

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

    private String addNewLine(String log) {
        if(!log.endsWith("\n")){
            log = log + "\n";
        }
        return log;
    }

    public ArrayList<String> getLogs() throws IOException {
        return getLogs(0);
    }

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

    public void close() throws IOException {
        instance = null;
    }

    @Override
    public void onMessageReceived(String message) {
        message = message.split(";")[0];
        //TODO do this properly.
        addLog(MessageSender.ROBOT.name() + ": " + message);
        Log.d("Logger", message);
    }

    @Override
    public void onServiceStopped() {
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
