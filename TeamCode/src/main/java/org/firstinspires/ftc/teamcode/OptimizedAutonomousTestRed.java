package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name = "Optimize Autonomous Test Red", group = "Competition")
public class OptimizedAutonomousTestRed extends AutoLinearOpMode {

    private static final double MOVE_SPEED = 0.8;
    private static final double TURN_SPEED = 0.6;

    @Override
    void opModeCycle() throws InterruptedException {

        final CollectionController controller = new CollectionController(hardwareMap);

        waitForStart();

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        telemetry.addData("Phase", "Moving");
        telemetry.update();


        straight(MOVE_SPEED, 2.0, 2.3);

//        controller.runArmToHeight(12.0, false, gamepad1);
//        controller.lowerArmToBottom(gamepad1);

        turn(TURN_SPEED, 0.98, TurnDirection.left, 0.8);
        straight(0.3);

        while (ColorClassifier.checkColor(robotSetup.getRightColorSensor(), robotSetup.getRightDistanceSensor()) != Color.BLACK);

        stopMotion();
        turn(TURN_SPEED, 0.30, TurnDirection.right, 0.4);

        controller.toggleFoundation(FoundationSide.RIGHT);
        straight(0.35, 0.1, 0.75);

        turn(TURN_SPEED, 0.3, TurnDirection.right, 0.5);

        straight(-1.0, 1.5, 2.0);

        turn(TURN_SPEED, 0.55, TurnDirection.left, 0.8);

        straight(-1.0);

        while(robotSetup.getBottomColorSensor().red() < 500);
        Thread.sleep(400);

        stopMotion();
        controller.toggleFoundation(FoundationSide.RIGHT);
        Thread.sleep(200);


        straight(MOVE_SPEED);
        while(robotSetup.getBottomColorSensor().red() < 500);
        Thread.sleep(300);
        stopMotion();

        //Get second brick

        turn(TURN_SPEED, 0.85, TurnDirection.right, 0.8);

        straight(1.0, 1.3, 1.4);

        turn(TURN_SPEED, 0.72, TurnDirection.left, 0.8);

        straight(0.3);

        while (ColorClassifier.checkColor(robotSetup.getRightColorSensor(), robotSetup.getRightDistanceSensor()) != Color.YELLOW);
        Thread.sleep(500);
        while (ColorClassifier.checkColor(robotSetup.getRightColorSensor(), robotSetup.getRightDistanceSensor()) != Color.BLACK);

        stopMotion();
        turn(TURN_SPEED, 0.30, TurnDirection.right, 0.4);
        straight(0.35, 0.5, 0.75);

        controller.toggleFoundation(FoundationSide.RIGHT);

        sleep(500);

//        turn(TURN_SPEED, 0.3, TurnDirection.right, 0.5);
//
//        straight(-1.0, 1.8, 2.0);
//
//        turn(TURN_SPEED, 0.60, TurnDirection.left, 0.8);

        straight(-1.0);

        while(robotSetup.getBottomColorSensor().red() < 500);
        Thread.sleep(500);

        stopMotion();
        controller.toggleFoundation(FoundationSide.RIGHT);
        Thread.sleep(1000);


        straight(MOVE_SPEED);
        while(robotSetup.getBottomColorSensor().red() < 500);
//        Thread.sleep(300);
        stopMotion();

        while(opModeIsActive());
    }
}
