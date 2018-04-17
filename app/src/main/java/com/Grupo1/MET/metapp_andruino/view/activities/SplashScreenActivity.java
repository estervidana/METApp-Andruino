package com.Grupo1.MET.metapp_andruino.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.Grupo1.MET.metapp_andruino.R;
import com.Grupo1.MET.metapp_andruino.data.communication.Logger;
import com.Grupo1.MET.metapp_andruino.data.communication.TestCommSenderService;
import com.Grupo1.MET.metapp_andruino.data.repository.Repository;

import java.io.IOException;

/**
 * This class represents the activity that contains the splash screen of the app.
 */

public class SplashScreenActivity extends AppCompatActivity {
    /** Constant used to create and recover the fragment.*/
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
    }

    private void startCommunicationService() throws IOException {
        Repository repository = Repository.getInstance();
        repository.addListener(Logger.getInstance(getApplicationContext()));
        repository.startService(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), TestCommSenderService.class);
        getApplicationContext().startService(intent);
    }
}


