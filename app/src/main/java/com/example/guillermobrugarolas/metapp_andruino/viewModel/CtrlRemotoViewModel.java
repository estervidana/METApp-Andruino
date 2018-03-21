package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

public class CtrlRemotoViewModel extends ViewModel{
    public CtrlRemotoViewModel(){

    }

    private int gear = 0;
    private final int MAXGEAR = 3;
    private final int MINGEAR = -3;

    private boolean gas = false;
    private boolean brake = false;
    private MutableLiveData<Integer> temperature;
    private MutableLiveData<Integer> speed;

    public MutableLiveData<Integer> getTemperature() {
        if (temperature == null) {
            temperature = new MutableLiveData<Integer>();
        }
        return temperature;
    }
    public MutableLiveData<Integer> getSpeed() {
        if (speed == null) {
            speed = new MutableLiveData<Integer>();
        }
        return speed;
    }

    public void setGas(boolean gas) {
        this.gas = gas;
    }

    public void setBrake(boolean brake) {
        this.brake = brake;
    }


    public void setTemperature(Integer temp) {
        temperature.postValue(temp);
    }

    public void setSpeed(Integer spd) {
        speed.postValue(spd);
    }

    public int incrementGear(){
        if (gear != MAXGEAR){
            gear++;
            //AUX! MUST BE REMOVED!!!!
            setSpeed(gear);
            //AUX!!! MUST BE REMOVED!!!
            setTemperature(gear);
        }
        return gear;
    }
    public int decrementGear(){
        if (gear != MINGEAR){
            gear--;

        }
        return gear;
    }
}
