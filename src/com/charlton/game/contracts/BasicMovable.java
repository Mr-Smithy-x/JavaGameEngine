package com.charlton.game.contracts;

public interface BasicMovable extends Movable {

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
