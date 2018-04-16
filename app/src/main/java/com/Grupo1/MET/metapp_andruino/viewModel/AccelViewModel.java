package com.Grupo1.MET.metapp_andruino.viewModel;

import android.arch.lifecycle.ViewModel;

/**
 * This is the viewModel of the accelerometer.
 */
public class AccelViewModel extends ViewModel {

    private static double xAccel; //Acceleration in x axis.
    private static double yAccel; //Acceleration in y axis.
    private static double zAccel; //Acceleration in z axis.

    /**
     * The constructor of the class.
     */
    public AccelViewModel() {
    }

    /**
     * getter of the variable xAccel.
     * @return the value of xAccel.
     */
    public double getxAccel() {
        return xAccel;
    }

    /**
     * setter of the value xAccel.
     * @param xAccel is the acceleration in the x axis.
     */
    public void setxAccel(double xAccel) {
        AccelViewModel.xAccel = xAccel;
    }

    /**
     * getter of the variable yAccel.
     * @return the value of yAccel.
     */
    public double getyAccel() {
        return yAccel;
    }

    /**
     * setter of the variable yAccel.
     * @param yAccel is the acceleration in the y axis.
     */
    public void setyAccel(double yAccel) {
        AccelViewModel.yAccel = yAccel;
    }

    /**
     * getter of the variable zAccel.
     * @return the value of zAccel.
     */
    public double getzAccel() {
        return zAccel;
    }

    /**
     * setter of the variable zAccel.
     * @param zAccel is the acceleration in the z axis.
     */
    public void setzAccel(double zAccel) {
        AccelViewModel.zAccel = zAccel;
    }
}
