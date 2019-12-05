package org.firstinspires.ftc.teamcode;

abstract class TeleLinearOpMode extends BaseLinearOpMode {
    /**
     * Starts moving the robot straight
     *
     * The [power] parameter controls the speed **and** direction of the motion. If [power]
     * is positive then the robot will move forward, if negative it will move backward
     *
     * @param power desired power of the motor as a double between -1.0 and 1.0
     */
    void straight(final double power) {
        setPowerOnMain(power);
    }

    void gamepadDrive(double x, double y) {

    }

    /**
     * Immediately stop all motion of the robot
     */
    void stopMotion() {
        setPowerOnMain(0.0);
    }
}
