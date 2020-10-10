package com.charlton.models;

import com.charlton.contracts.BoundingContract;
import com.charlton.contracts.Movable;

public abstract class MovableObject implements Movable, BoundingContract<Number> {

    protected double position_x, position_y;
    protected double drag_x = 0, drag_y = 0; //Lower the drag the lower the bounce
    protected double velocity_x = 0, velocity_y = 0;
    protected double accelerate_x = 0, accelerate_y = 0;
    protected double world_angle;
    protected double cos_angle;
    protected double sin_angle;


    protected boolean bouncy = false;
    protected boolean automate = false;


    @Override
    public boolean isBouncy() {
        return bouncy;
    }

    @Override
    public Movable setBouncy(boolean bouncy) {
        this.bouncy = bouncy;
        return this;
    }

    public void bounce() {
        this.velocity_y = -drag_y * velocity_y;
    }

    @Override
    public Movable setVelocity(double velocity_x, double velocity_y) {
        this.velocity_x = velocity_x;
        this.velocity_y = velocity_y;
        return this;
    }

    @Override
    public Movable setAcceleration(double accelerate_x, double accelerate_y) {
        this.accelerate_x = accelerate_x;
        this.accelerate_y = accelerate_y;
        return this;
    }

    @Override
    public void moveBy(double dx, double dy) {
        this.position_x += dx;
        this.position_y += dy;
    }

    @Override
    public void moveForwardBy(double dA) {
        double dx = (dA * cos_angle);
        double dy = (dA * sin_angle);
        this.moveBy(dx, dy);
    }


    public double getSpeed() {
        return Math.sqrt(velocity_x * velocity_x + velocity_y * velocity_y);
    }

    @Override
    public void jump(double velocity) {
        setVelocity(0, -velocity);
    }

    @Override
    public void toss(double velocity_x, double velocity_y) {
        setVelocity(velocity_x, velocity_y);
    }

    @Override
    public void move() {
        this.velocity_x += accelerate_x; //Accelerate
        this.velocity_y += accelerate_y;
        this.position_x += this.velocity_x;
        this.position_y += this.velocity_y;
    }

    @Override
    public void turnLeft(int dA) {
        rotateBy(-dA);
    }

    @Override
    public void turnRight(int dA) {
        rotateBy(dA);
    }

    @Override
    public void moveBackwardBy(double da) {
        this.moveForwardBy(-da);
    }

    @Override
    public void rotateBy(int dA) {
        world_angle += dA;
        if (world_angle > 359) world_angle -= 360;
        if (world_angle < 0) world_angle += 360;
        if (world_angle > 360) {
            return;
        } else if (world_angle < 0) {
            return;
        }
        sin_angle = sin[(int) world_angle];
        cos_angle = cos[(int) world_angle];
    }


    @Override
    public void setWorld(int x, int y) {
        this.position_x = x;
        this.position_y = y;
    }

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
        return 0;
    }

    @Override
    public Number getHeight() {
        return 0;
    }


    @Override
    public Number getRadius() {
        return 0;
    }

    @Override
    public void align() {

    }

    @Override
    public Movable setDrag(double drag_x, double drag_y){
        this.drag_x = drag_x;
        this.drag_y = drag_y;
        return this;
    }
}
