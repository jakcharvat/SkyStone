package org.firstinspires.ftc.teamcode.stick_game.ai;

import org.firstinspires.ftc.teamcode.stick_game.GameManager;

public abstract class AI {

    public AI(GameManager manager) {
        this.manager = manager;
    }

    final GameManager manager;

    abstract public boolean removeStick() throws CouldNotRemoveStickException;
}
