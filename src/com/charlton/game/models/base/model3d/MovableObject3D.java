package com.charlton.game.models.base.model3d;

import com.charlton.game.models.base.model2d.MovableObject2D;
import com.charlton.game.models.base.model3d.contracts.Movable3D;

public abstract class MovableObject3D extends MovableObject2D implements Movable3D {

    protected double z;
    protected double distance;


    @Override
    public Number getZ() {
        return z;
    }

    @Override
    public void setZ(Number z) {
        this.z = z.doubleValue();
    }

}
