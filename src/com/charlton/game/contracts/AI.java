package com.charlton.game.contracts;

public interface AI extends CollisionDetection {


    default void turnToward(Movable circle, Number turnspeed) {
        Number distance = distanceTo(circle);
        if (toTheLeftOf(circle)) turnLeft(turnspeed);
        else turnRight(turnspeed);
    }

    default void chase(Movable movable, Number speed) {
        this.turnToward(movable, speed);
        this.moveForwardBy(speed);
    }


    void setChaseSpeed(int speed);

    void setTurnSpeed(int turnspeed);

    default boolean toTheLeftOf(Movable movable) {
        double dx = movable.getX().doubleValue() - getX().doubleValue();
        double dy = movable.getY().doubleValue() - getY().doubleValue();
        return getSinAngle() * dx - getCosAngle() * dy > 0;
    }

    default boolean toTheRightOf(Movable movable) {
        return !toTheLeftOf(movable);
    }

    default boolean inFrontOf(Movable movable) {
        double dx = movable.getX().doubleValue() - getX().doubleValue();
        double dy = movable.getY().doubleValue() - getY().doubleValue();
        return getCosAngle() * dx + getSinAngle() * dy > 0;
    }

    default boolean inVicinity(Movable movable, double pixels) {
        double dx = getX().doubleValue() - movable.getX().doubleValue();
        double dy = getY().doubleValue() - movable.getY().doubleValue();
        double distance = dx * dx + dy * dy;
        double pixels_squared = pixels * pixels;
        return distance < pixels_squared;
    }

    // |v| <vx, vy>
    default Number distanceTo(Movable movable) {
        double vx = movable.getX().doubleValue() - getX().doubleValue();
        double vy = movable.getY().doubleValue() - getY().doubleValue();
        return getSinAngle() * vx - getCosAngle() * vy;
    }

}
