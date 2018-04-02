package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UdpSender extends AsyncTask<String, Void, List<String>> {

    private Communicator communicator;
    private Logger logger;

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
            logger.addLog((MessageSender.APP.name()) + message);
        }
    }
}
