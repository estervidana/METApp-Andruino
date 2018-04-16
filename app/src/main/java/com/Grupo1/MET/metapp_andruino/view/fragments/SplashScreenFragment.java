package com.Grupo1.MET.metapp_andruino.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.Grupo1.MET.metapp_andruino.R;
import com.Grupo1.MET.metapp_andruino.debug.Debug;
import com.Grupo1.MET.metapp_andruino.view.activities.MainActivity;

/**
 * This class represents the splash screen fragment that displays its view items.
 */
public class SplashScreenFragment extends Fragment {
    /** The time out in milliseconds. */
    private static final int SPLASHSCREEN_TIMEOUT = 1000;
    /** The title of the app. */
    private  TextView tvTitle;
    /** The logo of the app. */
    private  ImageView ivLogo;


    public SplashScreenFragment() {
    }

    public static SplashScreenFragment newInstance() { return new SplashScreenFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.showLog("Entering the splashscreen fragment");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start your app main activity
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        }, SPLASHSCREEN_TIMEOUT);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        bindViews(v);
        return v;
    }

    /**
     * This method links every view item to its corresponding layout item.
     * @param v The view that contains these items.
     */
    private void bindViews(View v){
        ivLogo = v.findViewById(R.id.ivLogo);
        tvTitle = v.findViewById(R.id.tvTitle);
    }
}
