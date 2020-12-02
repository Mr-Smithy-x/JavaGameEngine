package com.charlton.game.models.base.model2d;

import com.charlton.game.models.base.model2d.contracts.Movable2D;

public abstract class MovableObject2D implements Movable2D {

    protected double x, y;
    protected double width, height;
    protected int world_angle;

    public double last_dx;
    public double last_dy;






    @Override
    public Number getX() {
        return x;
    }

    @Override
    public Number getY() {
        return y;
    }

    @Override
    public void setX(Number x) {
        this.x = x.doubleValue();
    }

    @Override
    public void setY(Number y) {
        this.y = y.doubleValue();
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
