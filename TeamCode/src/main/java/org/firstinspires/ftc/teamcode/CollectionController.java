package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@SuppressWarnings("ALL")
class CollectionController {

    /**
     * Class that handles all the actions of the robot's arm, including raising and lowering it,
     * picking up blocks and placing them on the tower. Also controls the pickup mechanism
     * and the claw.
     *
     * @param robotSetup
     */
    CollectionController(RobotSetup robotSetup) {
        this.robotSetup = robotSetup;
    }

    /**
     * Class that handles all the actions of the robot's arm, including raising and lowering it,
     * picking up blocks and placing them on the tower. Also controls the pickup mechanism
     * and the claw.
     *
     * @param hardwareMap
     */
    CollectionController(HardwareMap hardwareMap) { this.robotSetup = new RobotSetup(hardwareMap); }

    private RobotSetup robotSetup;

    private static Utils utils = new Utils();

    //IMPORTANT: All measurements in cm / rad

    /**
     * The speed of the arm when moving both up and down, regardless of the distance to travel.
     * Positive should represent winding up the string, therefore moving the arm up, negative down.
     */
    private static final double ARM_UP_MOTION_SPEED = 0.8;
    private static final double ARM_DOWN_MOTION_SPEED = -0.4;

    /**
     * Ticks in the encoder of the motor raising and lowering the arm
     */
    private static final double ARM_MOTOR_TICKS = 1120;

    /**
     * Circumference of the block the string raising and lowering the hand is wound up on.
     * Used to calculate by how much to rotate the motor to reach a certain height
     */
    private static final double WIND_UP_COIL_CIRCUMFERENCE = 13.34;

    /**
     * Maximum possible height of the arm
     */
    private static final double MAXIMUM_ARM_HEIGHT = 68;

    /**
     * Ratio of the small and big gears driving the arm rotation
     */
    private static final double ARM_ANGLE_SERVO_GEAR_RATIO = 5;

    /**
     * Angle in which the arm is in a standard up position - meaning in a position in
     * which the stone is a standard length away from the robot and also a standard
     * height in relation to how much the arm was risen
     */
    private static final double STANDARD_ARM_UP_ANGLE = 0.75;

    private static final double STANDARD_ARM_COLLECTION_ANGLE = 0.84;

    private static final double STANDARD_ARM_SECOND_BRICK_ANGLE = 0.8;

    /**
     * Angle in which the arm is in a standard down position ready to pick up a stone
     */
    private static final double STANDARD_ARM_DOWN_ANGLE = 0.88;

    /**
     * Position of the claw servo where it can pick up a stone
     */
    private static final double OPEN_SERVO_POSITION = 0.4;

    /**
     * Position of the claw servo where it's holding the stone with the lowest possible force
     */
    private static final double CLOSE_SERVO_POSITION = 0.7;

    private static final double LEFT_FOUNDATION_SERVO_UP = 0.75;
    private static final double LEFT_FOUNDATION_SERVO_DOWN = 0.38;

    private static final double RIGHT_FOUNDATION_SERVO_UP = 0.46;
    private static final double RIGHT_FOUNDATION_SERVO_DOWN = 0.85;

    /**
     * Speed of the collection mechanism //FIXME: ensure this isn't too fast
     */
    private static final double COLLECTION_MOTOR_SPEED = 0.73;

    /**
     * Keeps track of the height the arm is currently at. Used to calculate distance to raise / lower between
     * two positions or to the very top
     */
    private double currentHeight = 0.0;
    private LoweredFoundation loweredFoundation = LoweredFoundation.NONE;

    private boolean aPressed = false;
    private boolean bPressed = false;
    private boolean isStopped = false;
    private boolean rbPressed = false;
    private boolean lbPressed = false;
    private boolean xPressed = false;
    private boolean rdpPressed = false;

    private int counter = 0;

    private boolean isClawClosed = false;

    void gamepadHandler(Gamepad gamepad, Telemetry telemetry) throws InterruptedException {
        if (gamepad.dpad_up) {
            runLift(LiftDiection.UP, gamepad);
            isStopped = false;
        } else if (gamepad.dpad_down) {
            runLift(LiftDiection.DOWN, gamepad);
            isStopped = false;
        } else if (!isStopped) {
            runLift(LiftDiection.STOP, gamepad);
            isStopped = true;
        }

        if (gamepad.a) {
            if (aPressed) return;

            lowerArmToBottom(gamepad, false);
            aPressed = true;
        } else aPressed = false;

        if (gamepad.b) {

            if (bPressed) return;

            counter += 1;

            toggleClaw();
            bPressed = true;
        } else bPressed = false;

        if (gamepad.x) {
            if (xPressed) return;

            rotateArm(ArmDirection.PLACE_SECOND);
            xPressed = true;
        } else xPressed = false;

        collect(gamepad.left_trigger, gamepad.right_trigger);

//        if (gamepad.left_trigger > 0.5) setCollectionMode(true, true);
//        else if (gamepad.right_trigger > 0.5) setCollectionMode(true, false);
//        else setCollectionMode(false);

        if (gamepad.dpad_left) rotateArm(ArmDirection.FORWARD);
        if (gamepad.dpad_right) {
            if (rdpPressed) return;

            collectBrick(gamepad);
            rdpPressed = true;
        } else rdpPressed = false;

        if (gamepad.right_bumper) {
            if (!rbPressed) {
                toggleFoundation(FoundationSide.RIGHT);
                rbPressed = true;
            }
        } else rbPressed = false;

        if (gamepad.left_bumper) {
            if (!lbPressed) {
                toggleFoundation(FoundationSide.LEFT);
                lbPressed = true;
            }
        } else lbPressed = false;

        telemetry.addData("B Counter", counter);
        telemetry.addData("Touch: ", getRobotSetup().getArmTouchSensor().isPressed());
        telemetry.addData("Dir: ", getRobotSetup().getArmMotor().getDirection());
        telemetry.addData("Height: ", getCurrentHeight());
        telemetry.addData("left Foundation:", robotSetup.getLeftFoundationServo().getPosition());
        telemetry.addData("right Foundation:", robotSetup.getRightFoundationServo().getPosition());
        telemetry.addData("arm: ", robotSetup.getArmServo().getPosition());
        telemetry.update();
    }

    void runLift(LiftDiection d, Gamepad gamepad) {

        if (robotSetup.getArmTouchSensor().isPressed() && d == LiftDiection.DOWN) d = LiftDiection.STOP;

        switch (d) {
            case UP:
                robotSetup.getArmMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robotSetup.getArmMotor().setPower(gamepad.y ? ARM_UP_MOTION_SPEED / 2 : ARM_UP_MOTION_SPEED);
                break;
            case DOWN:
                robotSetup.getArmMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robotSetup.getArmMotor().setPower(gamepad.y ? ARM_DOWN_MOTION_SPEED / 2 : ARM_DOWN_MOTION_SPEED);
                break;
            case STOP:
                robotSetup.getArmMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robotSetup.getArmMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robotSetup.getArmMotor().setTargetPosition(0);
                robotSetup.getArmMotor().setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robotSetup.getArmMotor().setPower(0.1);
                break;
        }
    }

    void lowerArmToBottom(Gamepad gamepad) {
        lowerArmToBottom(gamepad, true);
    }

    /**
     * Lower the arm to the very bottom. Uses the touch sensor to ensure the arm is always in the exact
     * same position after this method
     */
    void lowerArmToBottom(Gamepad gamepad, boolean openFirst) {
        boolean aPressed = gamepad.a;

        rotateArm(ArmDirection.COLLECT);

        if (openFirst) toggleClaw(true);

        /// If the touch sensor is already pressed stop the execution of the method
        if (robotSetup.getArmTouchSensor().isPressed()) {
            if (!openFirst) toggleClaw(true);
            return;
        }

        robotSetup.getArmMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robotSetup.getArmMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robotSetup.getArmMotor().setPower(ARM_DOWN_MOTION_SPEED);
        while (!robotSetup.getArmTouchSensor().isPressed()) {
            if (gamepad.a && !aPressed) {
                robotSetup.getArmMotor().setPower(0);
                robotSetup.getArmMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                return;
            }
            if (!gamepad.a) aPressed = false;
        }
        robotSetup.getArmMotor().setPower(0);
        robotSetup.getArmMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        if (!openFirst) toggleClaw(true);

        currentHeight = 0.0;
    }

    void toggleClaw() {
        toggleClaw(false);
    }

    void toggleClaw(boolean onlyOpen) {
        if (!isClawClosed && !onlyOpen) {
            robotSetup.getClawServo().setPosition(CLOSE_SERVO_POSITION);

            isClawClosed = true;
            return;
        }

        robotSetup.getClawServo().setPosition(OPEN_SERVO_POSITION);
        isClawClosed = false;
    }

    /**
     * Raises the arm to the maximum height
     *
     * @param lowerFirst Whether the arm should be first lowered to the very bottom before being raised.
     *                   Might increase the precision of driving to the top
//     * @see MAXIMUM_ARM_HEIGHT
     */
    void raiseArmToTop(boolean lowerFirst, Gamepad gamepad) {

        if (lowerFirst) lowerArmToBottom(gamepad);

        final int ticks = calculateTicksToRaiseByDistance(MAXIMUM_ARM_HEIGHT - currentHeight);

        Utils.encoderMotorRun(robotSetup.getArmMotor(), ticks, ARM_UP_MOTION_SPEED);

        currentHeight = MAXIMUM_ARM_HEIGHT;
    }

    /**
     * Raises the arm to the specified height
     *
     * @param targetHeight The height, in cm, the arm should raise to. 0 is the height when the arm is
     *                     touching the touch sensor
     * @param lowerFirst Whether the arm should be first lowered to the very bottom before being raised.
     *                   Might increase the precision of driving to the top
     */
    void runArmToHeight(double targetHeight, boolean lowerFirst, Gamepad gamepad) {

        targetHeight = Range.clip(targetHeight, 0, MAXIMUM_ARM_HEIGHT);

        if (lowerFirst) lowerArmToBottom(gamepad);

        final double raiseDistance = targetHeight - currentHeight;
        final int ticks = calculateTicksToRaiseByDistance(raiseDistance);

        Utils.encoderMotorRun(robotSetup.getArmMotor(), ticks, ticks > 0 ? ARM_UP_MOTION_SPEED : -ARM_DOWN_MOTION_SPEED);

        currentHeight = targetHeight;
    }

    /// Workaround for Java not supporting optional parameters with default values
    void runArmToHeight(double targetHeight, Gamepad gamepad) {
        runArmToHeight(targetHeight, false, gamepad);
    }

    /**
     * Convert a distance the arm should move into ticks in the motor.
     *
     * @param distance How much, in cm, the arm should move up or down
     * @return The ticks the motor must turn by to move the specified distance
     */
    private int calculateTicksToRaiseByDistance(double distance) {

        //TODO: Convert distance to move into distance of rope to be wound up or down

        final double rotations = distance / WIND_UP_COIL_CIRCUMFERENCE;
        final double ticks = rotations * ARM_MOTOR_TICKS;
        return (int) Math.round(ticks);
    }

    void rotateArm(ArmDirection armDirection) {
        if (armDirection == ArmDirection.PLACE_SECOND) {
            robotSetup.getArmServo().setPosition(STANDARD_ARM_SECOND_BRICK_ANGLE);
            return;
        }

        if (armDirection == ArmDirection.BOTTOM) {
            robotSetup.getArmServo().setPosition(STANDARD_ARM_DOWN_ANGLE);
            return;
        }

        if (armDirection == ArmDirection.COLLECT) {
            robotSetup.getArmServo().setPosition(STANDARD_ARM_COLLECTION_ANGLE);
            return;
        }

        robotSetup.getArmServo().setPosition(STANDARD_ARM_UP_ANGLE);
    }

//    void setCollectionMode(boolean running) {
//        setCollectionMode(running, false);
//    }

//    void setCollectionMode(boolean running, boolean cannon) {
//        if (running) {
//            robotSetup.getRightCollectionMotor().setPower(cannon ? 1.0 : COLLECTION_MOTOR_SPEED);
//            robotSetup.getLeftCollectionMotor().setPower(cannon ? 1.0 : COLLECTION_MOTOR_SPEED);
//
//            return;
//        }
//
//        robotSetup.getRightCollectionMotor().setPower(0);
//        robotSetup.getLeftCollectionMotor().setPower(0);
//    }

    void collect(double left, double right) {
        if (right > 0) {
            robotSetup.getRightCollectionMotor().setPower(right);
            robotSetup.getLeftCollectionMotor().setPower(right);

            return;
        }

        if (left > 0) {
            robotSetup.getRightCollectionMotor().setPower(-COLLECTION_MOTOR_SPEED);
            robotSetup.getLeftCollectionMotor().setPower(-COLLECTION_MOTOR_SPEED);

            return;
        }

        robotSetup.getRightCollectionMotor().setPower(0.0);
        robotSetup.getLeftCollectionMotor().setPower(0.0);
    }

    void collectBrick(Gamepad gamepad) {
        lowerArmToBottom(gamepad);
        toggleClaw();
        rotateArm(ArmDirection.FORWARD);
    }

    void toggleFoundation(FoundationSide side) {
        if (side == FoundationSide.LEFT) {
            robotSetup.getRightFoundationServo().setPosition(RIGHT_FOUNDATION_SERVO_UP);

            if (loweredFoundation == LoweredFoundation.LEFT) {
                robotSetup.getLeftFoundationServo().setPosition(LEFT_FOUNDATION_SERVO_UP);
                loweredFoundation = LoweredFoundation.NONE;
            } else {
                robotSetup.getLeftFoundationServo().setPosition(LEFT_FOUNDATION_SERVO_DOWN);
                loweredFoundation = LoweredFoundation.LEFT;
            }

            return;
        }

        robotSetup.getLeftFoundationServo().setPosition(LEFT_FOUNDATION_SERVO_UP);

        if (loweredFoundation == LoweredFoundation.RIGHT) {
            robotSetup.getRightFoundationServo().setPosition(RIGHT_FOUNDATION_SERVO_UP);
            loweredFoundation = LoweredFoundation.NONE;
        } else {
            robotSetup.getRightFoundationServo().setPosition(RIGHT_FOUNDATION_SERVO_DOWN);
            loweredFoundation = LoweredFoundation.RIGHT;
        }
    }

    double getCurrentHeight() { return currentHeight; }

    RobotSetup getRobotSetup() { return robotSetup; }
}

enum ArmDirection {
    BOTTOM,
    FORWARD,
    COLLECT,
    PLACE_SECOND
}

enum LiftDiection {
    UP,
    DOWN,
    STOP
}

enum LoweredFoundation {
    LEFT,
    RIGHT,
    NONE
}

enum FoundationSide {
    RIGHT,
    LEFT
}
