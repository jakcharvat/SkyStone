package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@SuppressWarnings({"StatementWithEmptyBody", "FieldCanBeLocal"})
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous", group = "Competition Code")
public class Autonomous extends AutoLinearOpMode {

//    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
//    private static final String LABEL_FIRST_ELEMENT = "Stone";
//    private static final String LABEL_SECOND_ELEMENT = "Skystone";
//    private static final String VUFORIA_KEY =
//            "ATo2Fcb/////AAABmdzs1kF7l03Wi+1A4+h9Gb8uBv/aY255UIi4TcFhJnqe6882bPbdwOMU1DllP2tR/SxA5C0RCsa2Q4YEHwx1Q7XStreON6ORBRV1c1BTKp3OAgfQfJwDdqZN0Qk+fDB2JDt8RwZ5/NRNJEwo8aW4slGfuw33MmQ+YQN2kyypkvNN6OwoMxUK5bmPC5M/3EVUWtcH5tU9YiYoJ5H085WFHEbzJHPrX2lVsdBq+hDB+Sf8HFQefprbaDf06BDGEstU0EC/45aihspR1MCt8Dm98qbAdD75Jk5BIr8pRtdEZAmefwTQnYu2ouZkTV/JOp5xOPq322UcQVRoflB7ypQ9konbTPSJmp5L3VVdX1EMCd86";
//    private VuforiaLocalizer vuforia;
//    private TFObjectDetector tfod;

    private static final double MOVE_SPEED = 0.5;
    private static final double TURN_SPEED = 0.3;

    @Override
    void opModeCycle() throws InterruptedException {

        final CollectionController controller = new CollectionController(hardwareMap);

        // --------------------------------------------------------------------------- Init TFOD

        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
//        initVuforia();
//
//        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
//            initTfod();
//        } else {
//            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
//        }
//
//        /**
//         * Activate TensorFlow Object Detection before we wait for the start command.
//         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
//         **/
//        if (tfod != null) {
//            tfod.activate();
//        }

        // --------------------------------------------------------------------------- Select where the robot is located on the field

//        PlayerColor color;
//        telemetry.addData("Selection: ", "Color");
//        telemetry.update();
//        while(true) {
//
//            if (gamepad1.x) {
//                color = PlayerColor.blue;
//                break;
//            } else if (gamepad1.y) {
//                color = PlayerColor.red;
//                break;
//            }
//
//        }
//
//        telemetry.addData("Color: ", color);
//        telemetry.addData("Selection: ", "Side");
//        telemetry.update();
//
//        FieldSide side;
//        while (true) {
//
//            if (gamepad1.left_bumper) {
//                side = FieldSide.left;
//                break;
//            } else if (gamepad1.right_bumper) {
//                side = FieldSide.right;
//                break;
//            }
//
//        }
//
//        telemetry.addData("Color: ", color);
//        telemetry.addData("Side: ", side);
//        telemetry.addData("Phase", "Waiting for Start");
//        telemetry.update();

        waitForStart();

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

//        telemetry.addData("Color: ", color);
//        telemetry.addData("Side: ", side);
        telemetry.addData("Phase", "Moving");
        telemetry.update();

        // --------------------------------------------------------------------------- Drive forward a bit

        straight(MOVE_SPEED, 2.2);
        turn(TURN_SPEED, 0.95, TurnDirection.left);
        straight(MOVE_SPEED);
        while (
                (Double.isNaN(robotSetup.getRightDistanceSensor().getDistance(DistanceUnit.CM))
                || robotSetup.getRightDistanceSensor().getDistance(DistanceUnit.CM) > 6.0)
                && ColorClassifier.checkColor(robotSetup.getRightColorSensor(), robotSetup.getRightDistanceSensor()) != Color.YELLOW
        ) {
            telemetry.addData("Distance", robotSetup.getRightDistanceSensor().getDistance(DistanceUnit.CM));
            telemetry.addData("Color", ColorClassifier.checkColor(robotSetup.getRightColorSensor(), robotSetup.getRightDistanceSensor()));
            telemetry.update();
        }
        stopMotion();

        turn(TURN_SPEED, 0.04, TurnDirection.left);
        straight(-MOVE_SPEED);

        while (!Double.isNaN(robotSetup.getRightDistanceSensor().getDistance(DistanceUnit.CM)));

//        straight(0.2);
//
//        while (Double.isNaN(robotSetup.getRightDistanceSensor().getDistance(DistanceUnit.CM)));
//        stopMotion();

        turn(TURN_SPEED, 0.05, TurnDirection.left);
        straight(0.2);

        while (ColorClassifier.checkColor(robotSetup.getRightColorSensor(), robotSetup.getRightDistanceSensor()) != Color.BLACK);

        stopMotion();

//        straight(-0.1, 0.35);


//        straight(0.2, 1000);

//        setPowerOnMainAndPerpendicular(0, 0.5);
//        while (robotSetup.getRightDistanceSensor().getDistance(DistanceUnit.CM) > 6.0);
//        stopMotion();

//        turn(TURN_SPEED, 0.15, TurnDirection.right);
        turn(TURN_SPEED, 0.30, TurnDirection.right);
        straight(0.1, 0.2);

        controller.toggleFoundation(FoundationSide.RIGHT);

//        wait(500);
        Thread.sleep(1000);

        straight(-1.0, 3.0);

        turn(TURN_SPEED, 0.50, TurnDirection.left);

        straight(-1.0, 1.5);
        stopMotion();
        controller.toggleFoundation(FoundationSide.RIGHT);
        Thread.sleep(1000);

        straight(MOVE_SPEED);
        while(robotSetup.getBottomColorSensor().red() < 500);
        stopMotion();

//        straight(-MOVE_SPEED);
//
//
//        controller.toggleFoundation(FoundationSide.RIGHT);

        while (opModeIsActive()) {
            telemetry.addData("Distance", robotSetup.getRightDistanceSensor().getDistance(DistanceUnit.CM));
            telemetry.addData("Color", ColorClassifier.checkColor(robotSetup.getRightColorSensor(), robotSetup.getRightDistanceSensor()));
            telemetry.addData("Red", robotSetup.getRightColorSensor().red());
            telemetry.addData("Green", robotSetup.getRightColorSensor().green());
            telemetry.addData("Blue", robotSetup.getRightColorSensor().blue());
            telemetry.update();
        }

//        for (int i = 0; i < 2; i++) {
//            straight(0.3);
//            while (ColorClassifier.checkColor(robotSetup.getRightColorSensor()) != Color.YELLOW);
//            while (ColorClassifier.checkColor(robotSetup.getRightColorSensor()) != Color.BLACK);
//
//            stopMotion();
//
//            straight(-0.3, 0.25);
//
//            controller.toggleFoundation();
//
//            straight(-0.3, 0.25);
//
//            turn(TURN_SPEED, 0.2, TurnDirection.right);
//            straight(-MOVE_SPEED, 4.0);
//            turn(TURN_SPEED, 0.2, TurnDirection.left);
//            straight(-MOVE_SPEED, 4.0);
//
//            controller.toggleFoundation();
//
//            if (i == 0) {
//                straight(MOVE_SPEED, 4.0);
//                turn(TURN_SPEED, 0.2, TurnDirection.right);
//                straight(MOVE_SPEED, 4.0);
//                turn(TURN_SPEED, 0.2, TurnDirection.left);
//            }
//        }


//        Recognition stone = null;
//
//        while (true) {
//            if (tfod != null) {
//                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
//                if (updatedRecognitions != null) {
//                    if (updatedRecognitions.size() == 1) {
//                        stone = updatedRecognitions.get(0);
//
//                        break;
//                    } else {
//                        for (Recognition recognition : updatedRecognitions) {
//                            if (stone == null) {
//                                stone = recognition;
//                                continue;
//                            }
//
//                            if (stone.getLeft() < recognition.getLeft()) stone = recognition;
//                        }
//
//                        break;
//                    }
//                }
//            } else break;
//        }
//
//        if (stone == null) throw new Exception("Stone null");

//        telemetry.addData("Stone", stone.getLabel());
//        telemetry.update();

        // --------------------------------------------------------------------------- Drive to first SkyStone and pick it up

        // --------------------------------------------------------------------------- Drive to building site

        // --------------------------------------------------------------------------- Place block

        // --------------------------------------------------------------------------- Drive back to pick up second SkyStone

        // --------------------------------------------------------------------------- Drive to building site

        // --------------------------------------------------------------------------- Place block

        // --------------------------------------------------------------------------- Park

    }


//    /**
//     * Initialize the Vuforia localization engine.
//     */
//    private void initVuforia() {
//        /*
//         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
//         */
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//
//        parameters.vuforiaLicenseKey = VUFORIA_KEY;
//        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
//
//        //  Instantiate the Vuforia engine
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//
//        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
//    }
//
//    /**
//     * Initialize the TensorFlow Object Detection engine.
//     */
//    private void initTfod() {
//        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
//        tfodParameters.minimumConfidence = 0.8;
//        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
//    }
}