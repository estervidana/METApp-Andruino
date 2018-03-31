package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.arch.lifecycle.ViewModel;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class LogCommViewModel extends ViewModel {
    private int listRowCount = 0;
    private int listIndex = 0;
    private ArrayList<String> listEvents;

    public LogCommViewModel(){}

    public int incrementListIndex() {
        listIndex++;
        return listIndex;
    }

    public int getListRowCount() {
        return listRowCount;
    }

    public void addEvent(String event) {
        listEvents.add(event);
    }

    public ArrayList<String> getListEvents() {
        return listEvents;
    }

    public void setListEvents(ArrayList<String> listEvents) {
        this.listEvents = listEvents;
    }
}

