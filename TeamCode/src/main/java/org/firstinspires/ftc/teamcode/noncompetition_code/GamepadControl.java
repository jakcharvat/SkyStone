package org.firstinspires.ftc.teamcode.noncompetition_code;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.noncompetition_code.prefs.BaseType;

@TeleOp(name = "Gamepad Control", group = "Control")
public class GamepadControl extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        final TeleMove teleMove = new TeleMove(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            if (teleMove.getRobotSetup().baseType() == BaseType.diagonal) {
                teleMove.diagonalBaseGamepadMove(gamepad1.left_stick_y < 0 ? gamepad1.right_stick_x : -gamepad1.right_stick_x, gamepad1.left_stick_y, gamepad1.left_stick_x, telemetry);
            } else {
                teleMove.gamepadTurn(gamepad1.right_stick_x, gamepad1.right_stick_y);
            }
        }
    }
}
