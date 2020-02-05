package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

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

    void gamepadDrive(Gamepad gamepad) {
        double x = gamepad.left_stick_x, y = -gamepad.right_stick_y, turn = normalizeTurn(gamepad.right_stick_x);

        if (turn != 0) {
            setTurnPower(turn);
            return;
        }

        setPowerOnMainAndPerpendicular(y, x);
    }

    private double normalizeTurn(double t) {
        t = Range.clip(t, -1, 1);

        if (t > 0) {
            return Range.clip((t - 0.5) * 2, 0, 1);
        }

        return Range.clip((t + 0.5) * 2, -1, 0);
    }

    /**
     * Immediately stop all motion of the robot
     */
    void stopMotion() {
        setPowerOnMain(0.0);
    }
}
