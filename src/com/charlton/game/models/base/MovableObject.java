package com.charlton.game.models.base;

import com.charlton.game.contracts.BoundingContract;
import com.charlton.game.contracts.Movable;

public abstract class MovableObject implements Movable, BoundingContract<Number> {

    protected double position_x, position_y;
    protected double drag_x = 0, drag_y = 0; //Lower the drag the lower the bounce
    protected double velocity_x = 0, velocity_y = 0;
    protected double accelerate_x = 0, accelerate_y = 0;
    protected double world_angle;
    protected double cos_angle;
    protected double sin_angle;

    protected int turnspeed = 2;
    protected int speed = 3;



    public double last_dx;
    public double last_dy;


    @Override
    public void turnToward(Movable circle) {
        double d = distanceTo(circle);
        if (toTheLeftOf(circle)) turnLeft(this.turnspeed);
        else turnRight(this.turnspeed);
    }

    @Override
    public void chase(Movable circle) {
        this.turnToward(circle);
        this.moveForwardBy(speed);
    }


    @Override
    public Movable setChaseSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public Movable setTurnSpeed(int turnspeed) {
        this.turnspeed = turnspeed;
        return this;
    }

    @Override
    public boolean toTheLeftOf(Movable c) {
        double dx = c.getX().doubleValue() - position_x;
        double dy = c.getY().doubleValue() - position_y;
        return sin_angle * dx - cos_angle * dy > 0;
    }

    @Override
    public boolean toTheRightOf(Movable c) {
        return !toTheLeftOf(c);
    }

    @Override
    public boolean inFrontOf(Movable c) {
        double dx = c.getX().doubleValue() - position_x;
        double dy = c.getY().doubleValue() - position_y;
        return cos_angle * dx + sin_angle * dy > 0;
    }

    @Override
    public boolean inVicinity(Movable c, double pixels) {
        double dx = position_x - c.getX().doubleValue();
        double dy = position_y - c.getY().doubleValue();
        double distance = dx * dx + dy * dy;
        double pixels_squared = pixels * pixels;
        if (distance < pixels_squared) {
            //System.out.printf("DISTANCE: %s, pixels: %s\n", distance, pixels * pixels);
        }
        return distance < pixels_squared;
    }

    @Override
    public double distanceTo(Movable c) {
        //double vx = point_x - x.doubleValue(); // |v| <vx, vy>
        //double vy = point_y - y.doubleValue();
        double vx = c.getX().doubleValue() - position_x;
        double vy = c.getY().doubleValue() - position_y;
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

        this.velocity_x *= drag_x;
        this.velocity_y *= drag_y;

        if (Math.abs(this.velocity_x) > 0.1) {
            this.position_x += this.velocity_x;
        }
        if (Math.abs(this.velocity_y) > 0.1) {
            this.position_y += this.velocity_y;
        }
    }

    @Override
    public void moveBy(double dx, double dy) {
        this.position_x += dx;
        this.position_y += dy;

        this.last_dx = dx;
        this.last_dy = dy;
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
    public void setWorld(double x, double y) {
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

    @Override
    public void setWorldAngle(double world_angle) {
        this.world_angle = world_angle;
        this.sin_angle = sin[(int) world_angle];
        this.cos_angle = cos[(int) world_angle];
    }

    @Override
    public double getSinAngle() {
        return sin_angle;
    }

    @Override
    public double getCosAngle() {
        return cos_angle;
    }

    @Override
    public double getWorldAngle() {
        return world_angle;
    }
}
