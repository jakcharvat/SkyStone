package org.firstinspires.ftc.teamcode.stick_game;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.prefs.DiagonalSetup;

@SuppressWarnings("StatementWithEmptyBody")
public class MoveManager {

    private int currentRow;
    private int currentStick;

    private int shelfHeight;
    private int ballDistance;
    private int ticksInRotation;
    private int wheelCircumference;
    private int armHeight;

    private DiagonalSetup motors;

    private DcMotor leftFrontMotor = motors.leftFrontMotor();
    private DcMotor rightFrontMotor = motors.rightFrontMotor();
    private DcMotor leftBackMotor = motors.leftBackMotor();
    private DcMotor rightBackMotor = motors.rightBackMotor();
    DcMotor upDownMotor = motors.upDownMotor();
    DcMotor woundUpDownMotor = motors.woundUpDownMotor();

    void moveToStick(int row, int stick) {
        int moveRightBy = stick - currentStick;
        int moveUpBy = row - currentRow;

        
    }

    //FIXME: Not going to work - unavailable async programming
    private void runMotor(DcMotor motor, float rotations, double power){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(Math.round(rotations*ticksInRotation));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
        while(motor.isBusy()) { }
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //TODO: This should accept the number of sticks to move as a parameter.
    //FIXME: Method names in lowerCamelCase
    void MoveLeft(int distance){
        //FIXME: Due to the nature this is written, one motor will run after the other, instead of both in parallel
        runMotor(rightFrontMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
        runMotor(rightBackMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
    }
    void MoveRight(int distance){
        //FIXME: As above
        runMotor(leftFrontMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
        runMotor(leftBackMotor, distance,0.25);
    }
    void MoveForward(int distance){
        //FIXME: As above
        runMotor(leftBackMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
        runMotor(rightBackMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
    }
    void MoveBackward(int distance){
        //FIXME: As above
        runMotor(leftFrontMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
        runMotor(rightFrontMotor, (float)((distance*Math.sqrt(2))/wheelCircumference),0.25);
    }
    void MoveUp(int height){

    }
    void MoveDown(int height){

    }

    void knockStick() {
        //TODO: Implement
    }

}
