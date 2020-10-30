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


    public boolean toTheLeftOf(MovableObject c) {
        double dx = position_x - c.position_x;
        double dy = position_y - c.position_y;
        return sin_angle * dx - cos_angle * dy > 0;
    }

    public boolean toTheRightOf(MovableObject c) {
        return !toTheLeftOf(c);
    }

    public boolean inFrontOf(MovableObject c) {
        double dx = c.position_x - position_x;
        double dy = c.position_y - position_y;
        return cos_angle * (dx) + sin_angle * dy > 0;
    }

    public boolean inVicinity(MovableObject c, double pixels) {
        double dx = position_x - c.position_x;
        double dy = position_y - c.position_y;
        return dx * dx + dy + dy < pixels * pixels;
    }

    public double distanceTo(MovableObject c) {
        //double vx = point_x - x.doubleValue(); // |v| <vx, vy>
        //double vy = point_y - y.doubleValue();
        double vx = c.position_x - position_x;
        double vy = c.position_y - position_y;
        return sin_angle * vx - cos_angle * vy;
    }


    public void bounce() {
        if (Math.abs(velocity_y) < 0.01) {
            this.velocity_y = -drag_y * velocity_y;
        }
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
    public void gravitate() {
        this.velocity_x += accelerate_x; //Accelerate
        this.velocity_y += accelerate_y;
        this.position_x += this.velocity_x;
        this.position_y += this.velocity_y;
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

    @Override
    public void moveBackwardBy(double da) {
        this.moveForwardBy(-da);
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
    public Number getVelocityX() {
        return velocity_x;
    }

    @Override
    public Number getVelocityY() {
        return velocity_y;
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
    public Movable setDrag(double drag_x, double drag_y) {
        this.drag_x = drag_x;
        this.drag_y = drag_y;
        return this;
    }
}
