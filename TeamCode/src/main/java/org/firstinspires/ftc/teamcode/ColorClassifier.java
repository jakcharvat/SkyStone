package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

final class ColorClassifier {
    static Color checkColor(ColorSensor colorSensor, DistanceSensor distanceSensor) {

        if (
                colorSensor.red() > colorSensor.green()
                && colorSensor.green() > colorSensor.blue()
                && colorSensor.red() - colorSensor.green() < colorSensor.green() - colorSensor.blue()
                && !Double.isNaN(distanceSensor.getDistance(DistanceUnit.CM))
                && colorSensor.red() >= 35
        ) return Color.YELLOW;

        if (Double.isNaN(distanceSensor.getDistance(DistanceUnit.CM))) return Color.NONE;

        return Color.BLACK;
    }
}

enum Color {
    YELLOW,
    BLACK,
    NONE
}
