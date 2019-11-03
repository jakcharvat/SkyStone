package org.firstinspires.ftc.teamcode.stick_game.utils;

import org.jetbrains.annotations.Contract;

public class StickCoordinate {

    //region Constructor
    public StickCoordinate(int row, int stick) {
        this.rowNumber = row;
        this.stickNumber = stick;
    }
    //endregion

    //region Properties
    private final int rowNumber;
    private final int stickNumber;
    //endregion

    //region Getters
    public int rowNumber() { return rowNumber; }
    public int stickNumber() { return stickNumber; }
    //endregion

}
