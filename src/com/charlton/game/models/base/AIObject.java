package com.charlton.game.models.base;

import com.charlton.game.contracts.AI;

public abstract class AIObject extends GravitationalObject implements AI {

    @Override
    public void setChaseSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void setTurnSpeed(int turnspeed) {
        this.turnspeed = turnspeed;
    }
}
