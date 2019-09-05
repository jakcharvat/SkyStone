package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Move {




    double power = 1;

    DcMotor left;
    DcMotor right;

    void moveForward() {
        left = hardWareMap.dcMotor.get("leftMotor")
        right = hardWareMap.dcMotor.get("rightMotor")

        right.setDirection(DcMotorrSimple.Direction.REVERSE);

        left.setPower(power)
        right.setPower(power)

        sleep(1000) // run for some amount of time

        power = 0;

        left.setPower(power)
        right.setPower(power)
    }

    void moveBackwards() {
        //Do something else

        power = -1;

        left = hardWareMap.dcMotor.get("leftMotor")
        right = hardWareMap.dcMotor.get("rightMotor")

        right.setDirection(DcMotorrSimple.Direction.REVERSE);

        left.setPower(power)
        right.setPower(power)

        sleep(1000) // run for some amount of time

        power = 0;

        left.setPower(power)
        right.setPower(power)
    }

    void turnLeft(){

        left = hardWareMap.dcMotor.get("leftMotor")
        right = hardWareMap.dcMotor.get("rightMotor")

        right.setDirection(DcMotorrSimple.Direction.REVERSE);

        left.setPower(0.0)
        right.setPower(power)



    }

    void turnRight(){

        left = hardWareMap.dcMotor.get("leftMotor")
        right = hardWareMap.dcMotor.get("rightMotor")

        right.setDirection(DcMotorrSimple.Direction.REVERSE);

        left.setPower(power)
        right.setPower(0.0)
    }


}
