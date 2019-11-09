package org.firstinspires.ftc.teamcode;

@SuppressWarnings("ALL")
class ArmController {

    /**
     * Class that handles all the actions of the robot's arm, including raising and lowering it,
     * picking up blocks and placing them on the tower. Also controls the pickup mechanism
     * and the claw.
     *
     * @param robotSetup
     */
    ArmController(RobotSetup robotSetup) {
        this.robotSetup = robotSetup;
    }

    private RobotSetup robotSetup;

    private static Utils utils = new Utils();

    //IMPORTANT: All measurements in cm / rad

    /**
     * The speed of the arm when moving both up and down, regardless of the distance to travel.
     * Positive should represent winding up the string, therefore moving the arm up, negative down.
     */
    private static final double ARM_MOTION_SPEED = 0.5;

    /**
     * Ticks in the encoder of the motor raising and lowering the arm
     */
    private static final double ARM_MOTOR_TICKS = 1440; //FIXME: Ensure this is actually correct

    /**
     * Circumference of the block the string raising and lowering the hand is wound up on.
     * Used to calculate by how much to rotate the motor to reach a certain height
     */
    private static final double WIND_UP_COIL_CIRCUMFERENCE = 10.0; //FIXME: ditto

    /**
     * Maximum possible height of the arm
     */
    private static final double MAXIMUM_ARM_HEIGHT = 200.0;

    /**
     * Keeps track of the height the arm is currently at. Used to calculate distance to raise / lower between
     * two positions or to the very top
     */
    private double currentHeight = 0.0;

    /**
     * Lower the arm to the very bottom. Uses the touch sensor to ensure the arm is always in the exact
     * same position after this method
     */
    void lowerArmToBottom() {

        /// If the touch sensor is already pressed stop the execution of the method
        if (robotSetup.getArmTouchSensor().getState()) return;

        robotSetup.getArmMotor().setPower(-ARM_MOTION_SPEED);
        while (!robotSetup.getArmTouchSensor().getState()) { }
        robotSetup.getArmMotor().setPower(0);

        currentHeight = 0.0;
    }

    /**
     * Raises the arm to the maximum height
     *
     * @param lowerFirst Whether the arm should be first lowered to the very bottom before being raised.
     *                   Might increase the precision of driving to the top
     * @see MAXIMUM_ARM_HEIGHT
     */
    void raiseArmToTop(boolean lowerFirst) {

        if (lowerFirst) lowerArmToBottom();

        final int ticks = calculateTicksToRaiseByDistance(MAXIMUM_ARM_HEIGHT - currentHeight);

        utils.encoderMotorRun(robotSetup.getArmMotor(), ticks, ARM_MOTION_SPEED);

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
    void runArmToHeight(double targetHeight, boolean lowerFirst) {

        if (lowerFirst) lowerArmToBottom();

        final double raiseDistance = targetHeight - currentHeight;
        final int ticks = calculateTicksToRaiseByDistance(raiseDistance);

        utils.encoderMotorRun(robotSetup.getArmMotor(), ticks, ticks > 0 ? ARM_MOTION_SPEED : -ARM_MOTION_SPEED);

        currentHeight = targetHeight;
    }

    private int calculateTicksToRaiseByDistance(double distance) {
        final double rotations = distance / WIND_UP_COIL_CIRCUMFERENCE;
        final double ticks = rotations * ARM_MOTOR_TICKS;
        return (int) Math.round(ticks);
    }


}
