package com.charlton.game.models.base.model3d.contracts;

import com.charlton.game.models.base.model2d.contracts.Boundable2D;

public interface Boundable3D extends Boundable2D {
    Number getZ();
    void setZ(Number z);

    Number getDistance();
    void setDistance(Number distance);

    default void setWorld(Number x, Number y, Number z) {
        setWorld(x, y);
        setZ(z);
    }
}
