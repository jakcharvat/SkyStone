package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

@SuppressWarnings("StatementWithEmptyBody")
class Utils {

    /**
     * Run a single motor until it reaches the specified target position
     *
     * @param motor A reference to the motor from the {@link RobotSetup} class
     * @param ticks The target position the motor should run to. Refers to physical ticks on the motor
     *              the encoder counts to keep track of the distance traveled.
     * @param power How much power to apply to the motor - expressed as a double between -1 and 1
     */
    static void encoderMotorRun(DcMotor motor, int ticks, double power) {
        motor.setPower(0);

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor.setPower(power);

        while (motor.isBusy());
    }

    /**
     * Run several motors until they reach a specified target position
     *
     * @param motors An array containing references to all the motors that should be run
     * @param ticks The target position the motor should run to. Refers to physical ticks on the motor
     *      *       the encoder counts to keep track of the distance traveled.
     * @param power How much power to apply to the motor - expressed as a double between -1 and 1
     */
    static void encoderMotorsRun(DcMotor[] motors, int ticks, double power) {

        for (DcMotor motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition(ticks);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        for (DcMotor motor : motors) motor.setPower(power);

        for (DcMotor motor : motors) {
            while (motor.isBusy());
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }
}
