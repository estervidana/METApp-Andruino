package com.example.guillermobrugarolas.metapp_andruino.view.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.CtrlRemotoActivity;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.LabActivity;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.MainActivity;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.CtrlRemotoViewModel;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.PaintView;

import java.util.ArrayList;


public class CtrlRemotoFragment extends Fragment implements SensorEventListener {
    private CtrlRemotoViewModel viewModel;
    private  ProgressBar pbSpeed, pbTemperature;
    private  TextView tvFrontalCollision, tvBackLeftCollision, tvBackRightCollision;
    private GestureOverlayView govGestures;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private GestureLibrary gLibrary;



    public static CtrlRemotoFragment newInstance() {
        return new CtrlRemotoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.showLogError("Entering the remote control fragment");

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_ctrl_remoto,container,false);
        initViewModel(v);
        bindViews(v);
        return v;
    }
    @SuppressLint("ClickableViewAccessibility")
    private void bindViews(View v){
        //THE CIRCULAR PROGRESS BAR FOR THE SPEED.
        pbSpeed = v.findViewById(R.id.progressbar_circular_progress);
        //THE TEMPERATURE PROGRESS BAR
        pbTemperature = v.findViewById(R.id.progressbar_temperature);
        //THE COLLISION INDICATORS
        tvBackLeftCollision = v.findViewById(R.id.text_back_left_collision);
        tvFrontalCollision = v.findViewById(R.id.text_frontal_collision);
        tvBackRightCollision = v.findViewById(R.id.text_back_right_collision);
        //THE GESTURES VIEW
        govGestures = v.findViewById(R.id.gestures);
        govGestures.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = gLibrary.recognize(gesture);
                Prediction prediction = (predictions.size() != 0) ? predictions.get(0) : null;
                prediction = (prediction.score >= 1.0) ? prediction: null;
                if (prediction != null) {
                    // do something with the prediction
                    //Toast.makeText(this, prediction.name + "(" + prediction.score + ")", Toast.LENGTH_LONG).show();
                    Debug.showLogError("::::::::::::::::::: Gesture recognised: " + prediction.name);
                    //send order to Arduino depending on the recognised gesture type
                    sendPolygonOrder(prediction);
                }
            }
        });
        //DisplayMetrics metrics = new DisplayMetrics();
        //this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //pvDrawShape.init(metrics);
        gLibrary = GestureLibraries.fromRawResource(getActivity(), R.raw.gestures);
        if (!gLibrary.load()) {
            this.getActivity().finish();
        }
        listenLightsSwitch(v);
        listenManualModeSwitch(v);
        listenButtonsGear(v);
        listenCanvas(v);
        listenButtonsGasBrakeClear(v);
        observeTemperature(v);
        observeSpeed(v);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener((SensorEventListener) this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        //From this list we will look for the Y value, as it shows the
        //rotation of the device when it is placed landscape.
        //Positive values indicate right rotation of the robot.
        //Negative values indicate left rotation of the robot.
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        viewModel.setYRotation(y);

    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    private void listenLightsSwitch(final View v){
        //LISTENING THE LIGHT'S SWITCH
        final Switch sLigths = v.findViewById(R.id.switch_mode_lights);
        sLigths.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled.
                    //Now we are setting it to "true", but we will need to wait until the response of the arduino.
                    sLigths.setChecked(true);
                } else {
                    // The toggle is disabled
                }
            }
        });
    }
    private void listenManualModeSwitch(final View v){
        //LISTENING THE MANUAL MODE SWITCH
        final Switch sManualMode = v.findViewById(R.id.switch_manual_mode);
        sManualMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    pbSpeed.setProgress(3);
                } else {
                    // The toggle is disabled
                    pbSpeed.setProgress(10);

                }
            }
        });

    }
    private void listenButtonsGear(final View v){
        //LISTENING TO GEAR BUTTONS
        final TextView tvGearText = v.findViewById(R.id.text_number_gear);
        final ImageButton ibGearUp = v.findViewById(R.id.image_button_gearup);
        ibGearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGearText.setText(String.valueOf(viewModel.incrementGear()));
                Debug.showLogError("Gear Up pressed");
            }
        });
        final ImageButton ibGearDown = v.findViewById(R.id.image_button_geardown);
        ibGearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGearText.setText(String.valueOf(viewModel.decrementGear()));
                Debug.showLogError("Gear Down pressed");
            }
        });

    }
    private void listenCanvas(final View v){
        //LISTENING TO THE CANVAS OF THE SCREEN
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //In order to adapt to different mobile resolutions we will check tye size of the lateral
                //Layouts and let the user paint only between them.
                LinearLayout llLateralLeft = v.findViewById(R.id.linearLayout_lateralLeft);
                LinearLayout llLateralRight = v.findViewById(R.id.linearLayout_lateralRight);
                LinearLayout llVerticalColision = v.findViewById(R.id.linearLayout_verticalCollisionIndicator);

                if(event.getX() > llLateralLeft.getWidth() && event.getX() < (v.getWidth()-llLateralRight.getWidth()) && event.getY()> llVerticalColision.getMeasuredHeight()){
                    Debug.showLogError("Canvas Pressed!!!!!");
                    Debug.showLogError(String.valueOf(event.getX()));
                    Debug.showLogError(String.valueOf(event.getY()));

                }
                return false;
            }
        });
    }
    private void listenButtonsGasBrakeClear(final View v){
        //LISTENING TO THE GAS, BRAKE OR CLEAR BUTTON
        final ImageButton ibGas = v.findViewById(R.id.image_button_gas);
        ibGas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Debug.showLogError("Aprieto el GAS!");
                    viewModel.setGas(true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Debug.showLogError("Dejo ir el GAS!");
                    viewModel.setGas(false);
                }
                return true;
            }
        });

        ImageButton ibClear = v.findViewById(R.id.image_button_clear);
        ibClear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Debug.showLogError("Aprieto el CLEAR!");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Debug.showLogError("Dejo ir el CLEAR!");
                }
                return true;
            }
        });

        ImageButton ibBrake = v.findViewById(R.id.image_button_brake);
        ibBrake.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Debug.showLogError("Aprieto el BRAKE!");
                    viewModel.setBrake(false);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Debug.showLogError("Dejo ir el BRAKE!");
                    viewModel.setBrake(false);
                }
                return true;
            }
        });

        ImageButton ibBack = v.findViewById(R.id.image_button_back_ctrl_remoto);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }
    private void observeTemperature(final View v){
        //OBSERVING THE TEMPERATURE OF THE ROBOT
        final TextView tvTemperature = v.findViewById(R.id.text_temperature_number);
        final Observer<Integer> temperatureObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newTemperature) {
                // Update the layout. Both the number of the temperature and the termometer
                tvTemperature.setText(newTemperature.toString()+'º');
                pbTemperature.setProgress(newTemperature*3);
            }
        };
        //Let's begin the observation!
        viewModel.getTemperature().observe(this,temperatureObserver);

    }

    private void observeSpeed(final View v){
        //OBSERVING THE SPEED OF THE ROBOT
        final TextView tvSpeed = v.findViewById(R.id.text_speed_number);
        final Observer<Integer> speedObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newSpeed) {
                // Update the layout. We need to update both the progressbar and the value.
                tvSpeed.setText(newSpeed.toString()+ " Km/h");
                pbSpeed.setProgress(newSpeed);
            }
        };
        //Let's begin the observation!
        viewModel.getSpeed().observe(this,speedObserver);
    }


    private void initViewModel(final View v){
        viewModel = ViewModelProviders.of(getActivity()).get(CtrlRemotoViewModel.class);
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public void changeStatusCollisionIndicators(int indicator) {
        //Pre: indicator must be 0 (Frontal Collision), 1 (Back right collision), 2 (Back left collision).
        //Post: if the previous color of the selected indicator was the default color is changed to the accent color.
        //Otherwise the color is changed to the default text color.
        switch (indicator){
            case 0:
                if (tvFrontalCollision.getCurrentTextColor() == getResources().getColor(R.color.colorDefaultText)) {
                    tvFrontalCollision.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tvFrontalCollision.setTextColor(getResources().getColor(R.color.colorDefaultText));
                }
                break;
            case 1:
                if (tvBackRightCollision.getCurrentTextColor()==getResources().getColor(R.color.colorDefaultText)) {
                    tvBackRightCollision.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tvBackRightCollision.setTextColor(getResources().getColor(R.color.colorDefaultText));
                }
                break;
            case 2:
                if (tvBackLeftCollision.getCurrentTextColor() == getResources().getColor(R.color.colorDefaultText)) {
                    tvBackLeftCollision.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tvBackLeftCollision.setTextColor(getResources().getColor(R.color.colorDefaultText));
                }
                break;
        }
    }

    private void sendPolygonOrder (Prediction prediction) {
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


