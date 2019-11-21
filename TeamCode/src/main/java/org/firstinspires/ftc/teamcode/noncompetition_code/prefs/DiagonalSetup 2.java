package org.firstinspires.ftc.teamcode.noncompetition_code.prefs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DiagonalSetup {
    private DcMotor _leftFrontMotor, _rightFrontMotor, _leftBackMotor, _rightBackMotor;

    DiagonalSetup(HardwareMap hardwareMap) {
        _leftFrontMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        _rightFrontMotor = hardwareMap.dcMotor.get("rightFrontMotor");
        _leftBackMotor = hardwareMap.dcMotor.get("leftBackMotor");
        _rightBackMotor = hardwareMap.dcMotor.get("rightBackMotor");

//        _leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        _rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        _leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        _rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        _leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        _rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        _leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        _rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
