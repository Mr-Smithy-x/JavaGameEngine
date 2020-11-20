package com.charlton.game.contracts;

import com.charlton.game.models.base.model2d.contracts.Movable2D;

public interface BasicMovable extends Movable2D {

    float getSpeed();

    default void moveLeft() {
        moveLeft(getSpeed());
    }

    default void moveRight() {
        moveRight(getSpeed());
    }

    default void moveUp() {
        moveUp(getSpeed());
    }

    default void moveDown() {
        moveDown(getSpeed());
    }
}
