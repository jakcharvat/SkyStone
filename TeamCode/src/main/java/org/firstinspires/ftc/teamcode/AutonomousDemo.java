package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/// This ensures that this class appears on the menu on the controller
/// so it can be run.
@Autonomous(name = "Autonomous Demo", group = "Demos")

public class AutonomousDemo extends LinearOpMode {

    /// This is where we declare private variables that we will use further on
    /// in the program
    private Move move;


    @Override
    public void runOpMode() throws InterruptedException {

        /// The following two statements are act like System.out.println but
        /// on the phone controllers
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /// Get a reference to the [Move] class defined in the Move class.
        move = new Move(hardwareMap);

        /// Wait for the driver to start
        waitForStart();

        /// Move forward at half power for 2 seconds and then stop
        move.straight(0.5);
        wait(2000);
        move.stop();

        /// Move back at full power for 1 second, then stop
        move.straight(-1.0, 1000);
    }
}
