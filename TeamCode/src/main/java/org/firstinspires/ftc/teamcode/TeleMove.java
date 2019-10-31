package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class TeleMove extends BaseMove {

    /**
     * A class used to control the basic movement of the robot in the 2d playing field
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     */
    TeleMove(HardwareMap hardwareMap) {
        super(hardwareMap);
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
}
