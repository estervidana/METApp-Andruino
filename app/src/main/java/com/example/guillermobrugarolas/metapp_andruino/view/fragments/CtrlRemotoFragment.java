package com.example.guillermobrugarolas.metapp_andruino.view.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.guillermobrugarolas.metapp_andruino.viewModel.CtrlRemotoViewModel;

import java.util.ArrayList;


public class CtrlRemotoFragment extends Fragment implements SensorEventListener {
    private CtrlRemotoViewModel viewModel;
    private  ProgressBar pbSpeed, pbTemperature;
    private  TextView tvFrontalCollision, tvBackLeftCollision, tvBackRightCollision;
    private GestureOverlayView govGestures;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private GestureLibrary gLibrary;
    private static long DELAY_TIME = 1000000;




    public static CtrlRemotoFragment newInstance() {
        return new CtrlRemotoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.showLog("Entering the remote control fragment");

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

    /**
     * In this method we are indicating which viewModel belongs to the remote control.
     * @param v the view of the remote control.
     */
    private void initViewModel(final View v){
        viewModel = ViewModelProviders.of(getActivity()).get(CtrlRemotoViewModel.class);
    }

    /**
     * In this function we declare all the elements that we will change in this fragment
     * and all of it's listeners.
     * @param v the view of the remote control.
     */
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
        //THE ACCELEROMETER SENSOR
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener( this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        }

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
                    Debug.showLog("::::::::::::::::::: Gesture recognised: " + prediction.name);
                    //send order to Arduino depending on the recognised gesture type
                    viewModel.sendPolygonOrder(prediction);
                }
            }
        });
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
        observeCollisions(v);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, 10000000);
    }

    /**
     * This is a listener for the sensors we have declared (the accelerometer).
     * It stores the three values (x,y,z) but only stores in the viewModel the y coordinate.
     * @param event is an event on the accelerometer.
     */
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

    /**
     * This method does nothing for the moment, but Android Studio required it's overriding.
     * @param sensor is the accelerometer.
     * @param accuracy is the accuracy. Now it is not used.
     */
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    /**
     * Listener for the switch that represent the lights of the robot. When the switch is clicked it must send a message
     * to the robot. The message sending part is still not implemented, for now it only turns the switch on/off.
     * @param v is the view of the remote control.
     */
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
                    sLigths.setChecked(false);
                }
            }
        });
    }

    /**
     * This method is a listener for the manual mode switch. Now, it changes the progress bar of the speed
     * of the robot in order to see how it changes.
     * @param v is the view of the remote control.
     */
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

    /**
     * This method is a listener for the Gear Buttons. For now, this method changes de gear value
     * and sends a debugging message.
     * @param v is the view of the remote control.
     */
    private void listenButtonsGear(final View v){
        //LISTENING TO GEAR BUTTONS
        final TextView tvGearText = v.findViewById(R.id.text_number_gear);
        final ImageButton ibGearUp = v.findViewById(R.id.image_button_gearup);
        ibGearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGearText.setText(String.valueOf(viewModel.incrementGear()));
                Debug.showLog("Gear Up pressed");
            }
        });
        final ImageButton ibGearDown = v.findViewById(R.id.image_button_geardown);
        ibGearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGearText.setText(String.valueOf(viewModel.decrementGear()));
                Debug.showLog("Gear Down pressed");
            }
        });

    }
    private void listenCanvas(final View v){
        //LISTENING TO THE CANVAS OF THE SCREEN
        v.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //In order to adapt to different mobile resolutions we will check tye size of the lateral
                //Layouts and let the user paint only between them.
                LinearLayout llLateralLeft = v.findViewById(R.id.linearLayout_lateralLeft);
                LinearLayout llLateralRight = v.findViewById(R.id.linearLayout_lateralRight);
                LinearLayout llVerticalColision = v.findViewById(R.id.linearLayout_verticalCollisionIndicator);

                if(event.getX() > llLateralLeft.getWidth() && event.getX() < (v.getWidth()-llLateralRight.getWidth()) && event.getY()> llVerticalColision.getMeasuredHeight()){
                    Debug.showLog("Canvas Pressed!!!!!");
                    Debug.showLog(String.valueOf(event.getX()));
                    Debug.showLog(String.valueOf(event.getY()));
                }
                return false;
            }
        });
    }

    /**
     * This method is a listener for the GAS, BRAKE and CLEAR buttons.
     * It sets the status of the corresponding variables of the view model to
     * false/true and send a debugging message.
     * @param v is the view of the remote control.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void listenButtonsGasBrakeClear(final View v){
        //LISTENING TO THE GAS, BRAKE OR CLEAR BUTTON
        final ImageButton ibGas = v.findViewById(R.id.image_button_gas);

        ibGas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.performClick();
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Debug.showLog("Aprieto el GAS!");
                    Toast.makeText(getActivity().getApplicationContext(), "Gas pressed", Toast.LENGTH_LONG).show();
                    viewModel.setGas(true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Debug.showLog("Dejo ir el GAS!");
                    Toast.makeText(getActivity().getApplicationContext(), "Gas released", Toast.LENGTH_LONG).show();
                    viewModel.setGas(false);
                }
                return true;
            }

        });

        ImageButton ibBrake = v.findViewById(R.id.image_button_brake);
        ibBrake.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Debug.showLog("Aprieto el BRAKE!");
                    Toast.makeText(getActivity().getApplicationContext(), "Brake pressed", Toast.LENGTH_LONG).show();
                    viewModel.setBrake(false);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Debug.showLog("Dejo ir el BRAKE!");
                    Toast.makeText(getActivity().getApplicationContext(), "Brake released", Toast.LENGTH_LONG).show();
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
    /**
     * This method observes the temperature of the robot, a variable stored in the ViewModel.
     * When a change in the temperature is observed, the value is set into the  progress bar of the view
     * (termometer) and it's corresponding textView.
     * @param v is the view of the remote control.
     */
    private void observeTemperature(final View v){
        final TextView tvTemperature = v.findViewById(R.id.text_temperature_number);
        final Observer<Integer> temperatureObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newTemperature) {
                // Update the layout. Both the number of the temperature and the termometer
                tvTemperature.setText(newTemperature.toString()+'º');
                pbTemperature.setProgress(newTemperature*3);
            }
        };
        //Declaring which variable is observed:
        viewModel.getTemperature().observe(this,temperatureObserver);

    }

    /**
     * This methos observes the collision variables of the viewModel. Then it changes the status of the collision indicator.
     * @param v is the view.
     */
    private void observeCollisions(final View v){
        final Observer<Integer> collisionFrontObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer status) {
                changeStatusCollisionIndicators(0,status);
            }
        };
        viewModel.getCollisionFront().observe(this,collisionFrontObserver);
        final Observer<Integer> collisionLeftObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer status) {
                changeStatusCollisionIndicators(2,status);

            }
        };
        viewModel.getCollisionLeft().observe(this,collisionLeftObserver);

        final Observer<Integer> collisionRightObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer status) {
                changeStatusCollisionIndicators(1,status);
            }
        };
        viewModel.getCollisionRight().observe(this,collisionRightObserver);

    }

    /**
     * This method observes the speed of the robot, a variable stored in the ViewModel.
     * When a change in the speed is observed, the value is set into the circular progress bar of the view
     * (velocimeter) and it's corresponding textView.
     * @param v is the view of the remote control.
     */
    private void observeSpeed(final View v){
        final TextView tvSpeed = v.findViewById(R.id.text_speed_number);
        final Observer<Integer> speedObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newSpeed) {
                // Update the layout. We need to update both the progressbar and the value.
                String saux = newSpeed.toString()+ " Km/h";
                tvSpeed.setText(saux);
                pbSpeed.setProgress(newSpeed);
            }
        };
        //Declaring which variable is observed:
        viewModel.getSpeed().observe(this,speedObserver);
    }




    @Override
    public void onStop(){
        super.onStop();
    }

    /**
     * This methos changes the color of the collision indicators depending of the parameters.
     * @param indicator  must be 0 (Frontal Collision), 1 (Back right collision), 2 (Back left collision).
     * @param status must be 0(no collision), 1 (collision).
     */
    public void changeStatusCollisionIndicators(int indicator, int status) {

        switch (indicator){
            case 0:
                if (status ==1){
                    tvFrontalCollision.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tvFrontalCollision.setTextColor(getResources().getColor(R.color.colorDefaultText));
                }
                break;
            case 1:
                if (status ==1){
                    tvBackRightCollision.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tvBackRightCollision.setTextColor(getResources().getColor(R.color.colorDefaultText));
                }
                break;
            case 2:
                if (status ==1){
                    tvBackLeftCollision.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    tvBackLeftCollision.setTextColor(getResources().getColor(R.color.colorDefaultText));
                }
                break;
        }
    }
}


