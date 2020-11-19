package com.charlton.game.models.base;

import com.charlton.game.contracts.Movable;

public abstract class MovableObject implements Movable {

    protected double position_x, position_y;
    protected double width, height;
    protected int world_angle;

    public double last_dx;
    public double last_dy;






    @Override
    public Number getX() {
        return position_x;
    }

    @Override
    public Number getY() {
        return position_y;
    }

    @Override
    public void setX(Number x) {
        this.position_x = x.doubleValue();
    }

    @Override
    public void setY(Number y) {
        this.position_y = y.doubleValue();
    }

    @Override
    public Number getWidth() {
        return width;
    }

    @Override
    public Number getHeight() {
        return height;
    }

    @Override
    public Number getRadius() {
        return width / 2;
    }

    @Override
    public int getType() {
        return 0;
    }


    @Override
    public void setWorldAngle(int world_angle) {
        this.world_angle = world_angle;
    }

    @Override
    public int getWorldAngle() {
        return world_angle;
    }
}
