package com.example.guillermobrugarolas.metapp_andruino.view.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.LabyrinthSection;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.LabyrinthSectionState;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.RecyclerViewAdapter;

public class LabFragment extends Fragment {
    private static final int MAX_ROWS = 5;
    private static final int MAX_COLUMNS = 5;
    private String robotOrientation;
    private ImageButton ibSolution;
    private ImageButton ibBack;
    private ImageView[][] imageData = new ImageView[MAX_ROWS][MAX_COLUMNS];
    private ImageView[] imageDataAdapter = new ImageView[MAX_ROWS*MAX_COLUMNS];

    private RecyclerView rvLab;
    private RecyclerViewAdapter adapter;
    //gridView is used to display on screen
    private SurfaceView[][] gridView = new SurfaceView[MAX_ROWS][MAX_COLUMNS];
    //gridData stores the information of each Section
    private LabyrinthSection[][] sectionData = new LabyrinthSection[MAX_ROWS][MAX_COLUMNS];

    private GridLayout gridLayout;

    private ImageView target;

    public static LabFragment newInstance(){
        return new LabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lab, container, false);
        for(int count1 = 0; count1 < MAX_ROWS; count1++) {
            for(int count2 = 0; count2 < MAX_COLUMNS; count2++) {
                imageData[count1][count2] = new ImageView(this.getActivity());
            }
        }
        setDrawables(imageData);
        initData();
        bindViews(view);
        //display random labyrinth
        displayRandomLab();
        adapter.notifyDataSetChanged();
        return view;
    }

    //fixme temporary method
    private void initData() {
        robotOrientation = "RIGHT";
        for(int count1 = 0; count1 < MAX_ROWS; count1++) {
            for(int count2 = 0; count2 < MAX_COLUMNS; count2++) {
                sectionData[count1][count2] = new LabyrinthSection(LabyrinthSectionState.DEAD_END, false, 0, new boolean[4]);
            }
        }
        sectionData[4][0].setState(LabyrinthSectionState.CURRENT);
        sectionData[0][4].setTargetLocation(true);
    }

    private void bindViews(View view) {
        //BIND THE THREE BUTTONS IN THE LABYRINTH SCREEN
        ibBack = view.findViewById(R.id.image_button_back_labyrinth);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ibSolution = view.findViewById(R.id.image_button_solution_labyrinth);
        ibSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.showLog("Recorrer solución laberinto!");
                //send signal to robot to start moving
            }
        });

        rvLab = view.findViewById(R.id.rvLabyrinth);
        int nCols = MAX_COLUMNS;
        rvLab.setLayoutManager(new GridLayoutManager(this.getActivity(), nCols));
        imageDataAdapter = matrixToArrayImageData(imageData);
        adapter = new RecyclerViewAdapter(this.getActivity(), imageDataAdapter);
        rvLab.setAdapter(adapter);
        rvLab.setHasFixedSize(true);
    }


    //ON WALL IN FRONT DETECTED
    public void printFrontWall() {
        for (int count1 = 0; count1 < MAX_ROWS; count1++) {
            for (int count2 = 0; count2 < MAX_COLUMNS; count2++) {
                if (sectionData[count1][count2].getState().equals(LabyrinthSectionState.CURRENT)) {
                    if (sectionData[count1][count2].getPassed() == 0) {
                        int currPaddLeft = imageData[count1][count2].getPaddingLeft();
                        int currPaddTop = imageData[count1][count2].getPaddingTop();
                        int currPaddRight = imageData[count1][count2].getPaddingRight();
                        int currPaddBottom = imageData[count1][count2].getPaddingBottom();
                        if (robotOrientation.equals("RIGHT")) {
                            if (count2 == 4) {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, 10, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, 5, currPaddBottom);
                            }
                        } else if (robotOrientation.equals("LEFT")) {
                            if (count2 == 0) {
                                imageData[count1][count2].setPadding(10, currPaddTop, currPaddRight, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(5, currPaddTop, currPaddRight, currPaddBottom);
                            }
                        } else if (robotOrientation.equals("UP")) {
                            if (count1 == 0) {
                                imageData[count1][count2].setPadding(currPaddLeft, 10, currPaddRight, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, 5, currPaddRight, currPaddBottom);
                            }
                        } else if (robotOrientation.equals("DOWN")) {
                            if (count1 == 4) {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, currPaddRight, 10);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, currPaddRight, 5);
                            }
                        }
                    }
                }
            }
        }
    }

    //ON WALL RIGHT DETECTED
    public void printRightWall() {
        for (int count1 = 0; count1 < MAX_ROWS; count1++) {
            for (int count2 = 0; count2 < MAX_COLUMNS; count2++) {
                if (sectionData[count1][count2].getState().equals(LabyrinthSectionState.CURRENT)) {
                    if (sectionData[count1][count2].getPassed() == 0) {
                        int currPaddLeft = imageData[count1][count2].getPaddingLeft();
                        int currPaddTop = imageData[count1][count2].getPaddingTop();
                        int currPaddRight = imageData[count1][count2].getPaddingRight();
                        int currPaddBottom = imageData[count1][count2].getPaddingBottom();
                        if (robotOrientation.equals("RIGHT")) {
                            if (count1 == 4) {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, currPaddRight, 10);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, currPaddRight, 5);
                            }
                        } else if (robotOrientation.equals("LEFT")) {
                            if (count1 == 0) {
                                imageData[count1][count2].setPadding(currPaddLeft, 10, currPaddRight, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, 5, currPaddRight, currPaddBottom);
                            }
                        } else if (robotOrientation.equals("UP")) {
                            if (count2 == 4) {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, 10, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, 5, currPaddBottom);
                            }
                        } else if (robotOrientation.equals("DOWN")) {
                            if (count2 == 0) {
                                imageData[count1][count2].setPadding(10, currPaddTop, currPaddRight, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(5, currPaddTop, currPaddRight, currPaddBottom);
                            }
                        }
                    }
                }
            }
        }
    }

    //ON WALL LEFT DETECTED
    public void printLeftWall() {
        for (int count1 = 0; count1 < MAX_ROWS; count1++) {
            for (int count2 = 0; count2 < MAX_COLUMNS; count2++) {
                if (sectionData[count1][count2].getState().equals(LabyrinthSectionState.CURRENT)) {
                    if (sectionData[count1][count2].getPassed() == 0) {
                        int currPaddLeft = imageData[count1][count2].getPaddingLeft();
                        int currPaddTop = imageData[count1][count2].getPaddingTop();
                        int currPaddRight = imageData[count1][count2].getPaddingRight();
                        int currPaddBottom = imageData[count1][count2].getPaddingBottom();
                        if (robotOrientation.equals("RIGHT")) {
                            if (count1 == 0) {
                                imageData[count1][count2].setPadding(currPaddLeft, 10, currPaddRight, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, 5, currPaddRight, currPaddBottom);
                            }
                        } else if (robotOrientation.equals("LEFT")) {
                            if (count1 == 4) {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, currPaddRight, 10);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, currPaddRight, 5);
                            }
                        } else if (robotOrientation.equals("UP")) {
                            if (count2 == 0) {
                                imageData[count1][count2].setPadding(10, currPaddTop, currPaddRight, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(5, currPaddTop, currPaddRight, currPaddBottom);
                            }
                        } else if (robotOrientation.equals("DOWN")) {
                            if (count2 == 4) {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, 10, currPaddBottom);
                            } else {
                                imageData[count1][count2].setPadding(currPaddLeft, currPaddTop, 5, currPaddBottom);
                            }
                        }
                    }
                }
            }
        }
    }

    //ON 180° LEFT ROTATION DETECTED
    public void leftRotationUpdate() {
        switch (robotOrientation) {
            case "RIGHT":
                robotOrientation = "UP";
                break;
            case "LEFT":
                robotOrientation = "DOWN";
                break;
            case "UP":
                robotOrientation = "LEFT";
                break;
            case "DOWN":
                robotOrientation = "RIGHT";
                break;
        }
    }

    //ON 180° RIGHT ROTATION DETECTED
    public void rightRotationUpdate() {
        switch (robotOrientation) {
            case "RIGHT":
                robotOrientation = "DOWN";
                break;
            case "LEFT":
                robotOrientation = "UP";
                break;
            case "UP":
                robotOrientation = "RIGHT";
                break;
            case "DOWN":
                robotOrientation = "LEFT";
                break;
        }
    }

    //ON REVERSE GEAR DETECTED??

    //ON CELL PASSED (moved forward x cm?)
    public void positionUpdate() {
        for (int count1 = 0; count1 < MAX_ROWS; count1++) {
            for (int count2 = 0; count2 < MAX_COLUMNS; count2++) {
                if (sectionData[count1][count2].getState().equals(LabyrinthSectionState.CURRENT)) {
                    sectionData[count1][count2].setState(LabyrinthSectionState.PASSED_UNCERTAIN);
                    sectionData[count1][count2].setPassed(sectionData[count1][count2].getPassed()+1);
                    setSectionBackground(imageData[count1][count2], LabyrinthSectionState.PASSED_UNCERTAIN);
                    switch (robotOrientation) {
                        case "RIGHT":
                            sectionData[count1][count2 + 1].setState(LabyrinthSectionState.CURRENT);
                            setSectionBackground(imageData[count1][count2 + 1], LabyrinthSectionState.CURRENT);
                            break;
                        case "LEFT":
                            sectionData[count1][count2 - 1].setState(LabyrinthSectionState.CURRENT);
                            setSectionBackground(imageData[count1][count2 - 1], LabyrinthSectionState.CURRENT);
                            break;
                        case "UP":
                            sectionData[count1 - 1][count2].setState(LabyrinthSectionState.CURRENT);
                            setSectionBackground(imageData[count1 - 1][count2], LabyrinthSectionState.CURRENT);
                            break;
                        case "DOWN":
                            sectionData[count1 + 1][count2].setState(LabyrinthSectionState.CURRENT);
                            setSectionBackground(imageData[count1 + 1][count2], LabyrinthSectionState.CURRENT);
                            break;
                    }
                }
            }
        }
    }

    private void setSectionBackground(ImageView imageData, LabyrinthSectionState state) {
        int color;
        switch(state){
            case PASSED_UNCERTAIN:
                color = getResources().getColor(R.color.colorGridPassedUncertain);
                break;
            case SOLUTION:
                color = getResources().getColor(R.color.colorGridSolution);
                break;
            case DEAD_END:
                color = getResources().getColor(R.color.colorGridDeadEnd);
                break;
            case UNEXPLORED:
                color = getResources().getColor(R.color.colorGridUnexplored);
                break;
            case CURRENT:
                color = getResources().getColor(R.color.colorGridCurrent);
                break;
            default:
                //TODO create message constant and improve message content
                throw new IllegalStateException("There is no valid state defined.");
        }
        imageData.setColorFilter(color);
    }

    private void setDrawables(ImageView[][] imageData) {
        for(int count1 = 0; count1 < MAX_ROWS; count1++) {
            for (int count2 = 0; count2 < MAX_COLUMNS; count2++) {
                imageData[count1][count2].setImageDrawable(getResources().getDrawable(R.drawable.emptygridcell));
            }
        }
    }

    private ImageView[] matrixToArrayImageData(ImageView[][] imageData) {
        ImageView newArray[] = new ImageView[imageData.length * imageData[0].length];
        for (int i = 0; i < imageData.length; i++) {
            ImageView[] row = imageData[i];
            System.arraycopy(imageData[i], 0, newArray, i * row.length, row.length);
        }
        return newArray;
    }

    private void displayRandomLab() {
        imageData[4][0].setPadding(0,5,0,5);
        imageData[4][0].setColorFilter(getResources().getColor(R.color.colorGridPassedUncertain));
        imageData[4][1].setPadding(0,0,5,5);
        imageData[4][1].setColorFilter(getResources().getColor(R.color.colorGridPassedUncertain));
        imageData[4][2].setPadding(5,0,0,5);
        imageData[4][2].setColorFilter(getResources().getColor(R.color.colorGridPassedUncertain));
        imageData[3][2].setColorFilter(getResources().getColor(R.color.colorGridCurrent));
        imageData[3][2].setPadding(0, 5, 5, 0);
        imageData[2][2].setPadding(0, 0, 5, 5);
        imageData[2][3].setPadding(5, 0, 0, 0);
        imageData[0][4].setPadding(0, 5, 0, 5);
        imageData[0][4].setColorFilter(getResources().getColor(R.color.colorGridTarget));
        imageData[0][3].setPadding(5, 5, 0, 0);
        imageData[0][3].setColorFilter(getResources().getColor(R.color.colorGridSolution));
        imageData[0][2].setPadding(5,5,5,0);
        imageData[0][2].setColorFilter(getResources().getColor(R.color.colorGridDeadEnd));
    }
}
