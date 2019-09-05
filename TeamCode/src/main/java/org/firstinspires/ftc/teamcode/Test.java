package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Test extends LinearOpMode {
    private DcMotor leftMotor,rightMotor;

    public Test(HardwareMap hardwareMap) {
        this.leftMotor = hardwareMap.get(DcMotor.class, "leftMotor");
        this.rightMotor = hardwareMap.get(DcMotor.class, "rightMotor");
    }
    @Override
    public void runOpMode() {
        //Set Power Setting
        leftMotor.setPower(1);
        rightMotor.setPower(1);
        //Set Target Position
        leftMotor.setTargetPosition(1200);
        rightMotor.setTargetPosition(1200);
        //Run Until Target Position Reached
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //Stop
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Turn Left

    }
}