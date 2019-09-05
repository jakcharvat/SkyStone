package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Move {

    private DcMotor leftMotor, rightMotor;

    public Move(HardwareMap hardwareMap) {
        this.leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        this.rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
    }

    void moveForward() {
        //Do something
    }

    void moveBackwards() {
        //Do something else
    }


}
