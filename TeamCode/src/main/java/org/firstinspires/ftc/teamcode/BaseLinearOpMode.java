package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

/**
 * A class used to control the basic movement of the robot in the 2d playing field. Override the {@code opModeCycle()}
 * method to define logic that would normally be defined in {@code runOpMode()}
 */
abstract class BaseLinearOpMode extends LinearOpMode {

    /// Declare a variable that will hold references to all the motors and sensors on the robot
    RobotSetup robotSetup;

    /**
     * Sets the power on the four main movement motors (excluding the perpendicular motor)
     *
     * @param power
     */
    void setPowerOnMain(double power) {
        setPowerOn(power, power, 0.0);
    }

    private void setPowerOn(double left, double right, double perpendicular) {

        left = Range.clip(left, -1.0, 1.0);
        right = Range.clip(right, -1.0, 1.0);
        perpendicular = Range.clip(perpendicular, -1.0, 1.0);

        robotSetup.getLeftFrontMotor().setPower(left);
        robotSetup.getRightFrontMotor().setPower(right);
        robotSetup.getLeftBackMotor().setPower(left);
        robotSetup.getRightBackMotor().setPower(right);
        robotSetup.getPerpendicularMotor().setPower(perpendicular);
    }

    abstract void opModeCycle() throws InterruptedException;

    @Override
    public void runOpMode() throws InterruptedException {
        /// Initialize the motors using the [hardwareMap] provided by LinearOpMode
        robotSetup = new RobotSetup(hardwareMap);

        /// Run the user code
        opModeCycle();
    }
}
