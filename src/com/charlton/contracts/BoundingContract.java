package com.charlton.contracts;

public interface BoundingContract<N extends Number>{
    int TYPE_CIRCLE = 0, TYPE_POLY = 1;
    N getX();
    N getY();
    void setX(N x);
    void setY(N y);
    N getWidth();
    N getHeight();
    N getRadius();
    N getVelocityX();
    N getVelocityY();
    void setVelocityX(N velocity_x);
    void setVelocityY(N velocity_y);
    N getAccelerationX();
    N getAccelerationY();
    void setAccelerationX(N acceleration_x);
    void setAccelerationY(N acceleration_y);
    void align();
    int getType();
}
