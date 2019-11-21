package org.firstinspires.ftc.teamcode.utils;

public class UtilFunctions {

    public double calculateJoystickAngle(double x, double y) {

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

    private Offset calculateXyAtPower(double angle, double power) {

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

    //@SuppressWarnings("SuspiciousNameCombination")
    public Power calculateMotorSettingsNeededToAchieveAngleAndPower(double angle, double power) {

        angle = angle - (Math.PI / 4);
        if (angle < 0) angle = (2 * Math.PI) + angle;

        double xPower = calculateXyAtPower(angle, power).getDx();
        double yPower = calculateXyAtPower(angle, power).getDy();


        return new Power(yPower, -xPower, xPower, -yPower, power, angle, xPower, yPower);
    }


    public Power adaptMotorSettingsToTurn(Power power, double turn) {

        double xComponent = power.getxComponent();
        double yComponent = power.getyComponent();

        double xComponentDelta = Math.min(1 - xComponent, 1 + xComponent);
        double yComponentDelta = Math.min(1 - yComponent, 1 + yComponent);

        double lf = yComponent + (turn * yComponentDelta);
        double lb = -xComponent + (turn * xComponentDelta);
        double rf = xComponent + (turn * xComponentDelta);
        double rb = -yComponent + (turn * yComponentDelta);

        return new Power(lf, lb, rf, rb, power.getPower(), power.getAngle(), xComponent, yComponent);
    }

    public double degToRad(int degrees) {
        return degrees * Math.PI / 180;
    }

    public int radToDeg(double rad) {
        return (int) Math.round(rad * 180 / Math.PI);
    }

}
