package com.example.guillermobrugarolas.metapp_andruino.view.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.view.fragments.CtrlRemotoFragment;

public class CtrlRemotoActivity extends AppCompatActivity {
    private String CTRL_REMOTO_FRAGMENT = "CTRL_REMOTO_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctrl_remoto);
        initFragment();
    }
    private void initFragment(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(CTRL_REMOTO_FRAGMENT);
        if(fragment == null){
            //Creating the fragment that we want:
            fragment = CtrlRemotoFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_ctrol_remoto_container,fragment,CTRL_REMOTO_FRAGMENT);
        transaction.commit();
        Debug.showLog("Entering the remote control activity");

    }
}
