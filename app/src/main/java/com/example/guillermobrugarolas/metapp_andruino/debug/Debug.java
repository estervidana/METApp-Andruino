package com.example.guillermobrugarolas.metapp_andruino.debug;

import android.util.Log;



public class Debug {
    public Debug(){}

    private static final boolean debugEnable = true;

    public static void showLogError(String error){
        if(debugEnable) {
            Log.d("------------> Mensaje:", error);
        }
    }
}
