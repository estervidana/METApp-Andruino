package com.example.guillermobrugarolas.metapp_andruino.view.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.CtrlRemotoViewModel;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.PaintView;


public class CtrlRemotoFragment extends Fragment {
    private CtrlRemotoViewModel viewModel;
    private static ProgressBar pbSpeed, pbTemperature;
    private static TextView tvFrontalCollision, tvBackLeftCollision, tvBackRightCollision;
    private PaintView pvDrawShape;


    public static CtrlRemotoFragment newInstance(){
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
        //THE CIRCULAR PROGRESS BAR FOR THE SPEED
        pbSpeed = (ProgressBar) v.findViewById(R.id.progressbar_circular_progress);
        //THE TEMPERATURE PROGRESS BAR
        pbTemperature = (ProgressBar) v.findViewById(R.id.progressbar_temperature);
        //THE COLLISION INDICATORS
        tvBackLeftCollision = (TextView) v.findViewById(R.id.text_back_left_collision);
        tvFrontalCollision = (TextView) v.findViewById(R.id.text_frontal_collision);
        tvBackRightCollision = (TextView) v.findViewById(R.id.text_back_right_collision);
        //THE PAINT VIEW
        pvDrawShape = (PaintView) v.findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        pvDrawShape.init(metrics);
        //LISTENING THE LIGHT'S SWITCH
        final Switch sLigths = (Switch)v.findViewById(R.id.switch_mode_lights);
        sLigths.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled.
                    //Ahora lo ponemos a FALSE, tendremos que esperar la respuesta del robot
                    //Para ponerlo a true.
                    sLigths.setChecked(false);
                } else {
                    // The toggle is disabled
                }
            }
        });

        //LISTENING THE MANUAL MODE SWITCH
        final Switch sManualMode = (Switch)v.findViewById(R.id.switch_manual_mode);
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

        //LISTENING TO GEAR BUTTONS
        final TextView tvGearText = (TextView) v.findViewById(R.id.text_number_gear);
        final ImageButton ibGearUp = (ImageButton)v.findViewById(R.id.image_button_gearup);
        ibGearUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGearText.setText(String.valueOf(viewModel.incrementGear()));
                Debug.showLogError("Gear Up pressed");
            }
        });
        final ImageButton ibGearDown = (ImageButton)v.findViewById(R.id.image_button_geardown);
        ibGearDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGearText.setText(String.valueOf(viewModel.decrementGear()));
                Debug.showLogError("Gear Down pressed");
            }
        });
        //LISTENING TO THE CANVAS OF THE SCREEN
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //In order to adapt to different mobile resolutions we will check tye size of the lateral
                //Layouts and let the user paint only between them.
                LinearLayout llLateralLeft = (LinearLayout) v.findViewById(R.id.linearLayout_lateralLeft);
                LinearLayout llLateralRight = (LinearLayout) v.findViewById(R.id.linearLayout_lateralRight);
                LinearLayout llVerticalColision = (LinearLayout) v.findViewById(R.id.linearLayout_verticalCollisionIndicator);

                if(event.getX() > llLateralLeft.getWidth() && event.getX() < (v.getWidth()-llLateralRight.getWidth()) && event.getY()> llVerticalColision.getMeasuredHeight()){
                    Debug.showLogError("Canvas Pressed!!!!!");
                    Debug.showLogError(String.valueOf(event.getX()));
                    Debug.showLogError(String.valueOf(event.getY()));

                }
                return false;
            }
        });
        //LISTENING TO THE GAS, BRAKE OR CLEAR BUTTON
        ImageButton ibGas = (ImageButton) v.findViewById(R.id.image_button_gas);
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

        ImageButton ibClear = (ImageButton) v.findViewById(R.id.image_button_clear);
        ibClear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Debug.showLogError("Aprieto el CLEAR!");
                    //pvDrawShape.clear();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Debug.showLogError("Dejo ir el CLEAR!");
                    pvDrawShape.clear();
                }
                return true;
            }
        });

        ImageButton ibBrake = (ImageButton) v.findViewById(R.id.image_button_brake);
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


        //OBSERVING THE TEMPERATURE OF THE ROBOT
        final TextView tvTemperature = (TextView) v.findViewById(R.id.text_temperature_number);
        final Observer<Integer> temperatureObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newTemperature) {
                // Update the layout. Both the number of the temperature and the termometer
                tvTemperature.setText(newTemperature.toString()+'º');
                pbTemperature.setProgress(newTemperature);
            }
        };
        //Let's begin the observation!
        viewModel.getTemperature().observe(this,temperatureObserver);

        //OBSERVING THE SPEED OF THE ROBOT
        final TextView tvSpeed = (TextView) v.findViewById(R.id.text_speed_number);
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

}


