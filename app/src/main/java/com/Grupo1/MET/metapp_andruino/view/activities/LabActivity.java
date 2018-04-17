package com.Grupo1.MET.metapp_andruino.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Grupo1.MET.metapp_andruino.R;

/**
 * This class represents the activity of the labyrinth challenge.
 */

public class LabActivity extends AppCompatActivity {
    /** Constant used to create and recover the fragment.*/
    private static final String LAB_FRAGMENT = "LAB_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
    }
}
