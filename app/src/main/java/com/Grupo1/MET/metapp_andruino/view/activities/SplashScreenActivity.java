package com.Grupo1.MET.metapp_andruino.view.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Grupo1.MET.metapp_andruino.R;
import com.Grupo1.MET.metapp_andruino.data.communication.Logger;
import com.Grupo1.MET.metapp_andruino.data.communication.TestCommSenderService;
import com.Grupo1.MET.metapp_andruino.data.repository.Repository;
import com.Grupo1.MET.metapp_andruino.debug.Debug;
import com.Grupo1.MET.metapp_andruino.view.fragments.SplashScreenFragment;

import java.io.IOException;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String SPLASHSCREEN_FRAGMENT = "SPLASHSCREEN_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            startCommunicationService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initFragment();
    }

    private void startCommunicationService() throws IOException {
        Repository repository = Repository.getInstance();
        repository.addListener(Logger.getInstance(getApplicationContext()));
        repository.startService(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), TestCommSenderService.class);
        getApplicationContext().startService(intent);
    }

    private void initFragment(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(SPLASHSCREEN_FRAGMENT);
        if(fragment == null){
            fragment = SplashScreenFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.activity_splashscreen_container, fragment, SPLASHSCREEN_FRAGMENT);
        transaction.commit();
        Debug.showLog("Entering the splashscreen activity");
    }
}


