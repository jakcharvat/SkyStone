package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Full Tele", group = "Competition")
public class FullTele extends TeleLinearOpMode {
    @Override
    void opModeCycle() throws InterruptedException {
        final CollectionController controller = new CollectionController(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            gamepadDrive(gamepad1);

            controller.gamepadHandler(gamepad1, telemetry);
        }
    }
}
