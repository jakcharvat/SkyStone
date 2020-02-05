package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("StatementWithEmptyBody")
abstract class AutoLinearOpMode extends TeleLinearOpMode {

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
    protected void straight(final double power, final int forMilliseconds) throws InterruptedException {
        straight(power);

        Thread.sleep(forMilliseconds);

        stopMotion();
    }

    protected  void turn(final double power, final double forRotations, final TurnDirection direction) {
        double leftRotations = Math.abs(forRotations);
        double rightRotations = Math.abs(forRotations);


        if (power < 0) {
            leftRotations = -leftRotations;
            rightRotations = -rightRotations;
        }

        double leftPower = power;
        double rightPower = power;

        if (direction == TurnDirection.left) {
            leftRotations = -leftRotations;
            leftPower = -leftPower;
        } else {
            rightRotations = -rightRotations;
            rightPower = -rightPower;
        }


        setupMotorEncoder(robotSetup.getLeftFrontMotor(), leftRotations);
        setupMotorEncoder(robotSetup.getLeftBackMotor(), leftRotations);
        setupMotorEncoder(robotSetup.getRightFrontMotor(), rightRotations);
        setupMotorEncoder(robotSetup.getRightBackMotor(), rightRotations);

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        setPowerOn(leftPower, rightPower, 0.0);

        while (robotSetup.getLeftFrontMotor().isBusy() ||
                robotSetup.getLeftBackMotor().isBusy() ||
                robotSetup.getRightFrontMotor().isBusy() ||
                robotSetup.getRightBackMotor().isBusy()) {
            if (timer.time(TimeUnit.SECONDS) > 2.0) break;
        }

        stopMotion();

        returnMotorEncoder(robotSetup.getLeftFrontMotor());
        returnMotorEncoder(robotSetup.getLeftBackMotor());
        returnMotorEncoder(robotSetup.getRightFrontMotor());
        returnMotorEncoder(robotSetup.getRightBackMotor());
    }

    protected void straight(final double power, double forRotations) {

        forRotations = Math.abs(forRotations);

        if (power < 0) {
            forRotations = -forRotations;
        }

        setupMotorEncoder(robotSetup.getLeftFrontMotor(), forRotations);
        setupMotorEncoder(robotSetup.getLeftBackMotor(), forRotations);
        setupMotorEncoder(robotSetup.getRightFrontMotor(), forRotations);
        setupMotorEncoder(robotSetup.getRightBackMotor(), forRotations);

        straight(power);

        while (robotSetup.getLeftFrontMotor().isBusy() ||
                robotSetup.getLeftBackMotor().isBusy() ||
                robotSetup.getRightFrontMotor().isBusy() ||
                robotSetup.getRightBackMotor().isBusy()) {
            telemetry.addData("Distance: ", robotSetup.getRightDistanceSensor().getDistance(DistanceUnit.CM));
            telemetry.update();
        }

        stopMotion();

        returnMotorEncoder(robotSetup.getLeftFrontMotor());
        returnMotorEncoder(robotSetup.getLeftBackMotor());
        returnMotorEncoder(robotSetup.getRightFrontMotor());
        returnMotorEncoder(robotSetup.getRightBackMotor());
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

enum TurnDirection {
    left,
    right,
}
