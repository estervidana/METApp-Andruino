package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.graphics.Path;

/**
 * Created by Guillermo Brugarolas on 27/03/2018.
 */

public class FingerPath {
    public int colour;
    public int strokeWidth;
    public Path path;

    public FingerPath(int colour, int strokeWidth, Path path) {
        this.colour = colour;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }


}
