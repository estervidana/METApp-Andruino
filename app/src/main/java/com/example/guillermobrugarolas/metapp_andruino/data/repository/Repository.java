package com.example.guillermobrugarolas.metapp_andruino.data.repository;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.guillermobrugarolas.metapp_andruino.data.communication.CommunicationService;
import com.example.guillermobrugarolas.metapp_andruino.data.communication.MessageSender;

import java.util.ArrayList;
import java.util.List;

public class Repository implements CommunicationService.CommunicationServiceListener {
    private List<RepositoryListener> repositoryListeners;

    private boolean serviceIsBound;

    private static Repository instance = null;

    private Repository() {
        repositoryListeners = new ArrayList<>();
    }

    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }

    public void addListener(RepositoryListener listener){
        repositoryListeners.add(listener);
    }

    public void startService(Context context) {
        Intent intent = new Intent(context, CommunicationService.class);
        context.startService(intent);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void stopService(Context context) {
        if (serviceIsBound) {
            context.unbindService(serviceConnection);
            serviceIsBound = false;
        }
        Intent intent = new Intent(context, CommunicationService.class);
        context.stopService(intent);
        for(RepositoryListener listener: repositoryListeners){
            listener.onServiceStopped();
        }
        repositoryListeners.clear();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CommunicationService.CommunicationServiceBinder binder
                    = (CommunicationService.CommunicationServiceBinder) service;
            //set the interface to the service so we can listen to the service callbacks
            binder.setListener(Repository.this);
            serviceIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceIsBound = false;
        }
    };

    @Override
    public void onServiceStopped() {
        for(RepositoryListener listener: repositoryListeners){
            listener.onServiceStopped();
        }
    }

    @Override
    public void onMessageReceived(String message) {
        for(RepositoryListener listener: repositoryListeners){
            listener.onMessageReceived((MessageSender.ROBOT.name()) + message);
        }
    }

    public interface RepositoryListener {
        void onMessageReceived(String message);
        void onServiceStopped();
    }
}
