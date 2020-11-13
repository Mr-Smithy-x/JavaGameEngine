package com.charlton.game.helpers;

public class GlobalCamera2D {

    public static int x;
    public static int y;

    public static void setup(int x, int y) {
        GlobalCamera2D.x = x;
        GlobalCamera2D.y = y;
    }

    public static void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
