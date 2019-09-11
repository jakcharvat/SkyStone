package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;


public class Move {

    /// Declare variables that will hold references to the motors
    private DcMotor leftFrontMotor, rightFrontMotor, leftBackMotor, rightBackMotor;

    /**
     * A class used to control the basic movement of the robot in the 2d playing field
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     */
    Move(final HardwareMap hardwareMap) {

        /// Initialize the motors using the [hardwareMap] passed in in the constructor
        this.leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        this.rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        this.leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        this.rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");

        this.leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
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

    private void setPowerOnAll(double power) {

        power = Range.clip(power, -1.0, 1.0);

        this.leftFrontMotor.setPower(power);
        this.rightFrontMotor.setPower(power);
        this.leftBackMotor.setPower(power);
        this.rightBackMotor.setPower(power);
    }

    private void setPowerOn(double left, double right) {

        left = Range.clip(left, -1.0, 1.0);
        right = Range.clip(right, -1.0, 1.0);

        this.leftFrontMotor.setPower(left);
        this.leftBackMotor.setPower(left);
        this.rightFrontMotor.setPower(right);
        this.rightBackMotor.setPower(right);
    }

}
