package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@SuppressWarnings({"StatementWithEmptyBody", "FieldCanBeLocal"})
@Autonomous(name = "Autonomous Red", group = "Competition")
public class AutonomousRed extends AutoLinearOpMode {


    private static final double MOVE_SPEED = 0.5;
    private static final double TURN_SPEED = 0.3;

    @Override
    void opModeCycle() throws InterruptedException {

        final CollectionController controller = new CollectionController(hardwareMap);

        waitForStart();

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        telemetry.addData("Phase", "Moving");
        telemetry.update();


        straight(MOVE_SPEED, 2.0, 2.3);

        controller.runArmToHeight(12.0, false, gamepad1);
        controller.lowerArmToBottom(gamepad1);

        turn(TURN_SPEED, 0.95, TurnDirection.left, 1.5);
        straight(0.2);

        while (ColorClassifier.checkColor(robotSetup.getRightColorSensor(), robotSetup.getRightDistanceSensor()) != Color.BLACK);

        stopMotion();
        turn(TURN_SPEED, 0.30, TurnDirection.right, 1.0);
        straight(0.1, 0.2, 0.75);

        controller.toggleFoundation(FoundationSide.RIGHT);

        sleep(1000);

        turn(TURN_SPEED, 0.3, TurnDirection.right, 1.0);

        straight(-1.0, 1.5, 2.0);

        turn(TURN_SPEED, 0.60, TurnDirection.left, 1.5);

        straight(-1.0);

        while(robotSetup.getBottomColorSensor().red() < 500);
        Thread.sleep(500);

        stopMotion();
        controller.toggleFoundation(FoundationSide.RIGHT);
        Thread.sleep(1000);

        controller.rotateArm(ArmDirection.FORWARD);

        straight(MOVE_SPEED);
        while(robotSetup.getBottomColorSensor().red() < 500);
//        Thread.sleep(300);
        stopMotion();

        straight(-0.1, 0.1, 0.5);

        while(opModeIsActive());
    }
}