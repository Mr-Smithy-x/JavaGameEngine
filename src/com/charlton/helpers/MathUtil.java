package com.charlton.helpers;

public class MathUtil {

    public static int rotate_x(int x, int y, int angle) {
        return (int) (x * cos(angle) - y * sin(angle));
    }

    public static int rotate_y(int x, int y, int angle) {
        return (int) (y * cos(angle) + x * sin(angle));
    }

    public static double cos(int a) {
        return Lookup.cos[a];
    }

    public static double sin(int a) {
        return Lookup.sin[a];
    }
    
}
