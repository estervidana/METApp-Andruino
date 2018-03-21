package com.example.guillermobrugarolas.metapp_andruino.view.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.view.fragments.CtrlRemotoFragment;
import com.example.guillermobrugarolas.metapp_andruino.view.fragments.LabFragment;

public class LabActivity extends AppCompatActivity {
    private static final String LAB_FRAGMENT = "LAB_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
        initFragment();
    }

    private void initFragment(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(LAB_FRAGMENT);
        if(fragment == null){
            fragment = LabFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.lab_fragment,fragment, LAB_FRAGMENT);
        transaction.commit();
    }
}
