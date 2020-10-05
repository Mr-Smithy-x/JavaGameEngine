package com.charlton.models;

import com.charlton.contracts.BoundingContract;
import com.charlton.contracts.Movable;

public class MovableObject implements Movable, BoundingContract<Number> {

    protected double world_x, world_y;
    protected double world_angle;
    protected double cos_angle;
    protected double sin_angle;

    @Override
    public void moveBy(double dx, double dy) {
        world_x += dx;
        world_y += dy;
    }

    @Override
    public void moveForwardBy(double dA) {
        double dx = (dA * cos_angle);
        double dy = (dA * sin_angle);
        this.moveBy(dx, dy);
    }

    @Override
    public void turnLeft(int dA)
    {
        rotateBy(-dA);
    }

    @Override
    public void turnRight(int dA)
    {
        rotateBy(dA);
    }

    @Override
    public void moveBackwardBy(double da){
        this.moveForwardBy(-da);
    }

    @Override
    public void rotateBy(int dA) {
        world_angle += dA;
        if (world_angle > 359) world_angle -= 360;
        if (world_angle < 0) world_angle += 360;
        if(world_angle > 360){
            return;
        }else if(world_angle < 0){
            return;
        }
        sin_angle = sin[(int)world_angle];
        cos_angle = cos[(int)world_angle];
    }


    public void setWorld(int x, int y) {
        this.world_x = x;
        this.world_y = y;
    }

    @Override
    public Number getX() {
        return world_x;
    }

    @Override
    public Number getY() {
        return world_y;
    }

    @Override
    public void setX(Number x) {
        this.world_x = x.doubleValue();
    }

    @Override
    public void setY(Number y) {
        this.world_y = y.doubleValue();
    }

    @Override
    public Number getWidth() {
        return 0;
    }

    @Override
    public Number getHeight() {
        return 0;
    }

    @Override
    public Number getRadius() {
        return  0;
    }

    @Override
    public void align() {

    }
}
