package com.Grupo1.MET.metapp_andruino.data.communication;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This class sends Udp messages asynchronously.
 */
public class UdpSender extends AsyncTask<String, Void, List<String>> {

    /** The communicator used to send the messages.*/
    private Communicator communicator;
    /** The Logger used to log the sent messages.*/
    private Logger logger;

    /**
     * Constructor.
     *
     * @param context The context in which tehe Sender runs.
     * @throws IOException If an I/O error occurred.
     */
    public UdpSender(Context context) throws IOException {
        this.communicator = UdpCommunicator.getInstance();
        this.logger = Logger.getInstance(context);
    }

    @Override
    protected List<String> doInBackground(String... messages) {
        for(String message: messages){
            try {
                communicator.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Arrays.asList(messages);
    }

    @Override
    protected void onPostExecute(List<String> messages) {
        // super.onPostExecute(s);
        for(String message: messages){
            logger.addLog(message);
        }
    }
}
