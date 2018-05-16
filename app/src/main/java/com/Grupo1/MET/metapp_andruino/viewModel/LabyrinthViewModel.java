package com.Grupo1.MET.metapp_andruino.viewModel;

import android.arch.lifecycle.ViewModel;

import com.Grupo1.MET.metapp_andruino.data.repository.Repository;

/**
 * Created by Guillermo Brugarolas on 02/05/2018.
 */

public class LabyrinthViewModel extends ViewModel implements Repository.RepositoryListener {



    public LabyrinthViewModel(){
        Repository.getInstance().addListener(this);
    }

    @Override
    public void onMessageReceived(String message) {

    }

    @Override
    public void onServiceStopped() {

    }
}

//NOTA DEL XAVI:
/* pasarnos desde el arduino el estado de todas las casillas cada vez, es decir, la estructura de
5x5 de tipo Casilla, función actualizaCasilla con los cuatro booleanos y el estado (2, 3, 4...)
 */
