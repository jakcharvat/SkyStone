package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotSetup {

    //MARK: motors
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightBackMotor;
    private DcMotor perpendicularMotor;

    /**
     * Initialize the robot - all the motors and sensors
     *
     * @param hardwareMap`A reference to the hardwareMap variable provided by LinearOpMode
     */
    public RobotSetup(HardwareMap hardwareMap) {
        leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");
        perpendicularMotor = hardwareMap.dcMotor.get("perpendicularMotor");

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    //MARK: getters

    /// Note: I use private variables and getters to ensure that once the robot setup class is initialized
    /// using a hardware map at the start of the execution of something, the motors and sensors cannot be
    /// reassigned.
    public DcMotor getLeftFrontMotor() { return leftFrontMotor; }
    public DcMotor getRightFrontMotor() { return rightFrontMotor; }
    public DcMotor getLeftBackMotor() { return leftBackMotor; }
    public DcMotor getRightBackMotor() { return rightBackMotor; }
    public DcMotor getPerpendicularMotor() { return perpendicularMotor; }
}
