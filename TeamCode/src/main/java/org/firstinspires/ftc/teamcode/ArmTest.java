package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@SuppressWarnings("FieldCanBeLocal")
@TeleOp(name = "Arm Test", group = "Competition Robot")
public class ArmTest extends LinearOpMode {

    private RobotSetup robotSetup;

    private double armPosition = 0.0;

    private boolean upPressed = false;
    private boolean downPressed = false;

    @Override
    public void runOpMode() throws InterruptedException {

        robotSetup = new RobotSetup(hardwareMap);

        robotSetup.getClawServo().setDirection(Servo.Direction.FORWARD);
        robotSetup.getArmServo().setDirection(Servo.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            hardwareMap.dcMotor.get("armMotor").setPower(gamepad1.left_stick_y / 5);

            if (gamepad1.a) robotSetup.getClawServo().setPosition(ArmController.OPEN_SERVO_POSITION);
            if (gamepad1.b) robotSetup.getClawServo().setPosition(ArmController.CLOSE_SERVO_POSITION);

            if (gamepad1.dpad_up && !upPressed) {
                armPosition += 0.1;
                robotSetup.getArmServo().setPosition(armPosition);
                upPressed = true;
            } else if (!gamepad1.dpad_up) upPressed = false;

            if (gamepad1.dpad_down && !downPressed) {
                armPosition -= 0.1;
                robotSetup.getArmServo().setPosition(armPosition);
                downPressed = true;
            } else if (!gamepad1.dpad_down) downPressed = false;

            telemetry.addData("Arm Servo", armPosition);
            telemetry.addData("Arm Position", robotSetup.getArmServo().getPosition());
            telemetry.addData("upPressed", upPressed);
            telemetry.addData("downPressed", downPressed);
            telemetry.update();
        }
    }
}
