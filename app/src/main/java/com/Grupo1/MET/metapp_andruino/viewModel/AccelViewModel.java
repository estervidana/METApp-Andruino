package com.Grupo1.MET.metapp_andruino.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.Grupo1.MET.metapp_andruino.data.communication.MessageType;
import com.Grupo1.MET.metapp_andruino.data.repository.Repository;
import com.Grupo1.MET.metapp_andruino.debug.Debug;

/**
 * This is the viewModel of the accelerometer.
 */
public class AccelViewModel extends ViewModel implements Repository.RepositoryListener{

    //private static double xAccel; //Acceleration in x axis.
    //private static double yAccel; //Acceleration in y axis.
    //private static double zAccel; //Acceleration in z axis.
    private MutableLiveData<Double> xAccel, yAccel, zAccel;

    /**
     * The constructor of the class.
     */
    public AccelViewModel() {
    }

    /**
     * getter of the variable xAccel.
     * @return the value of xAccel.
     */
    public MutableLiveData<Double> getXAccel() {
        if (xAccel == null) {
            xAccel = new MutableLiveData<>();
        }
        return xAccel;
    }

    public MutableLiveData<Double> getYAccel() {
        if (yAccel == null) {
            yAccel = new MutableLiveData<>();
        }
        return yAccel;
    }

    public MutableLiveData<Double> getZAccel() {
        if (zAccel == null) {
            zAccel = new MutableLiveData<>();
        }
        return zAccel;
    }

    private void setXAccel(Double xAccelReceived) {
        xAccel.postValue(xAccelReceived);
    }
    private void setYAccel(Double yAccelReceived) {
        yAccel.postValue(yAccelReceived);
    }
    private void setZAccel(Double zAccelReceived) {
        zAccel.postValue(zAccelReceived);
    }


    @Override
    public void onMessageReceived(String message) {
        //When receiving a message, we need to process the string.
        //In this case only the accelerations are checked.
        String msg[] =message.split(";");
        String msgHeader[] =msg[0].split(":");
        Debug.showLog(msgHeader[0]);
        String msgParameters[] = msgHeader[1].split(",");
        Debug.showLog(String.valueOf(MessageType.ACCEL.ordinal()));
        if (Integer.parseInt(msgHeader[0]) == MessageType.ACCEL.ordinal()){
            if (msgParameters[0].equals("XACCEL")){
                xAccel.postValue(Double.parseDouble(msgParameters[1]));
            }else if (msgParameters[0].equals("YACCEL")){
                yAccel.postValue(Double.parseDouble(msgParameters[1]));
            }else if (msgParameters[0].equals("ZACCEL")) {
                zAccel.postValue(Double.parseDouble(msgParameters[1]));
            }
        }
    }

    @Override
    public void onServiceStopped() {

    }
}
