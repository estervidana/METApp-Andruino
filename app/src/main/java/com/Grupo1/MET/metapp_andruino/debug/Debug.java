package com.Grupo1.MET.metapp_andruino.debug;

import android.util.Log;


public class Debug {
    public Debug(){}

    private static final boolean debugEnable = true;

    public static void showLog(String message){
        if(debugEnable) {
            Log.d("------------> Mensaje:", message);
        }
    }

}
