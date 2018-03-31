package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.arch.lifecycle.ViewModel;

public class AccelViewModel extends ViewModel {
    private static double xAccel;
    private static double yAccel;
    private static double zAccel;

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
