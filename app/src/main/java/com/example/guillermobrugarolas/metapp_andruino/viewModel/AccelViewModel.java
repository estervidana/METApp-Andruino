package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.arch.lifecycle.ViewModel;

public class AccelViewModel extends ViewModel {
    private static double xAccel;
    private static double yAccel;
    private static double zAccel;
    private static double tilt;

    public static double getTilt() {
        return tilt;
    }

    public static void setTilt(double tilt) {
        AccelViewModel.tilt = tilt;
    }

    public AccelViewModel() {
    }

    public double getxAccel() {
        return xAccel;
    }

    public void setxAccel(double xAccel) {
        AccelViewModel.xAccel = xAccel;
    }

    public double getyAccel() {
        return yAccel;
    }

    public void setyAccel(double yAccel) {
        AccelViewModel.yAccel = yAccel;
    }

    public double getzAccel() {
        return zAccel;
    }

    public void setzAccel(double zAccel) {
        AccelViewModel.zAccel = zAccel;
    }
}
