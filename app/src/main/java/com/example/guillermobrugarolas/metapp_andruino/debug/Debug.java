package com.example.guillermobrugarolas.metapp_andruino.debug;

import android.util.Log;
import android.widget.Toast;


public class Debug {
    public Debug(){}

    private static final boolean debugEnable = true;

    public static void showLog(String message){
        if(debugEnable) {
            Log.d("------------> Mensaje:", message);
        }
    }

}
