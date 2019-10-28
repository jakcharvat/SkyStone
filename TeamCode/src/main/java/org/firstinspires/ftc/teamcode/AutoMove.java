package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.prefs.BaseType;
import org.firstinspires.ftc.teamcode.prefs.RobotSetup;

@SuppressWarnings("StatementWithEmptyBody")
class AutoMove extends TeleMove {

    private RobotSetup robotSetup;

    /**
     * A class used to control the basic movement of the robot in the 2d playing field
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     */
    AutoMove(HardwareMap hardwareMap) {
        super(hardwareMap);
        robotSetup = new RobotSetup(hardwareMap);
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

    void straight(final double power, double forRotations) throws InterruptedException {

        forRotations = Math.abs(forRotations);

        if (power < 0) {
            forRotations = -forRotations;
        }

        if (robotSetup.baseType() == BaseType.twoWheeler) {

            setupMotorEncoder(robotSetup.twoWheelerSetup().leftMotor(), forRotations);
            setupMotorEncoder(robotSetup.twoWheelerSetup().rightMotor(), forRotations);

        } else if (robotSetup.baseType() == BaseType.fourWheeler) {

            setupMotorEncoder(robotSetup.fourWheelerSetup().leftFrontMotor(), forRotations);
            setupMotorEncoder(robotSetup.fourWheelerSetup().leftBackMotor(), forRotations);
            setupMotorEncoder(robotSetup.fourWheelerSetup().rightFrontMotor(), forRotations);
            setupMotorEncoder(robotSetup.fourWheelerSetup().rightBackMotor(), forRotations);

        }

        straight(power);

        if (robotSetup.baseType() == BaseType.twoWheeler) {

            while (robotSetup.twoWheelerSetup().leftMotor().isBusy() ||
                    robotSetup.twoWheelerSetup().rightMotor().isBusy()) { }

        } else if (robotSetup.baseType() == BaseType.fourWheeler) {

            while (robotSetup.fourWheelerSetup().leftFrontMotor().isBusy() ||
                    robotSetup.fourWheelerSetup().leftBackMotor().isBusy() ||
                    robotSetup.fourWheelerSetup().rightFrontMotor().isBusy() ||
                    robotSetup.fourWheelerSetup().rightBackMotor().isBusy()) { }

        }

        stop();

        if (robotSetup.baseType() == BaseType.twoWheeler) {

            returnMotorEncoder(robotSetup.twoWheelerSetup().leftMotor());
            returnMotorEncoder(robotSetup.twoWheelerSetup().rightMotor());

        } else if (robotSetup.baseType() == BaseType.fourWheeler) {

            returnMotorEncoder(robotSetup.fourWheelerSetup().leftFrontMotor());
            returnMotorEncoder(robotSetup.fourWheelerSetup().leftBackMotor());
            returnMotorEncoder(robotSetup.fourWheelerSetup().rightFrontMotor());
            returnMotorEncoder(robotSetup.fourWheelerSetup().rightBackMotor());

        }
    }

    private void setupMotorEncoder(final DcMotor motor, final double rotations) {

        final double ticksInRotation = 1120;

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(Math.round((float) (rotations * ticksInRotation)));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void returnMotorEncoder(final DcMotor motor) {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
