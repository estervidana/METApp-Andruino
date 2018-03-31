package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.guillermobrugarolas.metapp_andruino.data.repository.Repository;



public class MainViewModel extends ViewModel implements Repository.RepositoryCallbacks {

    //our repository
    private Repository repository;

    //observable integer variable
    private MutableLiveData<Integer> elapsedSeconds;

    public MainViewModel(){
        repository = new Repository(this);
    }

    public LiveData<Integer> startCounting(Context context){
        if(elapsedSeconds == null){
            //init observable variable
            elapsedSeconds = new MutableLiveData<>();
        }
        //tell the repository to start the service
        repository.startService(context);
        //return the observable variable
        return elapsedSeconds;
    }



    /*-------------------------- Repository Callbacks -----------------------------*/
    @Override
    public void onSecondsUpdate(int seconds) {
        //post value to the view
        elapsedSeconds.postValue(seconds);
    }

    @Override
    public void onServiceStopped() {
        //post value to the view
        elapsedSeconds.postValue(-1);
    }
}