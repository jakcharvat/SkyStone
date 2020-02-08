package org.firstinspires.ftc.teamcode;
//Not edited for blue side
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous(name = "Optimize Autonomous Test Blue", group = "Competition")
public class OptimizedAutonomousTestBlue extends AutoLinearOpMode {

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

        turn(TURN_SPEED, 1.1, TurnDirection.right, 1.0);
        straight(0.2);

        while (ColorClassifier.checkColor(robotSetup.getLeftColorSensor(), robotSetup.getLeftDistanceSensor()) != Color.BLACK) {
            if (isStopRequested()) {
                break;
            }
        }

        stopMotion();
        turn(TURN_SPEED, 0.30, TurnDirection.left, 0.4);
        straight(0.35, 0.2, 0.75);

        controller.toggleFoundation(FoundationSide.LEFT);

        sleep(1000);

        turn(TURN_SPEED, 0.3, TurnDirection.left, 0.5);

        straight(-1.0, 1.5, 2.0);

        turn(TURN_SPEED, 0.60, TurnDirection.right, 0.8);

        straight(-1.0);

        while(robotSetup.getBottomColorSensor().blue() < 500) {
            if (isStopRequested()) break;
        }

        Thread.sleep(500);

        stopMotion();
        controller.toggleFoundation(FoundationSide.LEFT);
        Thread.sleep(1000);


        straight(MOVE_SPEED);
        while(robotSetup.getBottomColorSensor().blue() < 500) {
            if (isStopRequested()) break;
        }
        Thread.sleep(300);
        stopMotion();

        //Get second brick

        turn(TURN_SPEED, 0.85, TurnDirection.left, 0.8);

        straight(1.0, 1.3, 1.4);

        turn(TURN_SPEED, 0.72, TurnDirection.right, 0.8);

        straight(0.3);

        while (ColorClassifier.checkColor(robotSetup.getLeftColorSensor(), robotSetup.getLeftDistanceSensor()) != Color.YELLOW) {
            if (isStopRequested()) break;
        }
        Thread.sleep(500);
        while (ColorClassifier.checkColor(robotSetup.getLeftColorSensor(), robotSetup.getLeftDistanceSensor()) != Color.BLACK) {
            if (isStopRequested()) break;
        }

        stopMotion();
        turn(TURN_SPEED, 0.30, TurnDirection.left, 0.4);
        straight(0.35, 0.5, 0.75);

        controller.toggleFoundation(FoundationSide.LEFT);

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
        controller.toggleFoundation(FoundationSide.LEFT);
        Thread.sleep(1000);


        straight(MOVE_SPEED);
        while(robotSetup.getBottomColorSensor().red() < 500);
//        Thread.sleep(300);
        stopMotion();

        while(opModeIsActive());
    }
}
