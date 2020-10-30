package com.charlton.models;

public class BadBoundingCircle extends BoundingCircle {


    public BadBoundingCircle(double x, double y, double r, int world_angle) {
        super(x, y, r, world_angle);
    }


    public void turnToward(MovableObject circle) {
        double d = distanceTo(circle);
        if (!circle.inFrontOf(this)) turnLeft(7);
        if (!circle.toTheLeftOf(this)) turnRight(7);
    }

    public void chase(MovableObject circle) {
        turnToward(circle);
        moveForwardBy(7);
    }

}
