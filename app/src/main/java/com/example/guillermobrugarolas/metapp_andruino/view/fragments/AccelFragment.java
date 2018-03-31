package com.example.guillermobrugarolas.metapp_andruino.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.MainActivity;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.AccelViewModel;

public class AccelFragment extends Fragment {

    private AccelViewModel viewModel;
    private  TextView xAccel;
    private  TextView yAccel;
    private  TextView zAccel;


    public AccelFragment() {
        // Required empty public constructor
    }

    public static AccelFragment newInstance() { return new AccelFragment();    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.showLogError("Entering the accelerometer fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_accel, container, false);
        bindViews(v);
        initViewModel(v);
        return v;
    }

    public void bindViews(View v) {
        xAccel = v.findViewById(R.id.tvXAccel);
        yAccel = v.findViewById(R.id.tvYAccel);
        zAccel = v.findViewById(R.id.tvZAccel);
        ImageButton ibBack = v.findViewById(R.id.image_button_back_accel);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public void updateAccelValues(double [] accelValues) {
        xAccel.setText((Html.fromHtml("X accel. (m/s<sup>2</sup>): ") + (String.valueOf(accelValues[0]))));
        viewModel.setxAccel(accelValues[0]);
        yAccel.setText((Html.fromHtml("Y accel. (m/s<sup>2</sup>): ") + (String.valueOf(accelValues[1]))));
        viewModel.setyAccel(accelValues[1]);
        zAccel.setText((Html.fromHtml("Z accel. (m/s<sup>2</sup>): ") + (String.valueOf(accelValues[2]))));
        viewModel.setzAccel(accelValues[2]);
    }


    private void initViewModel(final View v){
        viewModel = ViewModelProviders.of(getActivity()).get(AccelViewModel.class);
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
