package org.firstinspires.ftc.teamcode.stick_game;

import org.jetbrains.annotations.NotNull;

class Stick {

    Stick(int row, int stick, MoveManager manager) {
        this.stickNumber = stick;
        this.rowNumber = row;
    }

    final private int rowNumber;
    final private int stickNumber;
    private boolean isRemoved = false;

    void remove() {
        isRemoved = true;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getStickNumber() {
        return stickNumber;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    @NotNull
    @Override
    public String toString() {
        return isRemoved ? " " : "I";
    }
}
