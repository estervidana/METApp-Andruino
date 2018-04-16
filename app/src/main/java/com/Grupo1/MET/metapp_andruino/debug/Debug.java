package com.Grupo1.MET.metapp_andruino.debug;

import android.util.Log;


public class Debug {
    public Debug(){}
    //Class for debugging.
    private static final boolean debugEnable = true; //If true all the logs will be displayed.

    public static void showLog(String message){
        if(debugEnable) {
            Log.d("------------> Mensaje:", message);
        }
    }

}
