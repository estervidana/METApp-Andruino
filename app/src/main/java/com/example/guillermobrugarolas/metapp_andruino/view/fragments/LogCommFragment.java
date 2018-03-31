package com.example.guillermobrugarolas.metapp_andruino.view.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.view.activities.MainActivity;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.LogCommViewModel;

import java.util.ArrayList;

public class LogCommFragment extends Fragment {

    private static TextView tvLogComm;
    private static ListView lvLogComm;
    private LogCommViewModel viewModel;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> eventList;

    public LogCommFragment() {
        // Required empty public constructor
    }

    public static LogCommFragment newInstance() { return new LogCommFragment();    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.showLogError("Entering the log comm fragment");
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_comm, container, false);
        bindViews(v);
        initViewModel(v);
        return v;
    }


    public void bindViews(View v) {
        tvLogComm = v.findViewById(R.id.tvLogComm);
        lvLogComm = v.findViewById(R.id.lvLogComm);
        eventList = new ArrayList<String>();
        //prueba:
        eventList.add("Message: I am here | Type: Acknowledge | In/Out: Out | Date: 2018-03-21");
        listAdapter  = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, eventList);
        lvLogComm.setAdapter(listAdapter);
        listAdapter.setNotifyOnChange(true);
        ImageButton ibBack = v.findViewById(R.id.image_button_back_log_comm);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public void addEvent(String event) {
        eventList.add(event);
        //viewModel.addEvent(event);
        listAdapter.add(eventList.get((eventList.size())-1));
        listAdapter.notifyDataSetChanged();
    }

    private void initViewModel(final View v){
        viewModel = ViewModelProviders.of(getActivity()).get(LogCommViewModel.class);
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
