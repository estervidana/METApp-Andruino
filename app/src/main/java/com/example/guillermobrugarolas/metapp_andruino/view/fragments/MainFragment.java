package com.example.guillermobrugarolas.metapp_andruino.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.AccelActivity;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.CtrlRemotoActivity;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.LabActivity;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.LogCommActivity;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.MainViewModel;



public class MainFragment extends Fragment{

    private TextView textView;
    private Button bCtrlRem, bAccel, bLab, bLogComm;

    private MainViewModel viewModel;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View v = inflater.inflate(R.layout.fragment_main,container,false);
        bindViews(v);
        initViewModel();
        return v;
    }

    private void bindViews(View v){

        bCtrlRem = v.findViewById(R.id.bCtrlRemoto);
        bCtrlRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intCtrlRem = new Intent(getContext(), CtrlRemotoActivity.class);
                startActivity(intCtrlRem);
                Debug.showLog("Ir a Ctrl remoto");
            }
        });
        bAccel = v.findViewById(R.id.bAcelerometro);
        bAccel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intAccel = new Intent(getContext(), AccelActivity.class);
                startActivity(intAccel);
                Debug.showLog("Ir a Accelerometer");
            }
        });
        bLab = v.findViewById(R.id.bLaberinto);
        bLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLab = new Intent(getContext(), LabActivity.class);
                startActivity(intLab);
                Debug.showLog("Ir a Laberinto");
            }
        });
        bLogComm = v.findViewById(R.id.bLogCom);
        bLogComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLogComm = new Intent(getContext(), LogCommActivity.class);
                startActivity(intLogComm);
                Debug.showLog("Ir a Log Comms");
            }
        });
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    public void onStop(){
        super.onStop();
    }

}
