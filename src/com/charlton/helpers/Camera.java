package com.charlton.helpers;

import com.charlton.contracts.Movable;
import com.charlton.models.SpriteSheet;

public class Camera {
    public static double x;
    public static double y;

    public static int vx;
    public static int vy;

    public static int ay;

    public static int x_origin = 900;
    public static int y_origin = 440;

    public static final int GRAVITY = 1;
    public final static int scaling_factor = 4;

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static void set(double x, double y) {
        Camera.x = x;
        Camera.y = y;
    }

    public static void moveBy(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public static void moveUp(double dist) {
        y -= dist;
    }

    public static void moveDown(double dist) {
        y += dist;
    }

    public static void moveLeft(double dist) {
        x -= dist;
    }

    public static void moveRight(double dist) {
        x += dist;
    }


    public static void update() {

    }

    public static void setOrigin(Movable link) {
        x_origin = link.getX().intValue();
        y_origin = link.getY().intValue();
        x = x_origin;
        y = y_origin;
    }
}