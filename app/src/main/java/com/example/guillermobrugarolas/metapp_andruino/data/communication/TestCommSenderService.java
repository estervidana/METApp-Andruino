package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

//FIXME this service is only for testing purposes
public class TestCommSenderService extends IntentService {

    public TestCommSenderService() {
        super("TestCommSenderService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while(true) {
            try {
                UdpSender sender = new UdpSender(getApplicationContext());
                String message = "test message";
                String[] messages = {message};
                // TODO uncomment execute line when the UdpCommunicator can create a socket to send data.
                //sender.execute(messages);
                Log.d("sender", message);
                SystemClock.sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
            }
        }
    }
}
