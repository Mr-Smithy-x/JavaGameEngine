package com.charlton.game.display;

import com.charlton.game.contracts.Boundable;
import com.charlton.game.contracts.Drawable;
import com.charlton.game.contracts.Movable;
import com.charlton.game.models.SpriteSheet;

import java.awt.*;

public abstract class BaseCamera implements Drawable {

    protected int x, y;
    protected int x_origin, y_origin;
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


    public void setOrigin(Boundable e, int screen_width, int screen_height) {
        int x = e.getX().intValue();
        int y = e.getY().intValue();
        //Commented out code would center the pixel onto the screen, but with this dynamic, it works much differently
        this.x_origin = x - (screen_width / 2);
        this.y_origin = y - (screen_height / 2);

        this.x = x_origin;
        this.y = y_origin;
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

    public BaseCamera(int x_origin, int y_origin) {
        this.x_origin = x_origin;
        this.y_origin = y_origin;
    }

    public void move(int xamt, int yamt) {
        x_origin += xamt;
        y_origin += yamt;
    }

    public int getXOrigin() {
        return x_origin;
    }

    public void setXOrigin(int x_offset) {
        this.x_origin = x_offset;
    }

    public int getYOrigin() {
        return y_origin;
    }

    public void setYOrigin(int y_offset) {
        this.y_origin = y_offset;
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
