package com.charlton.game.models.base.model2d.contracts;

public interface Gravitational2D extends Movable2D {
    void setDragX(Number dragX);

    void setDragY(Number dragY);

    void setVelocityX(Number velocityX);

    void setVelocityY(Number velocityY);

    void setAccelerationX(Number accelerationX);

    void setAccelerationY(Number accelerationY);

    default void setVelocity(Number velocityX, Number velocityY) {
        setVelocityX(velocityX);
        setVelocityY(velocityY);
    }

    default void setAcceleration(Number accelerateX, Number accelerateY) {
        setAccelerationX(accelerateX);
        setAccelerationY(accelerateY);
    }

    default void setDrag(Number dragX, Number dragY) {
        setDragX(dragX);
        setDragY(dragY);
    }


    default void jump(Number velocity) {
        setVelocityY(-velocity.doubleValue());
    }

    default void toss(Number velocity_x, Number velocity_y) {
        setVelocity(velocity_x, velocity_y);
    }

    default void bounce() {
        if (Math.abs(getVelocityY().doubleValue()) < 0.01) {
            setVelocityY(-getDragY().doubleValue() * getVelocityY().doubleValue());
        }
    }

    default Number getCurrentSpeed() {
        return Math.sqrt(getVelocityX().doubleValue() * getVelocityX().doubleValue() + getVelocityY().doubleValue() * getVelocityY().doubleValue());
    }

    default void gravitate() {
        double velocityX = getVelocityX().doubleValue() + getAccelerationX().doubleValue();
        double velocityY = getVelocityY().doubleValue() + getAccelerationY().doubleValue();
        setVelocity(velocityX, velocityY);

        velocityX *= getDragX().doubleValue();
        velocityY *= getDragY().doubleValue();
        setVelocity(velocityX, velocityY);

        if (Math.abs(velocityX) > 0.1) {
            setX(this.getX().doubleValue() + velocityX);
        }
        if (Math.abs(velocityY) > 0.1) {
            setY(getY().doubleValue() + velocityY);
        }
    }

    Number getDragX();

    Number getDragY();

    Number getVelocityX();

    Number getVelocityY();

    Number getAccelerationX();

    Number getAccelerationY();

}
