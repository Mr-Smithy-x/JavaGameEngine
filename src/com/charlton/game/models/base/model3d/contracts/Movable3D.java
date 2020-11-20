package com.charlton.game.models.base.model3d.contracts;

import com.charlton.game.helpers.TrigLookupHelper;
import com.charlton.game.models.base.model2d.contracts.Movable2D;

public interface Movable3D extends Movable2D, Boundable3D {

    double[] tan = TrigLookupHelper.tan;

    default void moveBy(Number dx, Number dy, Number dz) {
        double x = getX().doubleValue();
        double y = getY().doubleValue();
        double z = getZ().doubleValue();
        x += dx.doubleValue();
        y += dy.doubleValue();
        z += dz.doubleValue();
        setWorld(x, y, z);
    }

    default double getTanAngle() {
        return tan[getWorldAngle()];
    }

    default void moveIn(Number speed) {
        moveBy(0, 0, speed.doubleValue());
    }

    default void moveOut(Number speed) {
        moveBy(0, 0, -speed.doubleValue());
    }

}
