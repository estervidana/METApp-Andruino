package com.Grupo1.MET.metapp_andruino.data.repository;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.Grupo1.MET.metapp_andruino.data.communication.CommunicationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Redundant class that listens on the Service and has listeners.
 * Instead of having the following flow Service -> Listeners now we have
 * Service -> Listener (Repository) -> Listener of Listeners
 */
public class Repository implements CommunicationService.CommunicationServiceListener {
    /** List of the repository's listeners.*/
    private List<RepositoryListener> repositoryListeners;
    /** Indicates if the {@link CommunicationService} has been bounded to. */
    private boolean serviceIsBound;

    /** Instance of the {@link Repository}. Only one {@link Repository} can be active at a time.*/
    private static Repository instance = null;

    /**
     * Private constructor.
     */
    private Repository() {
        repositoryListeners = new ArrayList<>();
    }

    /**
     * Static method to get the instance of the Repository. If no instance currently exists one is created.
     *
     * @return The instance of the {@link Repository}.
     */
    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }

    /**
     * Adds a listener to the class. Listeners will be a alerted when an action occurs.
     *
     * @param listener The listener to be added.
     */
    public void addListener(RepositoryListener listener){
        repositoryListeners.add(listener);
    }

    /**
     * Starts the {@link CommunicationService}.
     *
     * @param context The context in which the Service will run.
     */
    public void startService(Context context) {
        Intent intent = new Intent(context, CommunicationService.class);
        context.startService(intent);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Stops the {@link CommunicationService}.
     *
     * @param context The context in which the Service is running.
     */
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
            listener.onMessageReceived(message);
        }
    }

    public interface RepositoryListener {
        void onMessageReceived(String message);
        void onServiceStopped();
    }
}
