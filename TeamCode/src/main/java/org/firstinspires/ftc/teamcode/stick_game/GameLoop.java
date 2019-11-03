package org.firstinspires.ftc.teamcode.stick_game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class GameLoop extends LinearOpMode {

    GameManager manager = new GameManager(4);

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
