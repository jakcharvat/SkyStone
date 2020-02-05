package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp(name = "Distance Test", group = "Tests")
public class DistanceTest extends LinearOpMode {

    private DistanceSensor d;

    @Override
    public void runOpMode() throws InterruptedException {
        d = hardwareMap.get(DistanceSensor.class, "rightColor");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Distance: ", d.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}
