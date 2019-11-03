package org.firstinspires.ftc.teamcode.stick_game.ai;

import org.firstinspires.ftc.teamcode.stick_game.GameManager;

abstract class AI {

    AI(GameManager manager) {
        this.manager = manager;
    }

    final GameManager manager;

    abstract public boolean removeStick() throws CouldNotRemoveStickException;
}
