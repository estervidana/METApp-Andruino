package com.Grupo1.MET.metapp_andruino.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Grupo1.MET.metapp_andruino.R;

public class AccelActivity extends AppCompatActivity {

    private String ACCEL_FRAGMENT = "ACCEL_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel);
    }
}
