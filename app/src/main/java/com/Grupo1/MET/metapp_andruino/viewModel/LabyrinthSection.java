package com.Grupo1.MET.metapp_andruino.viewModel;

/**
 * This class represents every cell of the labyrinth with its attributes and methods.
 */
public class LabyrinthSection {
    /** The state of the cell (current, solution, etc.) */
    private LabyrinthSectionState state;
    /** The finishing cell has this attribute in true. */
    private boolean isTargetLocation;
    /** The number of times the robot passed this cell. */
    private int passed;
    /** The walls of the cell. */
    private boolean[] walls;

    /**
     * Getter for the passed variable.
     * @return the value of the variable passed.
     */
    public int getPassed() {
        return passed;
    }

    /**
     * Setter for the variable passed.
     * @param passed is the value that will be stored in passed of this class.
     */
    public void setPassed(int passed) {
        this.passed = passed;
    }

    /**
     * Constructor of the class.
     * @param state is the type of the cell.
     * @param isTargetLocation true if it is the last wall.
     * @param passed number of times the robot has passed across this cell.
     * @param walls the walls of the cell.
     */
    public LabyrinthSection(LabyrinthSectionState state, boolean isTargetLocation, int passed, boolean[] walls) {
        this.state = state;
        this.isTargetLocation = isTargetLocation;
        this.passed = passed;
        this.walls = walls;
    }

    /**
     * getter of the state variable.
     * @return state.
     */
    public LabyrinthSectionState getState() {
        return state;
    }

    /**
     * setter for the state variable.
     * @param state The state of the cell (current, solution, etc.).
     */
    public void setState(LabyrinthSectionState state) {
        this.state = state;
    }

    /**
     * getter of isTargetLocation
     * @return isTargetLocation
     */
    public boolean getIsTargetLocation() {
        return isTargetLocation;
    }

    /**
     * setter of isTargetLocation
     * @param targetLocation The finishing (last) cell has this attribute in true.
     */
    public void setTargetLocation(boolean targetLocation) {
        isTargetLocation = targetLocation;
    }

    /**
     * getter of the array of walls.
     * @returns the value of walls.
     */
    public boolean[] getWalls() {
        return walls;
    }

    /**
     * setter of walls.
     * @param walls are the walls of the cell.
     */
    public void setWalls(boolean[] walls) {
        this.walls = walls;
    }
}
