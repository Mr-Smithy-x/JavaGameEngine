package com.charlton.game.display;

import com.charlton.game.models.base.model2d.contracts.Boundable2D;
import com.charlton.game.contracts.Drawable;

import java.awt.*;

public abstract class BaseCamera implements Drawable {

    protected int x, y;
    protected int xOrigin, yOrigin;
    protected int scaling = 4;


    public int getScaling() {
        return scaling;
    }


    public void setScaling(int scaling) {
        this.scaling = scaling;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveBy(double dx, double dy) {
        x += dx;
        y += dy;
    }


    public void setOrigin(Boundable2D e, int screenWidth, int screenHeight) {
        int x = e.getX().intValue();
        int y = e.getY().intValue();
        //Commented out code would center the pixel onto the screen, but with this dynamic, it works much differently
        this.xOrigin = x - (screenWidth / 2);
        this.yOrigin = y - (screenHeight / 2);

        this.x = xOrigin;
        this.y = yOrigin;
        // System.out.printf("(X: %s,Y: %s), ORIGIN: (x: %s, y: %s)", x, y, x_origin, y_origin);
    }

    public void setup(int x, int y) {
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

    public BaseCamera(int xOrigin, int yOrigin) {
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
    }

    public void move(int dx, int dy) {
        xOrigin += dx;
        yOrigin += dy;
    }

    public int getXOrigin() {
        return xOrigin;
    }

    public void setXOrigin(int xOffset) {
        this.xOrigin = xOffset;
    }

    public int getYOrigin() {
        return yOrigin;
    }

    public void setYOrigin(int yOffset) {
        this.yOrigin = yOffset;
    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(getXOrigin() - 10 * 3, 0, getXOrigin() + 10 * 3, 0);
        g.drawLine(0, getYOrigin() - 10 * 3, 0, getYOrigin() + 10 * 3);
        g.drawRect(getXOrigin(), getYOrigin(), 50, 50);
        System.out.printf("Origin: (%s, %s)\n",getXOrigin() , getYOrigin());
    }
}
