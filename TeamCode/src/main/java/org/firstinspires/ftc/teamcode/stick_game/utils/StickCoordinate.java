package org.firstinspires.ftc.teamcode.stick_game.utils;

public class StickCoordinate {

    //region Constructor
    public StickCoordinate(int row, int stick) {
        this.rowNumber = row;
        this.stickNumber = stick;
    }
    //endregion

    //region Properties
    private int rowNumber;
    private int stickNumber;
    //endregion

    public void moveToStick(int row, int stick) {

        int numberOfSticksInTargetRow = (2 * row) + 1;

        if (stick < 0) {
            rowNumber = row;
            stickNumber = 0;
        } else if (stick < numberOfSticksInTargetRow) {
            rowNumber = row;
            stickNumber = stick;
        } else {
            rowNumber = row;
            stickNumber = numberOfSticksInTargetRow - 1;
        }
    }

    //region Getters
    public int rowNumber() { return rowNumber; }
    public int stickNumber() { return stickNumber; }
    //endregion

}
