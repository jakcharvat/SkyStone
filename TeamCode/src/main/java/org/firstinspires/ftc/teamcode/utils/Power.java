package org.firstinspires.ftc.teamcode.utils;

public class Power {
    private final double leftFront;
    private final double rightFront;
    private final double leftBack;
    private final double rightBack;
    private final double angle;
    private final double power;
    private final double xComponent;
    private final double yComponent;

    Power(double leftFront,
          double leftBack,
          double rightFront,
          double rightBack,
          double angle,
          double power,
          double xComponent,
          double yComponent
    ) {
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        this.angle = angle;
        this.power = power;
        this.xComponent = xComponent;
        this.yComponent = yComponent;
    }

    // Getters
    public double getLeftFront() {
        return leftFront;
    }

    public double getRightFront() {
        return rightFront;
    }

    public double getLeftBack() {
        return leftBack;
    }

    public double getRightBack() {
        return rightBack;
    }

    public double getAngle() {
        return angle;
    }

    public double getPower() {
        return power;
    }

    public double getxComponent() { return xComponent; }

    public double getyComponent() { return yComponent; }

    public boolean isStationary() {
        return xComponent == 0 && yComponent == 0;
    }


}
