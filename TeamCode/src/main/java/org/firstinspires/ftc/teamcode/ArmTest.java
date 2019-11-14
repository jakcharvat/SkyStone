package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Arm Test", group = "Competition Robot")
public class ArmTest extends LinearOpMode {

    private ArmController armController;
    private RobotSetup robotSetup;

    private double currentHeight = 0.0;
    private double servoPosition = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        robotSetup = new RobotSetup(hardwareMap);
        armController = new ArmController(robotSetup);

        robotSetup.getClawServo().setDirection(Servo.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                currentHeight += 0.001;
                armController.runArmToHeight(currentHeight, false);
            }
            if (gamepad1.dpad_down) {
                currentHeight -= 0.001;
                armController.runArmToHeight(currentHeight, false);
            }
            if (gamepad1.dpad_left) {
                servoPosition += 0.0001;
                robotSetup.getClawServo().setPosition(servoPosition);
            }
            if (gamepad1.dpad_right) {
                servoPosition -= 0.0001;
                robotSetup.getClawServo().setPosition(servoPosition);
            }

            telemetry.addData("Servo", servoPosition);
            telemetry.addData("Height", currentHeight);
            telemetry.update();
        }
    }
}
