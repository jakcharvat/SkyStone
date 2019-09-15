package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;


public class Move {

    /// Choose base type. This makes it easily possible to switch between the three bases
    /// when we start testing on two or three at once.
    private BaseType type = BaseType.fourWheeler;

    /// Declare variables that will hold references to the motors. [hAcrossMotor] will be
    /// the motor in the perpendicular arm of the H Base in the final robot.
    private DcMotor leftFrontMotor, rightFrontMotor, leftBackMotor, rightBackMotor, hAcrossMotor;

    /**
     * A class used to control the basic movement of the robot in the 2d playing field
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     */
    Move(final HardwareMap hardwareMap) {

        /// Initialize the motors using the [hardwareMap] passed in in the constructor
        this.leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        this.rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");

        if (this.type != BaseType.twoWheeler) {
            this.leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
            this.rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");
        }

        if (this.type == BaseType.HBase) {
            this.hAcrossMotor = hardwareMap.dcMotor.get("hAcrossMotor");
        }

        /// Reverse the direction of certain motors. This is necessary because the motors
        /// aren't always put onto the robot in the same direction, and if the motor is
        /// set up to face backwards, then we need to reverse it. There isn't a way to
        /// know which motors to reverse as you code, the best way to do it is to write
        /// a simple program and run it to see which motors work as expected and which
        /// need to be reversed.
        if (this.type == BaseType.fourWheeler) {
            this.leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            this.leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else if (this.type == BaseType.twoWheeler) {
            this.rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

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

        leftFrontMotor.setPower(power);
        rightFrontMotor.setPower(power);

        if (type != BaseType.twoWheeler) {
            leftBackMotor.setPower(power);
            rightBackMotor.setPower(power);
        }
    }

    private void setPowerOn(double left, double right) {

        left = Range.clip(left, -1.0, 1.0);
        right = Range.clip(right, -1.0, 1.0);

        leftFrontMotor.setPower(left);
        rightFrontMotor.setPower(right);

        if (type != BaseType.twoWheeler) {
            leftBackMotor.setPower(left);
            rightBackMotor.setPower(right);
        }
    }

    enum BaseType {
        twoWheeler,
        fourWheeler,
        HBase
    }

}
