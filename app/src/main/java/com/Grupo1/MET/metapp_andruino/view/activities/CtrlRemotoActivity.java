package com.Grupo1.MET.metapp_andruino.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Grupo1.MET.metapp_andruino.R;

/**
 * This class represents the activity that contains all the remote control options.
 */

public class CtrlRemotoActivity extends AppCompatActivity {
    private String CTRL_REMOTO_FRAGMENT = "CTRL_REMOTO_FRAGMENT"; //Name of the fragment.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctrl_remoto);
    }
}
