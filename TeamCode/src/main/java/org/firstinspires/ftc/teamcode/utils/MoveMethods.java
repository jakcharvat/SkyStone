package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.prefs.DiagonalSetup;

public class MoveMethods {

    int shelfHeight;
    int ballDistance ;
    int ticksInRotation;
    int wheelCircumference;
    int armHeight;
    int turn = 0;

    private DiagonalSetup motors;


    DcMotor leftFrontMotor = motors.leftFrontMotor();
    DcMotor rightFrontMotor = motors.rightFrontMotor();
    DcMotor leftBackMotor = motors.leftBackMotor();
    DcMotor rightBackMotor = motors.rightBackMotor();
    DcMotor upDownMotor = motors.upDownMotor();
    DcMotor woundUpDownMotor = motors.woundUpDownMotor();

    void runMotor(DcMotor motor, float rotations, double power){

        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(Math.round(rotations*ticksInRotation));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
        while(motor.isBusy()){

        }
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    void MoveLeft(int distance){
        runMotor(rightFrontMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
        runMotor(rightBackMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
    }
    void MoveRight(int distance){
        runMotor(leftFrontMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
        runMotor(leftBackMotor, distance,0.25);
    }
    void MoveForward(int distance){
        runMotor(leftBackMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
        runMotor(rightBackMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
    }
    void MoveBackward(int distance){
        runMotor(leftFrontMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
        runMotor(rightFrontMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
    }
    void MoveUp(int height){

    }
    void MoveDown(int height){

    }

}
