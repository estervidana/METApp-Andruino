package com.Grupo1.MET.metapp_andruino.data.communication;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;

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
                String message = "3:5,20;";
                String[] messages = {message};
                sender.execute(messages);
                SystemClock.sleep(10000);
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(10000);
            }
        }
    }
}
