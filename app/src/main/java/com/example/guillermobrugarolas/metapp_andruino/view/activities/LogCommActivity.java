package com.example.guillermobrugarolas.metapp_andruino.view.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.view.fragments.LogCommFragment;

public class LogCommActivity extends AppCompatActivity {

    private String LOGCOMM_FRAGMENT = "LOGCOMM_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_comm);
        initFragment();
    }

    private void initFragment(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(LOGCOMM_FRAGMENT);
        if(fragment == null){
            fragment = LogCommFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_log_comm_container,fragment,LOGCOMM_FRAGMENT);
        transaction.commit();
        Debug.showLogError("Entering the log comm activity");
    }
}
