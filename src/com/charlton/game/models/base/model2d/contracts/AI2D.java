package com.charlton.game.models.base.model2d.contracts;

public interface AI2D extends CollisionDetection2D {


    default void turnToward(Movable2D circle, Number turnspeed) {
        Number distance = distanceTo(circle);
        if (toTheLeftOf(circle)) turnLeft(turnspeed);
        else turnRight(turnspeed);
    }

    default void chase(Movable2D movable2D, Number speed) {
        this.turnToward(movable2D, speed);
        this.moveForwardBy(speed);
    }


    void setChaseSpeed(int speed);

    void setTurnSpeed(int turnspeed);

    default boolean toTheLeftOf(Movable2D movable2D) {
        double dx = movable2D.getX().doubleValue() - getX().doubleValue();
        double dy = movable2D.getY().doubleValue() - getY().doubleValue();
        return getSinAngle() * dx - getCosAngle() * dy > 0;
    }

    default boolean toTheRightOf(Movable2D movable2D) {
        return !toTheLeftOf(movable2D);
    }

    default boolean inFrontOf(Movable2D movable2D) {
        double dx = movable2D.getX().doubleValue() - getX().doubleValue();
        double dy = movable2D.getY().doubleValue() - getY().doubleValue();
        return getCosAngle() * dx + getSinAngle() * dy > 0;
    }

    default boolean inVicinity(Movable2D movable2D, double pixels) {
        double dx = getX().doubleValue() - movable2D.getX().doubleValue();
        double dy = getY().doubleValue() - movable2D.getY().doubleValue();
        double distance = dx * dx + dy * dy;
        double pixels_squared = pixels * pixels;
        return distance < pixels_squared;
    }

    // |v| <vx, vy>
    default Number distanceTo(Movable2D movable2D) {
        double vx = movable2D.getX().doubleValue() - getX().doubleValue();
        double vy = movable2D.getY().doubleValue() - getY().doubleValue();
        return getSinAngle() * vx - getCosAngle() * vy;
    }

}
