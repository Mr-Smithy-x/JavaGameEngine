package com.charlton.game.display;

import com.charlton.game.contracts.Movable;

public abstract class BaseCamera {

    protected float x, y;
    protected float x_origin, y_origin;
    protected float scale = 1;


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void moveBy(double dx, double dy) {
        x += dx;
        y += dy;
    }


    public void setOrigin(Movable e, float screen_width, float screen_height) {
        x_origin = e.getX().floatValue() - screen_width / 2 + e.getWidth().floatValue() / 2;
        y_origin = e.getY().floatValue() - screen_height / 2 + e.getHeight().floatValue() / 2;
    }

    public void setup(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp(double dist) {
        y -= dist;
    }

    public void moveDown(double dist) {
        y += dist;
    }

    public void moveLeft(double dist) {
        x -= dist;
    }

    public void moveRight(double dist) {
        x += dist;
    }

    public BaseCamera(float x_origin, float y_origin) {
        this.x_origin = x_origin;
        this.y_origin = y_origin;
    }

    public void move(float xamt, float yamt) {
        x_origin += xamt;
        y_origin += yamt;
    }

    public float getXOrigin() {
        return x_origin;
    }

    public void setXOrigin(float x_offset) {
        this.x_origin = x_offset;
    }

    public float getYOrigin() {
        return y_origin;
    }

    public void setYOrigin(float y_offset) {
        this.y_origin = y_offset;
    }


}
