package com.charlton.contracts;

public interface BoundingContract<N extends Number> {
    N getX();
    N getY();
    void setX(N x);
    void setY(N y);
    N getWidth();
    N getHeight();
    N getRadius();
    void align();
}
