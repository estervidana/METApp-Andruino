package com.Grupo1.MET.metapp_andruino.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.gesture.Prediction;

import com.Grupo1.MET.metapp_andruino.data.communication.MessageType;
import com.Grupo1.MET.metapp_andruino.data.repository.Repository;
import com.Grupo1.MET.metapp_andruino.debug.Debug;

/**
 * This class is a ViewModel for the Remote control screen of the robot.
 */
public class CtrlRemotoViewModel extends ViewModel implements Repository.RepositoryListener{
    /**
     * This method is the contructor of the class.
     */
    public CtrlRemotoViewModel(){
        Repository.getInstance().addListener(this);
    }
    private int gear = 0;
    private final int MAXGEAR = 3;
    private final int MINGEAR = -3;

    private boolean gas = false;
    private boolean brake = false;
    private MutableLiveData<Integer> temperature,speed, collisionFront, collisionLeft, collisionRight;
    private int yPosition,oldYPosition;
    long tOld = System.currentTimeMillis();


    /**
     * This method returns the value of the temperature, which is observed by the view.
     * @return the value of the temperature of the robot.
     */
    public MutableLiveData<Integer> getTemperature() {
        if (temperature == null) {
            temperature = new MutableLiveData<>();
        }
        return temperature;
    }

    /**
     * This method returns the value of the speed, which is observed by the view.
     * @return the value of the speed of the robot.
     */
    public MutableLiveData<Integer> getSpeed() {
        if (speed == null) {
            speed = new MutableLiveData<>();
        }
        return speed;
    }
    public MutableLiveData<Integer> getCollisionFront() {
        if (collisionFront == null) {
            collisionFront = new MutableLiveData<>();
        }
        return collisionFront;
    }
    public MutableLiveData<Integer> getCollisionLeft() {
        if (collisionLeft == null) {
            collisionLeft = new MutableLiveData<>();
        }
        return collisionLeft;
    }
    public MutableLiveData<Integer> getCollisionRight() {
        if (collisionRight == null) {
            collisionRight = new MutableLiveData<>();
        }
        return collisionRight;
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
        }else if (y<6 && y>=3){
            yPosition = 2;

        }else if (y<3 && y>=1) {
            yPosition = 1;
        }else if(y<1 && y>=-1){
            yPosition = 0;
            //The robot must move straight.
        }else if (y<0 && y>=-3){
            yPosition = -1;
        }else if (y<-3 && y>=-6){
            yPosition = -2;
        }else{
            yPosition = -3;
        }
        if (oldYPosition != yPosition && System.currentTimeMillis() - tOld > 500){
            tOld = System.currentTimeMillis();
            switch (yPosition){
                case 3:
                    Debug.showLog("Turn right heavy");
                    break;
                case 2:
                    Debug.showLog("Turn right moderate");
                    break;
                case 1:
                    Debug.showLog("Turn right a little bit");
                    break;
                case 0:
                    Debug.showLog("Don't move");
                    break;
                case -1:
                    Debug.showLog("Turn left a little bit");
                    break;
                case -2:
                    Debug.showLog("Turn left moderate");
                    break;
                case -3:
                    Debug.showLog("Turn left heavy");
                    break;
            }
        }
        oldYPosition = yPosition;
    }

    @Override
    public void onMessageReceived(String message) {
        String msg[] =message.split(";");
        String msgHeader[] =msg[0].split(":");
        Debug.showLog(msgHeader[0]);
        String msgParameters[] = msgHeader[1].split(",");
        Debug.showLog(String.valueOf(MessageType.COLLISION.ordinal()));
        if (Integer.parseInt(msgHeader[0]) == MessageType.COLLISION.ordinal()){
            Debug.showLog("entro");
            if (msgParameters[0].equals("FRONT")){
                if (msgParameters[1].equals("ON")) {
                    Debug.showLog("kjgskdfg");
                    collisionFront.postValue(1);
                }else collisionFront.postValue(0);
            }else if (msgParameters[0].equals("LEFT")){
                if (msgParameters[1].equals("ON")) {
                    collisionLeft.postValue(1);
                }else collisionLeft.postValue(0);

            }else if (msgParameters[0].equals("RIGHT")) {
                if (msgParameters[1].equals("ON")) {
                    collisionRight.postValue(1);
                } else collisionRight.postValue(0);
            }
        }

    }

    @Override
    public void onServiceStopped() {
    }

    public void sendPolygonOrder (Prediction prediction) {
        if (prediction.name.equals("Circle")) {
            Debug.showLog("::::::::::::::::::: Arduino, Do a CIRCLE 40 cm RADIUS!");
        } else if (prediction.name.equals("Triangle")) {
            Debug.showLog("::::::::::::::::::: Arduino, Do a TRIANGLE 40 cm RADIUS!");
        } else if (prediction.name.equals("Square")) {
            Debug.showLog("::::::::::::::::::: Arduino, Do a SQUARE 40 cm RADIUS!");
        }
    }
}
