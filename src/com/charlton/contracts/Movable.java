package com.charlton.contracts;

import com.charlton.helpers.Lookup;

public interface Movable {

    double[] cos = Lookup.cos;
    double[] sin = Lookup.sin;
    Movable setVelocity(double velocity_x, double velocity_y);
    Movable setAcceleration(double accelerate_x, double accelerate_y);
    Movable setDrag(double drag_x, double drag_y);
    void setWorldAngle(double world_angle);

    void moveBy(double dx, double dy);
    void moveForwardBy(double dA);
    void moveBackwardBy(double dA);
    void gravitate();
    void rotateBy(int dA);
    void jump(double velocity);
    void toss(double velocity_x, double velocity_y);
    void turnLeft(int dA);
    void turnRight(int dA);
    void setWorld(double x, double y);
    void bounce();

}
