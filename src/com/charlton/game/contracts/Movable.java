package com.charlton.game.contracts;

import com.charlton.game.helpers.TrigLookupHelper;

public interface Movable {

    double[] cos = TrigLookupHelper.cos;
    double[] sin = TrigLookupHelper.sin;

    double getWorldAngle();
    double getSinAngle();
    double getCosAngle();
    void moveBy(double dx, double dy);
    void moveForwardBy(double dA);
    void moveBackwardBy(double dA);
    void gravitate();
    void rotateBy(int dA);
    void jump(double velocity);
    void toss(double velocity_x, double velocity_y);
    void turnLeft(int dA);
    void turnRight(int dA);
    double distanceTo(Movable c);
    void bounce();


    boolean toTheLeftOf(Movable c);
    boolean toTheRightOf(Movable c);
    boolean inFrontOf(Movable c);
    boolean inVicinity(Movable c, double pixels);

    Number getX();
    Number getY();
    Number getWidth();
    Number getHeight();
    Number getRadius();
    Number getVelocityX();
    Number getVelocityY();
    Number getAccelerationX();
    Number getAccelerationY();
    Movable setVelocity(double velocity_x, double velocity_y);
    Movable setAcceleration(double accelerate_x, double accelerate_y);
    Movable setDrag(double drag_x, double drag_y);
    void setX(Number x);
    void setY(Number y);
    void setWorld(double x, double y);
    void setWorldAngle(double world_angle);
    void setVelocityX(Number velocity_x);
    void setVelocityY(Number velocity_y);
    void setAccelerationX(Number acceleration_x);
    void setAccelerationY(Number acceleration_y);
    void chase(Movable movable);
    Movable setChaseSpeed(int speed);
    Movable setTurnSpeed(int turnspeed);
    void turnToward(Movable circle);
    void bind(MovableCollision movable);



    default void moveLeft(Number speed) {
        moveBy(-speed.doubleValue(), 0);
    }

    default void moveRight(Number speed) {
        moveBy(speed.doubleValue(), 0);
    }

    default void moveDown(Number speed) {
        moveBy(0, speed.doubleValue());
    }

    default void moveUp(Number speed) {
        moveBy(0, -speed.doubleValue());
    }

}
