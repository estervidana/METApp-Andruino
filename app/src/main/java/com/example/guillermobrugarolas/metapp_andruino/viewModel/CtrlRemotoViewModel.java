package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.gesture.Prediction;

import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;

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
    private int yPosition,oldYPosition;

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
    public void setYRotation(float y){

        if (y >= 6){
            yPosition = 3;
            Debug.showLogError("Turn right heavy");
        }else if (y<6 && y>=3){
            yPosition = 2;
            Debug.showLogError("Turn right moderate");

        }else if (y<3 && y>=1) {
            yPosition = 1;
            Debug.showLogError("Turn right a little bit");
        }else if(y<1 && y>=-1){
            yPosition = 0;
            //The robot must move straight.
        }else if (y<0 && y>=-3){
            yPosition = -1;
            Debug.showLogError("Turn left a little bit");
        }else if (y<-3 && y>=-6){
            yPosition = -2;
            Debug.showLogError("Turn left moderate");
        }else{
            yPosition = -3;
            Debug.showLogError("Turn left heavy");
        }
        if (oldYPosition != yPosition){
            Debug.showLogError("Changed status!");
        }
        oldYPosition = yPosition;
    }

    public void sendPolygonOrder (Prediction prediction) {
        if (prediction.name.equals("Circle")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a CIRCLE 40 cm RADIUS!");
        } else if (prediction.name.equals("Triangle")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a TRIANGLE 40 cm RADIUS!");
        } else if (prediction.name.equals("Square")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a SQUARE 40 cm RADIUS!");
        }
    }
}
