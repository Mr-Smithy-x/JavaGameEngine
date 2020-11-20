package com.charlton.game.models.base.model3d.contracts;

import com.charlton.game.models.base.model2d.contracts.Gravitational2D;

public interface Gravitational3D extends Gravitational2D, Movable3D {

    void setDragZ(Number dragY);

    void setVelocityZ(Number velocityX);

    void setAccelerationZ(Number accelerationY);

    default void setVelocity(Number velocityX, Number velocityY, Number velocityZ) {
        setVelocity(velocityX, velocityY);
        setVelocityZ(velocityZ);
    }

    default void setAcceleration(Number accelerateX, Number accelerateY, Number accelerateZ) {
        setAcceleration(accelerateX, accelerateY);
        setAccelerationZ(accelerateZ);
    }

    default void setDrag(Number dragX, Number dragY, Number dragZ) {
        setDrag(dragX, dragY);
        setDragZ(dragZ);
    }

    default void toss(Number velocity_x, Number velocity_y, Number velocity_z) {
        setVelocity(velocity_x, velocity_y, velocity_z);
    }

    default Number getCurrentSpeed() {
        return Math.sqrt(
                getVelocityX().doubleValue() * getVelocityX().doubleValue() +
                getVelocityY().doubleValue() * getVelocityY().doubleValue() +
                getVelocityZ().doubleValue() * getVelocityZ().doubleValue()
        );
    }

    default void gravitate() {
        double velocityX = getVelocityX().doubleValue() + getAccelerationX().doubleValue();
        double velocityY = getVelocityY().doubleValue() + getAccelerationY().doubleValue();
        double velocityZ = getVelocityZ().doubleValue() + getAccelerationZ().doubleValue();
        setVelocity(velocityX, velocityY, velocityZ);

        velocityX *= getDragX().doubleValue();
        velocityY *= getDragY().doubleValue();
        velocityZ *= getDragZ().doubleValue();
        setVelocity(velocityX, velocityY, velocityZ);

        if (Math.abs(velocityX) > 0.1) {
            setX(this.getX().doubleValue() + velocityX);
        }
        if (Math.abs(velocityY) > 0.1) {
            setY(getY().doubleValue() + velocityY);
        }
        if (Math.abs(velocityZ) > 0.1) {
            setZ(getZ().doubleValue() + velocityZ);
        }
    }

    Number getDragZ();
    Number getVelocityZ();
    Number getAccelerationZ();
}
