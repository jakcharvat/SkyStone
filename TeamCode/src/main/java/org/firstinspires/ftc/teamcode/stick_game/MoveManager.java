package org.firstinspires.ftc.teamcode.stick_game;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.prefs.BaseType;
import org.firstinspires.ftc.teamcode.prefs.DiagonalSetup;
import org.firstinspires.ftc.teamcode.prefs.RobotSetup;
import org.firstinspires.ftc.teamcode.stick_game.utils.StickCoordinate;
import org.firstinspires.ftc.teamcode.utils.Power;
import org.firstinspires.ftc.teamcode.utils.UtilFunctions;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("StatementWithEmptyBody")
class MoveManager {

    MoveManager(@NotNull HardwareMap map, Telemetry telemetry) {

        this.telemetry = telemetry;

        RobotSetup robotSetup = new RobotSetup(map, BaseType.diagonal);
        DiagonalSetup diagonalSetup = robotSetup.diagonalSetup();

        leftFrontMotor = diagonalSetup.leftFrontMotor();
        rightFrontMotor = diagonalSetup.rightFrontMotor();
        leftBackMotor = diagonalSetup.leftBackMotor();
        rightBackMotor = diagonalSetup.rightBackMotor();
//        upDownMotor = diagonalSetup.upDownMotor();
//        woundUpDownMotor = diagonalSetup.woundUpDownMotor();
    }

    final private Telemetry telemetry;

    // IMPORTANT: All measurements should be in rad for angles and cm for distances.
    private StickCoordinate currentStick = new StickCoordinate(3, 0);
    private StickCoordinate targetStick = new StickCoordinate(3, 0);

    private int verticalDistanceBetweenSticks = 15;
    private int horizontalDistanceBetweenSticks = 25;

    private int ticksInRotation = 1120;
    private double wheelCircumference = 10 * Math.PI;
    private int armHeight;

    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
//    private DcMotor upDownMotor;
//    private DcMotor woundUpDownMotor;

    void moveLeft() {
        targetStick.moveTo(currentStick.rowNumber(), currentStick.stickNumber() - 1);
        moveToStick(false);
    }

    void moveRight() {
        targetStick.moveTo(currentStick.rowNumber(), currentStick.stickNumber() + 1);
        moveToStick(false);
    }

    void moveUp() {
        targetStick.moveTo(currentStick.rowNumber() - 1, currentStick.stickNumber());
        moveToStick(false);
    }

    void moveDown() {
        targetStick.moveTo(currentStick.rowNumber() + 1, currentStick.stickNumber());
        moveToStick(false);
    }

    void moveToStickCoordinate(int row, int stick) {
        targetStick.moveTo(row, stick);
        moveToStick(true);
    }

    private void moveToStick(boolean withRemoval) {
        int moveRightBy = targetStick.stickNumber() - currentStick.stickNumber();
        int moveUpBy = currentStick.rowNumber() - targetStick.rowNumber();

        currentStick.moveTo(targetStick.rowNumber(), targetStick.stickNumber());

        try {
            if (moveRightBy != 0) stickMove(moveRightBy, MoveDirection.leftRight, withRemoval);
            if (moveUpBy != 0) stickMove(moveUpBy, MoveDirection.upDown, withRemoval);
        } catch (Exception e) {
            //
        }

    }

    private int calculateTurnTicksForMoving(int sticks, MoveDirection direction) {
        if (direction == MoveDirection.upDown) {
            //TODO

            return 0;
        }

        double moveRightDistance = sticks * verticalDistanceBetweenSticks * Math.sqrt(2);
        double moveByTurns = moveRightDistance / wheelCircumference;

        return (int) Math.round(moveByTurns * ticksInRotation);
    }

    private void stickMove(int sticks, MoveDirection direction, boolean withRemoval) throws InterruptedException {

        int ticks = calculateTurnTicksForMoving(sticks, direction);

        telemetry.addData("Ticks", ticks);

        if (direction == MoveDirection.leftRight) {

            boolean moveRight = ticks > 0;

            ticks = Math.abs(ticks);

            DcMotor[] motors = new DcMotor[]{leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor};

            if (moveRight) {
                leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            }

            for (DcMotor motor : motors) {
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setTargetPosition(ticks);
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            for (DcMotor motor : motors) {
                motor.setPower(0.25);
            }

            for (DcMotor motor : motors) {
                while(motor.isBusy()) { }
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setDirection(DcMotorSimple.Direction.FORWARD);
            }

            if (withRemoval) knockStick();

            return;
        }

        //TODO

    }

    void knockStick() {

        final UtilFunctions utilFunctions = new UtilFunctions();

        for (int i = 0; i < 2; i++) {

            double angle = i * Math.PI;

            Power power = utilFunctions.calculateMotorSettingsNeededToAchieveAngleAndPower(angle, 0.25);
            final double lfPower = power.getLeftFront();
            final double lbPower = power.getLeftBack();
            final double rfPower = power.getRightFront();
            final double rbPower = power.getRightBack();
            final double[] powers = new double[]{lfPower, lbPower, rfPower, rbPower};

            DcMotor[] motors = new DcMotor[]{leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor};
            int ticks = Math.round(ticksInRotation / 6);

            for (int j = 0; j < motors.length; j++) {
                DcMotor motor = motors[j];
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor.setTargetPosition(powers[j] < 0 ? -ticks : ticks);
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            for (int j = 0; j < motors.length; j++) {
                double motorPower = powers[j];
                DcMotor motor = motors[j];
                motor.setPower(motorPower);
            }

            for (DcMotor motor : motors) {
                while(motor.isBusy()) { }
                motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
        }
    }

    public StickCoordinate currentStick() {
        return currentStick;
    }
}

enum MoveDirection {
    leftRight,
    upDown,
}
