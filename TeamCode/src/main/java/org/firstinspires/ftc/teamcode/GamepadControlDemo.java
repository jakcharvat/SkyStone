package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Gamepad Control Demo", group = "Demos")

/*
  A very incomplete implementation of the ability to control the robot
  using the xbox controllers. Right now this only takes the input from
  the
 */
public class GamepadControlDemo extends LinearOpMode {

    /// This is where we declare private variables that we will use further on
    /// in the program
    private ElapsedTime runtime = new ElapsedTime();
    private Move move;

    @Override
    public void runOpMode() throws InterruptedException {
        /// The following two statements are act like System.out.println but
        /// on the phone controllers
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /// Get a reference to the [Move] class defined in the Move class.
        move = new Move(hardwareMap);

        /// Wait for the driver to start and reset the timer
        waitForStart();
        runtime.reset();

        /// Run the following code until stopped by the driver
        while (opModeIsActive()) {

            /*
            Get the value of the right stick along the y (up - down) axis
            (which is provided as a double between -1.0 and 1.0) and pass
            it to [move.straight()] as the power parameter. This will update
            the drive strength several times per second and ensure continuous
            updates from the gamepad.

            There are issues with this, for example if the robot is top-heavy
            and there is a sudden change in power there is a chance the robot
            would flip. Possible ways to solve that are making sure it
            decelerates at some reasonable rate, or giving it a sorta gearbox
            and multiple speeds. I'll let you guys explore those possibilities
             */
            move.straight(gamepad1.right_stick_y);

            /// Show the elapsed game time
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
