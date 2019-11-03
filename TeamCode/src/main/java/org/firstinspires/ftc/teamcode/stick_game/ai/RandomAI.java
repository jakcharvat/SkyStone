package org.firstinspires.ftc.teamcode.stick_game.ai;

import org.firstinspires.ftc.teamcode.stick_game.GameManager;
import org.firstinspires.ftc.teamcode.stick_game.GameRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAI extends AI {

    RandomAI(GameManager manager) {
        super(manager);
    }

    final private Random random = new Random();

    @Override
    public boolean removeStick() {
        List<Integer> rows = new ArrayList<>();

        for (GameRow row : manager.board()) {
            if (!row.isEmpty()) {
                rows.add(row.rowNumber());
            }
        }

        int row = rows.get(random.nextInt(rows.size()));
        int stick;

        try {
            stick = random.nextInt(manager.board().get(row).currentNumberOfSticks() - 1);
        } catch(Exception e) {
            stick = 0;
        }

        manager.removeAllToTheRight(row, stick);
        return true;
    }
}
