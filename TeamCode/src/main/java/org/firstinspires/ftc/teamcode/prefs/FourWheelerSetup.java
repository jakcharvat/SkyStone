package org.firstinspires.ftc.teamcode.prefs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FourWheelerSetup {
    private DcMotor _leftFrontMotor, _rightFrontMotor, _leftBackMotor, _rightBackMotor;

    FourWheelerSetup(HardwareMap hardwareMap) {
        _leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        _rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        _leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        _rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");

        _leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        _leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        _leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        _rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        _leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        _rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    public DcMotor leftFrontMotor() {
        return _leftFrontMotor;
    }

    public DcMotor rightFrontMotor() {
        return _rightFrontMotor;
    }

    public DcMotor leftBackMotor() {
        return _leftBackMotor;
    }

    public DcMotor rightBackMotor() {
        return _rightBackMotor;
    }
}
