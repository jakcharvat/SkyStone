package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

class ArmTest extends LinearOpMode {

    private ArmController armController;
    private RobotSetup robotSetup;

    private double currentHeight = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        robotSetup = new RobotSetup(hardwareMap);
        armController = new ArmController(robotSetup);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                currentHeight += 10;
                armController.runArmToHeight(currentHeight, false);
            }
            if (gamepad1.dpad_down) {
                currentHeight -= 10;
                armController.runArmToHeight(currentHeight, false);
            }
        }
    }
}
