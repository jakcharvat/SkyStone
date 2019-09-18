package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.prefs.BaseType;
import org.firstinspires.ftc.teamcode.prefs.RobotSetup;


public class Move {

    /// Choose base type. This makes it easily possible to switch between the three bases
    /// when we start testing on two or three at once.
    private BaseType type = BaseType.fourWheeler;


    /// Declare a variable that will hold references to all the motors and sensors on the robot
    private RobotSetup robotSetup;

    /**
     * A class used to control the basic movement of the robot in the 2d playing field
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     */
    Move(final HardwareMap hardwareMap) {

        /// Initialize the motors using the [hardwareMap] passed in in the constructor
        robotSetup = new RobotSetup(hardwareMap);
    }

    /**
     * Starts moving the robot straight
     *
     * The [power] parameter controls the speed **and** direction of the motion. If [power]
     * is positive then the robot will move forward, if negative it will move backward
     *
     * @param power desired power of the motor as a double between -1.0 and 1.0
     */
    void straight(final double power) {
        setPowerOnAll(power);
    }

    /**
     * Starts moving the robot straight
     *
     * The [power] parameter controls the speed **and** direction of the motion. If [power]
     * is positive then the robot will move forward, if negative it will move backward
     *
     * The parameter [forMilliseconds] must be provided at all times. The motors
     * will run forward for the specified duration and then stop. Note that this
     * variable must be a positive integer or 0
     *
     * @param power           desired power of the motor as a double between -1.0 and 1.0
     * @param forMilliseconds how long the robot should move before stopping in milliseconds
     */
    void straight(final double power, final int forMilliseconds) throws InterruptedException {
        straight(power);

        Thread.sleep(forMilliseconds);

        stop();
    }

    /**
     * Immediately stop all motion of the robot
     */
    void stop() {
        setPowerOnAll(0.0);
    }

    void gamepadTurn(final double x, final double y) {

        /// Since the joysticks don't move in a square field, but rather a circular one, I use the
        /// equation of a circle here to calculate the fractional power the robot should move at
        /// as a distance from the current position of the stick to the origin
        final double power = Range.clip(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)), 0, 1);
        final double turn = Math.abs(x);

        final boolean shouldTurnRight = x >= 0;
        final boolean shouldMoveForward = y >= 0;

        double right, left;

        if (shouldTurnRight) {
            left = power;
            right = power - (power * turn);
        } else {
            left = power - (power * turn);
            right = power;
        }

        if (!shouldMoveForward) {
            left = -left;
            right = -right;
        }

        setPowerOn(left, right);

    }

    private void setPowerOnAll(double power) {

        power = Range.clip(power, -1.0, 1.0);

        setPowerOn(power, power);
    }

    private void setPowerOn(double left, double right) {

        left = Range.clip(left, -1.0, 1.0);
        right = Range.clip(right, -1.0, 1.0);

        if (robotSetup.baseType() == BaseType.twoWheeler) {

            robotSetup.twoWheelerSetup().leftMotor().setPower(left);
            robotSetup.twoWheelerSetup().rightMotor().setPower(right);

        } else if (robotSetup.baseType() == BaseType.fourWheeler) {

            robotSetup.fourWheelerSetup().leftFrontMotor().setPower(left);
            robotSetup.fourWheelerSetup().rightFrontMotor().setPower(right);
            robotSetup.fourWheelerSetup().leftBackMotor().setPower(left);
            robotSetup.fourWheelerSetup().rightBackMotor().setPower(right);

        } else if (robotSetup.baseType() == BaseType.hBase) {

            // Todo

        }
    }
}
