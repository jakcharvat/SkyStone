package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@SuppressWarnings("FieldCanBeLocal")
@TeleOp(name = "Arm Test", group = "Competition Robot")
public class ArmTest extends LinearOpMode {

    private CollectionController controller;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = new CollectionController(hardwareMap);

        boolean downPressed = false;
        boolean upPressed = false;
        boolean aPressed = false;

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                if (upPressed) continue;

                controller.runArmToHeight(controller.getCurrentHeight() + 2);
                upPressed = true;
            } else {
                upPressed = false;
            }

            if (gamepad1.dpad_down) {
                if (downPressed) continue;

                controller.runArmToHeight(controller.getCurrentHeight() - 2);
                downPressed = true;
            }
            else {
                downPressed = false;
            }

            if (gamepad1.a) {
                if (aPressed) continue;

                controller.lowerArmToBottom();
                aPressed = true;
            } else {
                aPressed = false;
            }

            telemetry.addData("Touch: ", controller.getRobotSetup().getArmTouchSensor().isPressed());
            telemetry.addData("Dir: ", controller.getRobotSetup().getArmMotor().getDirection());
            telemetry.addData("Height: ", controller.getCurrentHeight());
            telemetry.update();
        }

    }
}
