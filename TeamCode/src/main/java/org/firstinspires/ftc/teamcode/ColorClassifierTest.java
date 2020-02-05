package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Color Test", group = "Tests")
public class ColorClassifierTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        ColorSensor sensor = hardwareMap.colorSensor.get("rightColor");
        DistanceSensor dSensor = hardwareMap.get(DistanceSensor.class, "rightColor");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Classification", ColorClassifier.checkColor(sensor, dSensor));
            telemetry.addData("Distance", dSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("Red", sensor.red());
            telemetry.addData("Green", sensor.green());
            telemetry.addData("Blue", sensor.blue());
            telemetry.update();
        }
    }
}
