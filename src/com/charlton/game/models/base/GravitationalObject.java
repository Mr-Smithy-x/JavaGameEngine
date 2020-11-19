package com.charlton.game.models.base;

import com.charlton.game.contracts.Gravitational;

public abstract class GravitationalObject extends MovableObject implements Gravitational {

    protected double drag_x = 0, drag_y = 0; //Lower the drag the lower the bounce
    protected double velocity_x = 0, velocity_y = 0;
    protected double accelerate_x = 0, accelerate_y = 0;
    protected int turnspeed = 2;
    protected int speed = 3;


    @Override
    public Number getVelocityX() {
        return velocity_x;
    }

    @Override
    public Number getVelocityY() {
        return velocity_y;
    }

    @Override
    public void setDragX(Number dragX) {
        this.drag_x = dragX.doubleValue();
    }

    @Override
    public void setDragY(Number dragY) {
        this.drag_y = dragY.doubleValue();
    }

    @Override
    public void setVelocityX(Number velocity_x) {
        this.velocity_x = velocity_x.doubleValue();
    }

    @Override
    public void setVelocityY(Number velocity_y) {
        this.velocity_y = velocity_y.doubleValue();
    }

    @Override
    public Number getAccelerationX() {
        return accelerate_x;
    }

    @Override
    public Number getAccelerationY() {
        return accelerate_y;
    }

    @Override
    public void setAccelerationX(Number acceleration_x) {
        this.accelerate_x = acceleration_x.doubleValue();
    }

    @Override
    public void setAccelerationY(Number acceleration_y) {
        this.accelerate_y = acceleration_y.doubleValue();
    }

    @Override
    public Number getDragX() {
        return drag_x;
    }

    @Override
    public Number getDragY() {
        return drag_y;
    }
}
