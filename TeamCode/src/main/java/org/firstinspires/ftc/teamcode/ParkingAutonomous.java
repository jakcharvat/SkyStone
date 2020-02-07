package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("StatementWithEmptyBody")
@Autonomous(name = "Parking Autonomous", group = "Competition")
public class ParkingAutonomous extends TeleLinearOpMode {
    @Override
    void opModeCycle() throws InterruptedException, Exception {

        waitForStart();

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(timer.time(TimeUnit.SECONDS) < 27);

        final CollectionController controller = new CollectionController(hardwareMap);
        controller.rotateArm(ArmDirection.FORWARD);
        straight(1);

        while (robotSetup.getBottomColorSensor().red() < 500
                && robotSetup.getBottomColorSensor().blue() < 500);

        Thread.sleep(300);
        stopMotion();

    }
}
