package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Gamepad Control", group = "Control")
public class GamepadControl extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        final Move move = new Move(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            move.gamepadTurn(gamepad1.right_stick_x, gamepad1.right_stick_y);
        }
    }
}
