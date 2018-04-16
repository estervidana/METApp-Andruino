package com.Grupo1.MET.metapp_andruino.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Grupo1.MET.metapp_andruino.R;

/**
 * This class represents the main menu activity with the four general options.
 */

public class MainActivity extends AppCompatActivity {

    private String MAIN_FRAGMENT = "MAIN_FRAGMENT"; //Name of the fragment.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
