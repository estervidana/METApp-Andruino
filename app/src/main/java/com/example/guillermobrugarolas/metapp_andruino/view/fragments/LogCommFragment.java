package com.example.guillermobrugarolas.metapp_andruino.view.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.data.communication.Logger;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.LogCommViewModel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Displays the Logs of the messages sent to and from the robot/app.
 */
public class LogCommFragment extends Fragment {
    private LogCommViewModel viewModel;
    private ArrayAdapter<String> listAdapter;

    /**
     * Necessary empty constructor.
     */
    public LogCommFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_comm, container, false);
        try {
            bindViews(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initViewModel();
        return v;
    }

    /**
     * Associate the views.
     *
     * @param v The Fragments View.
     * @throws IOException If an I/O error occurred.
     */
    public void bindViews(View v) throws IOException {
        ListView lvLogs = v.findViewById(R.id.lvLogComm);
        List<String> logs = Logger.getInstance(getActivity().getApplicationContext()).getLogs();
        Collections.reverse(logs);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, logs.toArray(new String[0]));
        lvLogs.setAdapter(adapter);
        ImageButton ibBack = v.findViewById(R.id.image_button_back_log_comm);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    /**
     * Starts the ViewModel.
     */
    private void initViewModel(){
        viewModel = ViewModelProviders.of(getActivity()).get(LogCommViewModel.class);
    }
}
