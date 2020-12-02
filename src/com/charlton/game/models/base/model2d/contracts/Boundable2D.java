package com.charlton.game.models.base.model2d.contracts;

public interface Boundable2D {

    void setX(Number x);
    void setY(Number y);

    Number getX();
    Number getY();
    Number getWidth();
    Number getHeight();
    Number getRadius();

    int getType();


    default void setWorld(Number x, Number y){
        setX(x);
        setY(y);
    }

    default Number getX2() {
        return getX().doubleValue() + getWidth().doubleValue();
    }

    default Number getY2() {
        return getY().doubleValue() + getHeight().doubleValue();
    }
}
