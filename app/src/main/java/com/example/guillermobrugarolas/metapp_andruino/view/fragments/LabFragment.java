package com.example.guillermobrugarolas.metapp_andruino.view.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.example.guillermobrugarolas.metapp_andruino.view.activities.MainActivity;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.LabyrinthSection;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.LabyrinthSectionState;
import com.example.guillermobrugarolas.metapp_andruino.viewModel.RecyclerViewAdapter;

public class LabFragment extends Fragment {
    private static final int MAX_ROWS = 5;
    private static final int MAX_COLUMNS = 5;
    private ImageButton ibStart;
    private ImageButton ibSolution;
    private ImageButton ibBack;
    private ImageView[] data;

    private RecyclerView rvLab;
    private RecyclerViewAdapter adapter;
    //gridView is used to display on screen
    private SurfaceView[] gridView = new SurfaceView[MAX_ROWS*MAX_COLUMNS];
    //gridData stores the information of each Section
    private LabyrinthSection[]gridData = new LabyrinthSection[MAX_ROWS*MAX_COLUMNS];

    private GridLayout gridLayout;

    private ImageView target;

    public static LabFragment newInstance(){
        return new LabFragment();
    }

    public LabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lab, container, false);

        data = new ImageView[25];
        for (int gg = 0; gg < 25; gg++) {
            data[gg] = new ImageView(this.getActivity());
        }
        setDrawables(data);


        //gridLayout = view.findViewById(R.id.lab_grid);
        initData();
        //initSections();
        bindViews(view);
        //displaySections();

        return view;
    }

    //fixme temporary method
    private void initData() {
        for(int count = 0; count < MAX_ROWS*MAX_COLUMNS; count++) {
            gridData[count] = new LabyrinthSection(LabyrinthSectionState.DEAD_END, false, new boolean[4]);
        }
    }

    private void bindViews(View view) {
        //BIND THE THREE BUTTONS IN THE LABYRINTH SCREEN
        ibBack = view.findViewById(R.id.image_button_back_labyrinth);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intMain = new Intent(getContext(), MainActivity.class);
                startActivity(intMain);
                Debug.showLogError("Volver a Menu!");
            }
        });
        ibSolution = view.findViewById(R.id.image_button_solution_labyrinth);
        ibSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.showLogError("Ver solución laberinto!");
            }
        });
        ibStart = view.findViewById(R.id.image_button_start_labyrinth);
        ibStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Debug.showLogError("Comenzar laberinto!");
            }
        });
        rvLab = view.findViewById(R.id.rvLabyrinth);
        int nCols = 5;
        rvLab.setLayoutManager(new GridLayoutManager(this.getActivity(), nCols));
        adapter = new RecyclerViewAdapter(this.getActivity(), data);
        rvLab.setAdapter(adapter);
    }

//    private void initSections() {
//        for(int row = 0; row < MAX_ROWS; row++){
//            for(int column = 0; column < MAX_COLUMNS; column++){
//                gridView[row][column] = initSection(gridData[row][column]);
//            }
//        }
//    }

//    public SurfaceView initSection(LabyrinthSection sectionData){
//        SurfaceView sectionView = new SurfaceView(getContext());
//        setSectionBackground(sectionView, sectionData.getState());
//        drawWalls(sectionView, sectionData.getWalls());
//        return sectionView;
//    }

//    private void displaySections() {
//        for(int row = 0; row < MAX_ROWS; row++){
//            for(int column = 0; column < MAX_COLUMNS; column++){
//                GridLayout.Spec rowSpec = GridLayout.spec(row);
//                GridLayout.Spec columnSpec = GridLayout.spec(column);
//                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
//                gridLayout.addView(gridView[row][column], layoutParams);
//            }
//        }
//    }

    //METHOD FOR SETTING A CELL TO DEAD END
    private void setDeadEndSection(int cell) {
        //THIS IS ONLY FOR THE CASE OF WALLUP
        //every change of colour, of cell type, has to keep the walls of the cell
        if (data[cell].getDrawable() == getResources().getDrawable(R.drawable.wallup)) {
            //data[cell].setImageDrawable(getResources().getDrawable(R.drawable.wallupdeadend));
            gridData[cell].setState(LabyrinthSectionState.DEAD_END);
        }
    }

    //METHOD FOR SETTING A CELL TO SOLUTION
    private void setSolutionSection(int cell) {
        //THIS IS ONLY FOR THE CASE OF WALLUP
        //every change of colour, of cell type, has to keep the walls of the cell
        if (data[cell].getDrawable() == getResources().getDrawable(R.drawable.wallup)) {
            //data[cell].setImageDrawable(getResources().getDrawable(R.drawable.wallupsolution));
            gridData[cell].setState(LabyrinthSectionState.SOLUTION);
        }
    }

    private void setSectionBackground(SurfaceView sectionView, LabyrinthSectionState state) {
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
            default:
                //TODO create message constant and improve message content
                throw new IllegalStateException("There is no valid state defined.");
        }
        sectionView.setBackgroundColor(color);
    }

//    private void drawWalls(SurfaceView sectionView, boolean[] walls) {
//        for(boolean wall: walls){
//            //sectionView.draw
//        }
//    }

    private void setDrawables(ImageView[] data) {
        data[0].setImageDrawable(getResources().getDrawable(R.drawable.wallupdownleft));
        data[1].setImageDrawable(getResources().getDrawable(R.drawable.wallup));
        data[2].setImageDrawable(getResources().getDrawable(R.drawable.wallup));
        data[3].setImageDrawable(getResources().getDrawable(R.drawable.wallupdown));
        data[4].setImageDrawable(getResources().getDrawable(R.drawable.wallupdownfinish));
        data[5].setImageDrawable(getResources().getDrawable(R.drawable.wallleft));
        data[6].setImageDrawable(getResources().getDrawable(R.drawable.walldown));
        data[7].setImageDrawable(getResources().getDrawable(R.drawable.wallright));
        data[8].setImageDrawable(getResources().getDrawable(R.drawable.wallnowalls));
        data[9].setImageDrawable(getResources().getDrawable(R.drawable.walldownright));
        data[10].setImageDrawable(getResources().getDrawable(R.drawable.walldownleftright));
        data[11].setImageDrawable(getResources().getDrawable(R.drawable.walldown));
        data[12].setImageDrawable(getResources().getDrawable(R.drawable.walldown));
        data[13].setImageDrawable(getResources().getDrawable(R.drawable.wallnowalls));
        data[14].setImageDrawable(getResources().getDrawable(R.drawable.walldownright));
        data[15].setImageDrawable(getResources().getDrawable(R.drawable.walldownleft));
        data[16].setImageDrawable(getResources().getDrawable(R.drawable.wallnowalls));
        data[17].setImageDrawable(getResources().getDrawable(R.drawable.wallnowalls));
        data[18].setImageDrawable(getResources().getDrawable(R.drawable.wallright));
        data[19].setImageDrawable(getResources().getDrawable(R.drawable.wallright));
        data[20].setImageDrawable(getResources().getDrawable(R.drawable.walldownstart));
        data[21].setImageDrawable(getResources().getDrawable(R.drawable.walldownright));
        data[22].setImageDrawable(getResources().getDrawable(R.drawable.walldown));
        data[23].setImageDrawable(getResources().getDrawable(R.drawable.walldown));
        data[24].setImageDrawable(getResources().getDrawable(R.drawable.walldownright));
    }
}