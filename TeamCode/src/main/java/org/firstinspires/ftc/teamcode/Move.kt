package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap

/**
 * A class used to control the basic movement of the robot in the 2d playing field
 *
 * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
 */
class Move(private val hardwareMap: HardwareMap) {

    /// Declare and initialize variables containing references to the four movement motors
    /// that should be used throughout the class
    private val frontLeftMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "frontLeftMotor")
    private val frontRightMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "frontRightMotor")
    private val backLeftMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "backLeftMotor")
    private val backRightMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "backRightMotor")

    /**
     * Starts moving the robot straight
     *
     * The [power] parameter controls the speed **and** direction of the motion. If power
     * is positive then the robot will move forward, if negative it will move backward
     *
     * If either or both of the optional parameters [forSeconds] and [forMilliseconds]
     * is provided, then the motors will run forward for the specified duration and then
     * stop. Note that both of these variables must be positive integers.
     *
     * @param power desired power of the motor as a double between -1.0 and 1.0
     * @param forSeconds how long the robot should move before stopping in seconds
     * @param forMilliseconds how long the robot should move before stopping in milliseconds
     */
    fun straight(power: Double, forSeconds: Int? = null, forMilliseconds: Int? = null) {

        setPowerOnAll(power)

        if (forMilliseconds != null || forSeconds != null) {
            
            val sleepMillis: Int = (forMilliseconds?:0) + ((forSeconds?:0) * 1000)
            Thread.sleep(sleepMillis.toLong())
            stop()
            
        }
    }

    fun stop() {
        setPowerOnAll(0.0)
    }

    private fun setPowerOnAll(power: Double) {
        frontLeftMotor.power = power
        frontRightMotor.power = power
        backLeftMotor.power = power
        backRightMotor.power = power
    }
}
