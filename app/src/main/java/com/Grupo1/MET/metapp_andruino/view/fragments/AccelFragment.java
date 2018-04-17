package com.Grupo1.MET.metapp_andruino.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.Grupo1.MET.metapp_andruino.R;
import com.Grupo1.MET.metapp_andruino.debug.Debug;
import com.Grupo1.MET.metapp_andruino.viewModel.AccelViewModel;

/**
 * This class represents the accelerometer fragment that displays its view items.
 */

public class AccelFragment extends Fragment {

    /** The view model that controls the accel. view */
    private AccelViewModel viewModel;
    /** The value of the accel. in X axis. */
    private  TextView xAccel;
    /** The value of the accel. in Y axis. */
    private  TextView yAccel;
    /** The value of the accel. in Z axis. */
    private  TextView zAccel;

    /**
     * Required empty constructor
     */
    public AccelFragment() {
    }

    public static AccelFragment newInstance() { return new AccelFragment();    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.showLog("Entering the accelerometer fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_accel, container, false);
        bindViews(v);
        initViewModel(v);
        return v;
    }

    /**
     * In this function we declare all the elements that we will change in this fragment
     * and all of its listeners.
     * @param v the view of the accelerometer.
     */
    public void bindViews(View v) {
        xAccel = v.findViewById(R.id.tvXAccel);
        yAccel = v.findViewById(R.id.tvYAccel);
        zAccel = v.findViewById(R.id.tvZAccel);
        //testing
        xAccel.setText(R.string.accel_x_accel_text);
        yAccel.setText(R.string.accel_y_accel_text);
        zAccel.setText(R.string.accel_z_accel_text);
        ImageButton ibBack = v.findViewById(R.id.image_button_back_accel);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    /**
     * This method updates the printed value of X axis acceleration.
     * @param xAccelValue The decimal value of X acceleration in m/s2.
     */
    public void updateXAccelText(double xAccelValue) {
        String text = R.string.accel_x_accel_text + String.valueOf(xAccelValue);
        xAccel.setText(text);
        viewModel.setxAccel(xAccelValue);
    }

    /**
     * This method updates the printed value of Y axis acceleration.
     * @param yAccelValue The decimal value of Y acceleration in m/s2.
     */
    public void updateYAccelText(double yAccelValue) {
        String text = R.string.accel_y_accel_text + String.valueOf(yAccelValue);
        yAccel.setText(text);
        viewModel.setyAccel(yAccelValue);
    }

    /**
     * This method updates the printed value of Z axis acceleration.
     * @param zAccelValue The decimal value of Z acceleration in m/s2.
     */
    public void updateZAccelText(double zAccelValue) {
        String text = R.string.accel_z_accel_text + String.valueOf(zAccelValue);
        zAccel.setText(text);
        viewModel.setzAccel(zAccelValue);
    }

    private void initViewModel(final View v){
        viewModel = ViewModelProviders.of(getActivity()).get(AccelViewModel.class);
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}