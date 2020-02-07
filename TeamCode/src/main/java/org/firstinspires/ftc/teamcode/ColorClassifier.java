package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

final class ColorClassifier {
    static Color checkColor(ColorSensor colorSensor, DistanceSensor distanceSensor) {

        if (
                colorSensor.red() > colorSensor.blue()
                && colorSensor.green() > colorSensor.blue()
                && Math.abs(colorSensor.red() - colorSensor.green()) < Math.abs(colorSensor.green() - colorSensor.blue())
                && colorSensor.red() >= 20
                && !Double.isNaN(distanceSensor.getDistance(DistanceUnit.CM))
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
