package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.prefs.DiagonalSetup;

public class PlayStickGame extends LinearOpMode {

    private MoveMethods move;

    @Override
    public void runOpMode(){
        while (opModeIsActive()) {
            if (move.turn == 0){
                if (gamepad1.dpad_right) {
                    move.MoveRight(move.ballDistance);
                }
                if (gamepad1.dpad_left) {
                    move.MoveLeft(move.ballDistance);
                }
            }
            if (move.turn == 1){

            }
        }
    }
}
