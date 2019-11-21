package org.firstinspires.ftc.teamcode.stick_game;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.stick_game.ai.AI;
import org.firstinspires.ftc.teamcode.stick_game.ai.BinaryAI;
import org.firstinspires.ftc.teamcode.stick_game.ai.CouldNotRemoveStickException;

@TeleOp(name = "NIM", group = "NonCompetition code")
public class GameLoop extends LinearOpMode {

    private boolean hasRobotStartedPlay = false;

    @Override
    public void runOpMode() throws InterruptedException {
        final GameManager manager = new GameManager(4, hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            if (manager.currentPlayer() == Player.human) {
                hasRobotStartedPlay = false;
                if (gamepad1.dpad_right) {
                    manager.moveManager.moveRight();
                }
                if (gamepad1.dpad_left) {
                    manager.moveManager.moveLeft();
                }
                if (gamepad1.dpad_up && manager.rowBlock() == null) {
                    manager.moveManager.moveUp();
                }
                if (gamepad1.dpad_down && manager.rowBlock() == null) {
                    manager.moveManager.moveDown();
                }
                if (gamepad1.a) {
                    telemetry.addData("Removing Stick", "");
                    telemetry.update();

                    Thread.sleep(1000);

                    telemetry.addData("Removing Stick", manager.moveManager.currentStick().rowNumber());
                    telemetry.update();

                    Thread.sleep(1000);

                    telemetry.addData("Removing Stick", manager.moveManager.currentStick().stickNumber());
                    telemetry.update();

                    Thread.sleep(1000);

                    MoveManager moveManager = new MoveManager(hardwareMap, telemetry);
                    DcMotor[] motors = new DcMotor[]{moveManager.getLeftBackMotor(), moveManager.getRightBackMotor()};
                    final int distanceFromShelf = 10;
                    final double calculatedTargetPosition = ((distanceFromShelf*Math.sqrt(2.00))/moveManager.wheelCircumference)*moveManager.ticksInRotation;
                    for(int i = 0;i < motors.length; i++){
                        DcMotor motor = motors[i];
                        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        motor.setTargetPosition((int)calculatedTargetPosition);
                        motor.setPower(0.5);
                        while(motor.isBusy()){}
                        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    }
                    for(int i = 0;i < motors.length; i++){
                        DcMotor motor = motors[i];
                        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        motor.setTargetPosition(-(int)calculatedTargetPosition);
                        motor.setPower(0.5);
                        while(motor.isBusy()){}
                        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    }

                    telemetry.addData("Stick Removed", "");
                    telemetry.update();

                    Thread.sleep(1000);
                }
                if (gamepad1.left_trigger > 0.7) {
                    manager.handoffPlayer();
                }
            }
            if (manager.currentPlayer() == Player.robot && !hasRobotStartedPlay) {
                AI ai = new BinaryAI(manager);
                try {
                    ai.removeStick();
                    manager.handoffPlayer();
                    hasRobotStartedPlay = true;
                } catch (CouldNotRemoveStickException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
