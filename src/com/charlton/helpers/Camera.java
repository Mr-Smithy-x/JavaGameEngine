package com.charlton.helpers;

public class Camera
{
    static double x;
    static double y;


    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static void set(double x, double y)
    {
        Camera.x = x;
        Camera.y = y;
    }

    public static void moveBy(double dx, double dy)
    {
        x += dx;
        y += dy;
    }

    public static void moveUp(double dist)
    {
        y -= dist;
    }

    public static void moveDown(double dist)
    {
        y += dist;
    }

    public static void moveLeft(double dist)
    {
        x -= dist;
    }

    public static void moveRight(double dist)
    {
        x += dist;
    }


}