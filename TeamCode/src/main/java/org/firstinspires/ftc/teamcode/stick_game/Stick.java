package org.firstinspires.ftc.teamcode.stick_game;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.jetbrains.annotations.NotNull;

class Stick {;
    Stick(int row, int stick, MoveManager manager) {
        this.stickNumber = stick;
        this.rowNumber = row;
        this.manager = manager;
    }

    final private int rowNumber;
    final private int stickNumber;
    final private MoveManager manager;
    private boolean isRemoved = false;

    void remove() {
        isRemoved = true;
        manager.knockStick();
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
