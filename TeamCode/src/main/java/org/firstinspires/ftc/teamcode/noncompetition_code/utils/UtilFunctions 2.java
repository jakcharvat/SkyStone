package org.firstinspires.ftc.teamcode.noncompetition_code.utils;

public class UtilFunctions {

    static public double calculateJoystickAngle(double x, double y) {

        double angle = Math.atan(Math.abs(y) / Math.abs(x));

        if (!(angle >= 0 && angle <= (2 * Math.PI))) return 0;

        // First quadrant
        if (x > 0 && y < 0) return (Math.PI / 2) - angle;

        // Fourth quadrant
        if (x > 0 && y >= 0) return (Math.PI / 2) + angle;

        // Second quadrant
        if (x <= 0 && y < 0) return (3 * Math.PI / 2) + angle;

        // Third quadrant
        if (x <= 0 && y >= 0) return (3 * Math.PI / 2) - angle;

        return 0;
    }

    static private Offset calculateXyAtPower(double angle, double power) {

        // All perfect 90° angles
        if (angle == 0 || angle == (2 * Math.PI)) return new Offset(0, power);
        if (angle == (Math.PI / 2)) return new Offset(power, 0);
        if (angle == Math.PI) return new Offset(0, -power);
        if (angle == (3 * Math.PI / 2)) return new Offset(-power, 0);

        // First quadrant
        if (angle <= (Math.PI/2)) {
            return new Offset(power * Math.cos((Math.PI / 2) - angle),
                    power * Math.sin((Math.PI / 2) - angle));
        }

        // Fourth quadrant
        if (angle <= Math.PI) {
            return new Offset(power * Math.cos(angle - (Math.PI / 2)),
                    -power * Math.sin(angle - (Math.PI / 2)));
        }

        // Third quadrant
        if (angle <= (3 * Math.PI / 2)) {
            return new Offset(-power * Math.cos((3 * Math.PI / 2) - angle),
                    -power * Math.sin((3 * Math.PI / 2) - angle));
        }

        // Second quadrant
        if (angle <= (2 * Math.PI)) {
            return new Offset(-power * Math.cos(angle - (3 * Math.PI / 2)),
                    power * Math.sin(angle - (3 * Math.PI / 2)));
        }

        return new Offset(0, 0);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    static public Power calculateMotorSettingsNeededToAchieveAngleAndPower(double angle, double power) {

        angle = angle - (Math.PI / 4);
        if (angle < 0) angle = (2 * Math.PI) + angle;

        final Offset motorPower = calculateXyAtPower(angle, power);

        final double xPower = motorPower.getDx();
        final double yPower = motorPower.getDy();

        return new Power(yPower, -xPower, xPower, -yPower, power, angle, xPower, yPower);
    }


    static public Power adaptMotorSettingsToTurn(Power power, double turn) {

        final double xComponent = power.getxComponent();
        final double yComponent = power.getyComponent();

        final double xComponentDelta = Math.min(1 - xComponent, 1 + xComponent);
        final double yComponentDelta = Math.min(1 - yComponent, 1 + yComponent);

        final double lf = yComponent + (turn * yComponentDelta);
        final double lb = -xComponent + (turn * xComponentDelta);
        final double rf = xComponent + (turn * xComponentDelta);
        final double rb = -yComponent + (turn * yComponentDelta);

        return new Power(lf, lb, rf, rb, power.getPower(), power.getAngle(), xComponent, yComponent);
    }

}
