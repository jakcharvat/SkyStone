package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotSetup {


    //region Base
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private DcMotor perpendicularMotor;
    //endregion

    //region Arm
    private DcMotor armMotor;
    private DigitalChannel armTouchSensor;
    //endregion


    /**
     * Initialize the robot - all the motors and sensors
     *
     * @param hardwareMap`A reference to the hardwareMap variable provided by LinearOpMode
     */
    RobotSetup(HardwareMap hardwareMap) {
        initBase(hardwareMap);
        initArm(hardwareMap);
    }

    /**
     * Initialize all motors on the base of the robot
     *
     * @param hardwareMap
     */
    private void initBase(HardwareMap hardwareMap) {
        leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");
        perpendicularMotor = hardwareMap.dcMotor.get("perpendicularMotor");

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Initialize all motors and sensors on the robot's arm and claw
     *
     * @param hardwareMap
     */
    private void initArm(HardwareMap hardwareMap) {
        armMotor = hardwareMap.dcMotor.get("armMotor");
        armTouchSensor = hardwareMap.digitalChannel.get("armTouchSensor");
    }

    //MARK: getters

    /// Note: I use private variables and getters to ensure that once the robot setup class is initialized
    /// using a hardware map at the start of the execution of something, the motors and sensors cannot be
    /// reassigned.
    DcMotor getLeftFrontMotor() { return leftFrontMotor; }
    DcMotor getRightFrontMotor() { return rightFrontMotor; }
    DcMotor getLeftBackMotor() { return leftBackMotor; }
    DcMotor getRightBackMotor() { return rightBackMotor; }
    DcMotor getPerpendicularMotor() { return perpendicularMotor; }

    DcMotor getArmMotor() { return armMotor; }
    DigitalChannel getArmTouchSensor() { return armTouchSensor; }
}
