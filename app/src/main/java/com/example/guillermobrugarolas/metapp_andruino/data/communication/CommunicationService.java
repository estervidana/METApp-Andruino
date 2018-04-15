package com.example.guillermobrugarolas.metapp_andruino.data.communication;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Service used to receive UDP packets.
 */
public class CommunicationService extends IntentService {

    /** Used to determine if the Service should be running or not. Once it is set to False it can not be set to True again. */
    private boolean running = true;
    /** Interface used to receive messages. */
    private Communicator communicator;

    /** Listener to alert when an action occurs. */
    private CommunicationServiceListener listener;

    /** Empty constructor. */
    public CommunicationService() {
        super("CommService");
    }

    /** Constructor that overrides super. */
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
                // used to avoid overflowing Logcat
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

    /**
     * Interface that must be implemented by the classes which want to listen to the actions done
     * by the CommunicationService.
     */
    public interface CommunicationServiceListener {
        /**
         * Called when the Service stops.
         */
        void onServiceStopped();

        /**
         * Called when the Service receives a message.
         * @param message The message received.
         */
        void onMessageReceived(String message);
    }
}
