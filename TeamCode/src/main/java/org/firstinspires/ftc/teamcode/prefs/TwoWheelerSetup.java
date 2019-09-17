package org.firstinspires.ftc.teamcode.prefs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TwoWheelerSetup {
    private DcMotor _leftMotor, _rightMotor;

    TwoWheelerSetup(HardwareMap hardwareMap) {
        _leftMotor = hardwareMap.dcMotor.get("leftFrontMotor");
        _rightMotor = hardwareMap.dcMotor.get("rightFrontMotor");

        _leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public DcMotor leftMotor() {
        return _leftMotor;
    }

    public DcMotor rightMotor() {
        return _rightMotor;
    }
}
