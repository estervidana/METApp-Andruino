package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class CommunicationService extends IntentService {

    private boolean running = true;
    private Communicator communicator;

    private CommunicationServiceListener listener;

    public CommunicationService() {
        super("CommService");
    }

    public CommunicationService(String name) {
        super(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CommunicationServiceBinder();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while(running){
            try {
                String message = UdpCommunicator.getInstance().receive();
                listener.onMessageReceived(message);
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId){
        super.onStartCommand(intent, flags, startId);
        running = true;
        return Service.START_NOT_STICKY;
    }

    private void createCommunicator() {
        try {
            communicator = UdpCommunicator.getInstance();
        } catch (SocketException e) {
            running = false;
            //listener.onServiceStopped();
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        running = false;
        communicator.close();
        super.onDestroy();
    }

    public class CommunicationServiceBinder extends Binder {
        public void setListener(CommunicationServiceListener listener){
            CommunicationService.this.listener = listener;
        }
    }

    public interface CommunicationServiceListener {
        void onServiceStopped();
        void onMessageReceived(String message);
    }
}
