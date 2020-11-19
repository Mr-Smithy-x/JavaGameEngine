package com.charlton.game.contracts;

import com.charlton.game.helpers.TrigLookupHelper;

public interface Movable extends Boundable, CameraContract {

    double[] cos = TrigLookupHelper.cos;
    double[] sin = TrigLookupHelper.sin;

    int getWorldAngle();
    void setWorldAngle(int world_angle);

    default double getSinAngle(){
        return sin[getWorldAngle()];
    }

    default double getCosAngle() {
        return cos[getWorldAngle()];
    }

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


    default void moveBy(Number dx, Number dy) {
        double x = getX().doubleValue();
        double y = getY().doubleValue();
        x += dx.doubleValue();
        y += dy.doubleValue();
        setWorld(x, y);
    }

    default void moveForwardBy(Number dA) {
        double dx = (dA.doubleValue() * getCosAngle());
        double dy = (dA.doubleValue() * getSinAngle());
        moveBy(dx, dy);
    }

    default void moveBackwardBy(Number dA) {
        this.moveForwardBy(-dA.doubleValue());
    }


    default void turnLeft(Number dA) {
        rotateBy(-dA.doubleValue());
    }

    default void turnRight(Number dA) {
        rotateBy(dA.doubleValue());
    }

    default void rotateBy(Number dA) {
        int worldAngle = getWorldAngle();
        worldAngle += dA.intValue();
        if (worldAngle > 359) worldAngle -= 360;
        if (worldAngle < 0) worldAngle += 360;
        setWorldAngle(worldAngle);
    }


}
