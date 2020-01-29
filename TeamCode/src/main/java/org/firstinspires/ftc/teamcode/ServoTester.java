package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "ServoTester", group = "Tests")
public class ServoTester extends LinearOpMode {

    private Servo servo;

    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.servo.get("servo");

        waitForStart();

        boolean ltPressed = false, rtPressed = false, lbPressed = false, rbPressed = false;
        while (opModeIsActive()) {
            if (gamepad1.left_trigger > 0.5) {
                if (ltPressed) continue;

                servo.setPosition(Range.clip(servo.getPosition() + 0.1, 0, 1));
                ltPressed = true;
            } else ltPressed = false;

            if (gamepad1.right_trigger > 0.5) {
                if (rtPressed) continue;

                servo.setPosition(Range.clip(servo.getPosition() - 0.1, 0, 1));
                rtPressed = true;
            } else rtPressed = false;

            if (gamepad1.left_bumper) {
                if (lbPressed) continue;

                servo.setPosition(Range.clip(servo.getPosition() + 0.01, 0, 1));
                lbPressed = true;
            } else lbPressed = false;

            if (gamepad1.right_bumper) {
                if (rbPressed) continue;

                servo.setPosition(Range.clip(servo.getPosition() - 0.01, 0, 1));
                rbPressed = true;
            } else rbPressed = false;

            telemetry.addData("Servo Position", servo.getPosition());
            telemetry.update();
        }
    }
}
