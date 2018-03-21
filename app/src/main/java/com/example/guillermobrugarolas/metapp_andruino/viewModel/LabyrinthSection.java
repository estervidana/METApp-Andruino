package com.example.guillermobrugarolas.metapp_andruino.viewModel;

public class LabyrinthSection {

    private LabyrinthSectionState state;
    private boolean isTargetLocation;
    private boolean[] walls;

    public LabyrinthSection(LabyrinthSectionState state, boolean isTargetLocation, boolean[] walls) {
        this.state = state;
        this.isTargetLocation = isTargetLocation;
        this.walls = walls;
    }

    public LabyrinthSectionState getState() {
        return state;
    }

    public void setState(LabyrinthSectionState state) {
        this.state = state;
    }

    public boolean isTargetLocation() {
        return isTargetLocation;
    }

    public void setTargetLocation(boolean targetLocation) {
        isTargetLocation = targetLocation;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public void setWalls(boolean[] walls) {
        this.walls = walls;
    }
}
