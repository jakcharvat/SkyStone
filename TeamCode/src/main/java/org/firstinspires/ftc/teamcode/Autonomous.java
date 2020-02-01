package org.firstinspires.ftc.teamcode;

@SuppressWarnings("StatementWithEmptyBody")
public class Autonomous extends AutoLinearOpMode {
    @Override
    void opModeCycle() throws InterruptedException {
        // --------------------------------------------------------------------------- Select where the robot is located on the field

        PlayerColor color;
        while(true) {

            if (gamepad1.x) {
                color = PlayerColor.blue;
                break;
            } else if (gamepad1.y) {
                color = PlayerColor.red;
                break;
            }

        }

        FieldSide side;
        while (true) {

            if (gamepad1.left_bumper) {
                side = FieldSide.left;
                break;
            } else if (gamepad1.right_bumper) {
                side = FieldSide.right;
                break;
            }

        }

        // --------------------------------------------------------------------------- Drive forward a bit

        // --------------------------------------------------------------------------- Rotate towards stones and scan

        // --------------------------------------------------------------------------- Drive to first SkyStone and pick it up

        // --------------------------------------------------------------------------- Drive to building site

        // --------------------------------------------------------------------------- Place block

        // --------------------------------------------------------------------------- Drive back to pick up second SkyStone

        // --------------------------------------------------------------------------- Drive to building site

        // --------------------------------------------------------------------------- Place block

        // --------------------------------------------------------------------------- Park

    }
}

enum PlayerColor {
    blue,
    red
}

enum FieldSide {
    left,
    right
}
