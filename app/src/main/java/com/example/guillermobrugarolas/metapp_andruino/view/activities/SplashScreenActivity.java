package com.example.guillermobrugarolas.metapp_andruino.view.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.view.fragments.SplashScreenFragment;

public class SplashScreenActivity extends AppCompatActivity {

    private String SPLASHSCREEN_FRAGMENT = "SPLASHSCREEN_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initFragment();
    }
    private void initFragment(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(SPLASHSCREEN_FRAGMENT);
        if(fragment == null){
            //Creating the fragment that we want:
            fragment = SplashScreenFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_splashscreen_container, fragment, SPLASHSCREEN_FRAGMENT);
        transaction.commit();
        Debug.showLogError("Entering the splashscreen activity");
    }
}


