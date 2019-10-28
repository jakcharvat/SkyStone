package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.prefs.BaseType;
import org.firstinspires.ftc.teamcode.prefs.RobotSetup;
import org.firstinspires.ftc.teamcode.utils.UtilFunctions;
import org.firstinspires.ftc.teamcode.utils.Power;


class TeleMove {
    /// Declare a variable that will hold references to all the motors and sensors on the robot
    private RobotSetup robotSetup;

    /**
     * A class used to control the basic movement of the robot in the 2d playing field
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     */
    TeleMove(final HardwareMap hardwareMap) {

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
     * Immediately stop all motion of the robot
     */
    void stop() {
        setPowerOnAll(0.0);
    }

    void gamepadTurn(double x, double y) {

        y = -y;
        x = -x;

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
            right = Range.clip(power - (2 * power * turn), -1, 1);
        } else {
            left = Range.clip(power - (2 * power * turn), -1, 1);
            right = power;
        }

        if (!shouldMoveForward) {
            double leftTemp = left;

            left = -right;
            right = -leftTemp;
        }

        setPowerOn(left, right);

    }

    /**
     * This method is only supposed to work on the diagonal based robot. It moves the robot in the
     * direction specified by one of the sticks on the controller, and turns it in the direction
     * specified by the x-axis position of the other stick
     *
     * WARNING: As of now it only moves in the specified direction, doesn't turn
     *
     * @param xMove the x-axis position of the stick controlling the motion of the robot
     * @param yMove the y-axis position of the stick controlling the motion of the robot
     * @param xTurn the x-axis position of the stick controlling the turning of the robot
     */
    void diagonalBaseGamepadMove(double xMove, double yMove, double xTurn, Telemetry telemetry) {

        // Kill the execution of the method if a base type other than diagonal is selected,
        // to avoid NPEs and other disgusting behaviour later on in the method
        if (robotSetup.baseType() != BaseType.diagonal) {
            telemetry.addData("Quitting diagonal move", "true");
            telemetry.update();
            return;
        }

        UtilFunctions utilFunctions = new UtilFunctions();

        yMove = -yMove;
        xMove = -xMove;
        xTurn = -xTurn;

        final double angle = utilFunctions.calculateJoystickAngle(xMove, yMove);
        final double power = Range.clip(Math.sqrt(Math.pow(xMove, 2) + Math.pow(yMove, 2)), 0, 1);

        Power individualMotorPower = utilFunctions.calculateMotorSettingsNeededToAchieveAngleAndPower(angle, power);

        individualMotorPower = utilFunctions.adaptMotorSettingsToTurn(individualMotorPower, xTurn);

        robotSetup.diagonalSetup().leftFrontMotor().setPower(individualMotorPower.getLeftFront());
        robotSetup.diagonalSetup().leftBackMotor().setPower(individualMotorPower.getLeftBack());
        robotSetup.diagonalSetup().rightFrontMotor().setPower(individualMotorPower.getRightFront());
        robotSetup.diagonalSetup().rightBackMotor().setPower(individualMotorPower.getRightBack());
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

    public RobotSetup getRobotSetup() {
        return robotSetup;
    }
}
