package com.Grupo1.MET.metapp_andruino.viewModel;

/**
 * This class represents every cell of the labyrinth with its attributes and methods.
 */
public class LabyrinthSection {
    /** The state of the cell (current, solution, etc.) */
    private LabyrinthSectionState state;
    /** The finish cell has this attribute in true. */
    private boolean isTargetLocation;
    /** The number of times the robot passed this cell. */
    private int passed;
    /** The walls of the cell. */
    private boolean[] walls;

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public LabyrinthSection(LabyrinthSectionState state, boolean isTargetLocation, int passed, boolean[] walls) {
        this.state = state;
        this.isTargetLocation = isTargetLocation;
        this.passed = passed;

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
