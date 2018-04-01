package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Logger {

    public enum MessageSender{
        ROBOT, APP
    }

    public enum MessageType{
        /*sent by Robot*/TEMP, BUMP, SPEED, WALL, POS,
        /*sent by App*/MODE, TURN, MOVE, STOP, FIGURE,
        /*sent by both*/LEDS, CTRL_MODE
    }

    private static Logger instance = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;

    private Logger(Context context) throws IOException {
        File file = new File(context.getFilesDir(), "commLogs.txt");
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();
        out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    public synchronized static Logger getInstance(Context context) throws IOException {
        if(instance == null){
            instance = new Logger(context);
        }
        return instance;
    }

    // It is important that any Object passed to this function has a toString implementation that is human readable.
    public void addLog(MessageSender sender, MessageType type, Object... args){
        String log = buildLog(getCurrentTime(), sender.name(), type.name(), args);
        append(log);
    }

    private String getCurrentTime(){
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        return format.format(currentTime);
    }

    private String buildLog(String date, String sender, String type, Object... args){
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
        out.println(log);
    }

    public ArrayList<String> getLogs() throws IOException {
        return getLogs(0);
    }

    public ArrayList<String> getLogs(int startingLine) throws IOException {
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

    public static void close() throws IOException {
        if(instance != null){
            out.close();
            out = null;
            instance = null;
        }
    }
}
