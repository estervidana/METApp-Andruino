package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.gesture.Prediction;

import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;

/**
 * This class is a ViewModel for the Remote control screen of the robot.
 */
public class CtrlRemotoViewModel extends ViewModel{
    /**
     * This method is the contructor of the class.
     */
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

    /**
     * This method returns the value of the temperature, which is observed by the view.
     * @return the value of the temperature of the robot.
     */
    public MutableLiveData<Integer> getTemperature() {
        if (temperature == null) {
            temperature = new MutableLiveData<Integer>();
        }
        return temperature;
    }

    /**
     * This method returns the value of the speed, which is observed by the view.
     * @return the value of the speed of the robot.
     */
    public MutableLiveData<Integer> getSpeed() {
        if (speed == null) {
            speed = new MutableLiveData<Integer>();
        }
        return speed;
    }

    /**
     * this method sets gas to true or false given the input gas.
     * @param gas can be true or false (true if the gas button it's pressed, false if it is not pressed).
     */
    public void setGas(boolean gas) {
        this.gas = gas;
    }
    /**
     * this method sets brake to true or false given value of the input brake.
     * @param brake can be true or false (true if the brake button it's pressed, false if it is not pressed).
     */
    public void setBrake(boolean brake) {
        this.brake = brake;
    }

    /**
     * This method sets the value of the temperature of the robot, a variable observed by the view of the remote control.
     * @param temp is the temperature that must be stored.
     */
    private void setTemperature(Integer temp) {
        temperature.postValue(temp);
    }
    /**
     * This method sets the value of the speed of the robot, a variable observed by the view of the remote control..
     * @param spd is the speed that must be stored.
     */
    private void setSpeed(Integer spd) {
        speed.postValue(spd);
    }
    /**
     * This function increments de private variable variable gear and returns it.
     * @return the variable gear, which indicates the gear of the robot (speed).
     */
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

    /**
     * This function decrements de private variable variable gear and returns it.
     * @return the variable gear, which indicates the gear of the robot (speed).
     */
    public int decrementGear(){
        if (gear != MINGEAR){
            gear--;

        }
        return gear;
    }

    /**
     *This method calculates the rotation degree of the robot given a rotation value of the android device.
     * The value is stored in the private variable yPosition of this class.
     * The range of values of YPosition is between [-3,3] with an increment of one unit.
     * @param y must be a a float determining the rotation value of the mobile device.
     */
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
        if (prediction.name.equals("CircleRadius10cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a CIRCLE 10 cm RADIUS!");
        } else if (prediction.name.equals("CircleRadius20cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a CIRCLE 20 cm RADIUS!");
        } else if (prediction.name.equals("CircleRadius20cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a CIRCLE 30 cm RADIUS!");
        } else if (prediction.name.equals("CircleRadius20cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a CIRCLE 40 cm RADIUS!");
        } else if (prediction.name.equals("SquareSide20cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a SQUARE 20 cm SIDE!");
        } else if (prediction.name.equals("SquareSide40cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a SQUARE 40 cm SIDE!");
        } else if (prediction.name.equals("SquareSide60cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a SQUARE 60 cm SIDE!");
        } else if (prediction.name.equals("SquareSide80cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a SQUARE 80 cm SIDE!");
        } else if (prediction.name.equals("TriangleSide20cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a TRIANGLE 20 cm SIDE!");
        } else if (prediction.name.equals("TriangleSide40cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a TRIANGLE 40 cm SIDE!");
        } else if (prediction.name.equals("TriangleSide60cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a TRIANGLE 60 cm SIDE!");
        } else if (prediction.name.equals("TriangleSide80cm")) {
            Debug.showLogError("::::::::::::::::::: Arduino, Do a TRIANGLE 80 cm SIDE!");
        }
    }
}
