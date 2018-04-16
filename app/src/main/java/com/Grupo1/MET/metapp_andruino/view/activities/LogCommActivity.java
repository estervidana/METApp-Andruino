package com.Grupo1.MET.metapp_andruino.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Grupo1.MET.metapp_andruino.R;

/**
 * Displays the Logs of the messages sent to and from the robot/app.
 */
public class LogCommActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_comm);
    }
}
