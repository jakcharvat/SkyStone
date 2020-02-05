package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@SuppressWarnings("FieldCanBeLocal")
@TeleOp(name = "Arm Test", group = "Competition Robot")
public class ArmTest extends BaseLinearOpMode {

    private CollectionController controller;

    @Override
    void opModeCycle() throws InterruptedException, Exception {
        final Servo claw = hardwareMap.servo.get("clawServo");
        final Servo arm = hardwareMap.servo.get("armServo");
        final DcMotor motor = hardwareMap.dcMotor.get("armMotor");

        controller = new CollectionController(hardwareMap);

        waitForStart();

        boolean aup = false;
        boolean adn = false;
        boolean mup = false;
        boolean mdn = false;
        boolean clo = false;
        boolean clc = false;
        boolean a = false;

        while (opModeIsActive()) {

            if (gamepad1.a) {
                if (a) continue;
                controller.lowerArmToBottom(gamepad1);
                a = true;
            } else a = false;

            if (gamepad1.dpad_up) {
                if (mup) continue;
                controller.runArmToHeight(controller.getCurrentHeight() + 1.0, gamepad1);
                mup = true;
            } else mup = false;

            if (gamepad1.dpad_down) {
                if (mdn) continue;
                controller.runArmToHeight(controller.getCurrentHeight() - 1.0, gamepad1);
                mdn = true;
            } else mdn = false;

            if (gamepad1.left_trigger > 0.8) {
                if (aup) continue;
                robotSetup.getArmServo().setPosition(robotSetup.getArmServo().getPosition() - 0.1);
                aup = true;
            } else aup = false;

            if (gamepad1.left_bumper) {
                if (adn) continue;
                robotSetup.getArmServo().setPosition(robotSetup.getArmServo().getPosition() + 0.1);
                adn = true;
            } else adn = false;

            if (gamepad1.right_trigger > 0.8) {
                if (clo) continue;
                robotSetup.getClawServo().setPosition(robotSetup.getClawServo().getPosition() + 0.1);
                clo = true;
            } else clo = false;

            if (gamepad1.right_bumper) {
                if (clc) continue;
                robotSetup.getClawServo().setPosition(robotSetup.getClawServo().getPosition() - 0.1);
                clc = true;
            } else clc = false;

            telemetry.addData("Arm", robotSetup.getArmServo().getPosition());
            telemetry.addData("Claw", robotSetup.getClawServo().getPosition());
            telemetry.addData("Motor", controller.getCurrentHeight());
            telemetry.update();
        }
    }
}
