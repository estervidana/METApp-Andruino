package com.Grupo1.MET.metapp_andruino.debug;

import android.util.Log;

/**
 * Class used to centralize writing logs. These logs should only be written when debugging the app.
 */
public class Debug {
    /**
     * Private constructor to make sure no one instantiates a Debug object.
     */
    private Debug(){}

    /** Indicates whether the app is running in debug mode or not.*/
    private static final boolean debugEnable = true;

    /**
     * Shows logs if {@link #debugEnable} is true. If {@link #debugEnable} is false, it will do nothing.
     *
     * @param message The message to be displayed in Logcat.
     */
    public static void showLog(String message){
        if(debugEnable) {
            Log.d("----> DebugMessage: ", message);
        }
    }

}
