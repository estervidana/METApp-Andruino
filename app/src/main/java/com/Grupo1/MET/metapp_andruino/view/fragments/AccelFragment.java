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

    public void bindViews(View v) {
        xAccel = v.findViewById(R.id.tvXAccel);
        yAccel = v.findViewById(R.id.tvYAccel);
        zAccel = v.findViewById(R.id.tvZAccel);
        //testing
        xAccel.setText("X accel.: 3.44 m/s2");
        yAccel.setText("Y accel.: 5.09 m/s2");
        zAccel.setText("Z accel.: -9.81 m/s2");
        ImageButton ibBack = v.findViewById(R.id.image_button_back_accel);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public void updateXAccelText(double xAccelValue) {
        String text = R.string.accel_x_accel_text + String.valueOf(xAccelValue);
        xAccel.setText(text);
        viewModel.setxAccel(xAccelValue);
    }

    public void updateYAccelText(double yAccelValue) {
        String text = R.string.accel_y_accel_text + String.valueOf(yAccelValue);
        yAccel.setText(text);
        viewModel.setyAccel(yAccelValue);
    }

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