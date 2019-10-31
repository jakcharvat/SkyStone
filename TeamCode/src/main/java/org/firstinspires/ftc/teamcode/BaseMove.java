package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

class BaseMove {
    /// Declare a variable that will hold references to all the motors and sensors on the robot
    RobotSetup robotSetup;

    /**
     * A class used to control the basic movement of the robot in the 2d playing field
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     */
    BaseMove(final HardwareMap hardwareMap) {

        /// Initialize the motors using the [hardwareMap] passed in in the constructor
        robotSetup = new RobotSetup(hardwareMap);
    }

    void setPowerOnAll(double power) {
        setPowerOn(power, power);
    }

    private void setPowerOn(double left, double right) {

        left = Range.clip(left, -1.0, 1.0);
        right = Range.clip(right, -1.0, 1.0);

        robotSetup.getLeftFrontMotor().setPower(left);
        robotSetup.getRightFrontMotor().setPower(right);
        robotSetup.getLeftBackMotor().setPower(left);
        robotSetup.getRightBackMotor().setPower(right);
    }
}
