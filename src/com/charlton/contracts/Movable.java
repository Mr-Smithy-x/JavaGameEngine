package com.charlton.contracts;

import com.charlton.helpers.Lookup;

public interface Movable {

    double[] cos = Lookup.cos;
    double[] sin = Lookup.sin;

    void moveBy(double dx, double dy);
    void moveForwardBy(double dA);
    void moveBackwardBy(double dA);
    void rotateBy(int dA);
    void turnLeft(int dA);
    void turnRight(int dA);

}
