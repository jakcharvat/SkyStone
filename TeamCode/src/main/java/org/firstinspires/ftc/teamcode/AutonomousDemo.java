package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/// This ensures that this class appears on the menu on the controller
/// so it can be run.
@Autonomous(name = "Autonomous Demo", group = "Demos")

public class AutonomousDemo extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        /// The following two statements are act like System.out.println but
        /// on the phone controllers
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /// Get a reference to the [TeleMove] class defined in the TeleMove class.
        /// This is where we declare private variables that we will use further on
        /// in the program
        AutoMove autoMove = new AutoMove(hardwareMap);

        /// Wait for the driver to start
        waitForStart();

        /// TeleMove forward at half power for 2 seconds and then stop
        autoMove.straight(-0.5, 1.0);
        Thread.sleep(2000);
        autoMove.stop();

        /// TeleMove back at full power for 1 second, then stop
        autoMove.straight(1.0, 1.0);
    }
}
