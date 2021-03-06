package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class RobotSetup {


    //region Base
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private DcMotor perpendicularMotor;

    private ColorSensor leftColorSensor;
    private ColorSensor rightColorSensor;
    private DistanceSensor leftDistanceSensor;
    private DistanceSensor rightDistanceSensor;
    private ColorSensor color;
    //endregion

    //region Arm
    private DcMotor armMotor;
    private TouchSensor armTouchSensor;
    private Servo armServo;     /// Controls the angle of the portion of the arm holding the claw
    private Servo clawServo;    /// Controls the opening and closing of the claw
    //endregion

    //region Collection
    private DcMotor rightCollectionMotor;
    private DcMotor leftCollectionMotor;
    private Servo collectionServo;
    private Servo rightFoundationServo;
    private Servo leftFoundationServo;
    //endregion


    /**
     * Initialize the robot - all the motors and sensors
     *
     * @param hardwareMap`A reference to the hardwareMap variable provided by LinearOpMode
     */
    RobotSetup(HardwareMap hardwareMap) {
        initBase(hardwareMap);
        initArm(hardwareMap);
        initCollection(hardwareMap);
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

        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftColorSensor = hardwareMap.colorSensor.get("leftColor");
        rightColorSensor = hardwareMap.colorSensor.get("rightColor");

        leftDistanceSensor = hardwareMap.get(DistanceSensor.class, "leftColor");
        rightDistanceSensor = hardwareMap.get(DistanceSensor.class, "rightColor");

        color = hardwareMap.colorSensor.get("bottomColor");
    }

    /**
     * Initialize all motors and sensors on the robot's arm and claw
     *
     * @param hardwareMap
     */
    private void initArm(HardwareMap hardwareMap) {
        armMotor = hardwareMap.dcMotor.get("armMotor");
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armTouchSensor = hardwareMap.touchSensor.get("armTouchSensor");
        armServo = hardwareMap.servo.get("armServo");
        clawServo = hardwareMap.servo.get("clawServo");
    }

    private void initCollection(HardwareMap hardwareMap) {
        rightCollectionMotor = hardwareMap.dcMotor.get("rightCollectionMotor");
        leftCollectionMotor = hardwareMap.dcMotor.get("leftCollectionMotor");

        rightCollectionMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        collectionServo = hardwareMap.servo.get("rightCollectionServo");
        rightFoundationServo = hardwareMap.servo.get("rightFoundationServo");
        leftFoundationServo = hardwareMap.servo.get("leftFoundationServo");
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
    TouchSensor getArmTouchSensor() { return armTouchSensor; }
    Servo getArmServo() { return armServo; }
    Servo getClawServo() { return clawServo; }

    DcMotor getRightCollectionMotor() { return rightCollectionMotor; }
    DcMotor getLeftCollectionMotor() { return leftCollectionMotor; }
    Servo getCollectionServo() { return collectionServo; }
    Servo getRightFoundationServo() { return rightFoundationServo; }
    Servo getLeftFoundationServo() { return leftFoundationServo; }

    ColorSensor getLeftColorSensor() {
        return leftColorSensor;
    }
    ColorSensor getRightColorSensor() { return rightColorSensor; }
    ColorSensor getBottomColorSensor() { return color; }

    DistanceSensor getLeftDistanceSensor() { return leftDistanceSensor; }
    DistanceSensor getRightDistanceSensor() { return rightDistanceSensor; }

}
