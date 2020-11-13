package com.charlton.game.contracts;

public interface BoundingContract<N extends Number> {
    int TYPE_CIRCLE = 0, TYPE_POLY = 1;

    void setX(N x);

    void setY(N y);

    N getX();

    N getY();

    N getWidth();

    N getHeight();

    N getRadius();

    int getType();
}
