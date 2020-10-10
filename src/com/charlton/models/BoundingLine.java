package com.charlton.models;

import com.charlton.contracts.BoundingContractLine;
import com.charlton.contracts.Drawable;

import java.awt.*;

public class BoundingLine implements Drawable, BoundingContractLine {

    double point_x;
    double point_y;
    double point_x2;
    double point_y2;
    double normal_x;
    double normal_y;
    double c;

    public BoundingLine(double x1, double y1, double x2, double y2) {
        this.point_x = x1;
        this.point_y = y1;
        this.point_x2 = x2;
        this.point_y2 = y2;
        double vx = x2 - x1; //<vx, vy> ie. vector v
        double vy = y2 - y1;
        double mag = Math.sqrt(vx * vx + vy * vy); //magnitude (length?) |v|
        double ux = vx / mag; // unit vector <ux, uy> ie. u
        double uy = vy / mag; // direction to
        normal_x = -uy;
        normal_y = ux;
        c = x1 * normal_x + y1 * normal_y;
    }


    @Override
    public Number distanceTo(Number x, Number y) {
        double vx = point_x - x.doubleValue(); // |v| <vx, vy>
        double vy = point_y - y.doubleValue();
        double d = normal_x * vx + normal_y * vy;
        //return -d;
       return x.doubleValue() * normal_x + y.intValue() * normal_y - c;
    }


    @Override
    public void draw(Graphics g) {
        g.drawLine((int) point_x, (int) point_y, (int) point_x2, (int) point_y2);
    }

    @Override
    public Number getX() {
        return point_x;
    }

    @Override
    public Number getY() {
        return point_y;
    }

    @Override
    public void setX(Number x) {

    }

    @Override
    public void setY(Number y) {

    }

    @Override
    public Number getWidth() {
        return point_x2 - point_x;
    }

    @Override
    public Number getHeight() {
        return point_y2 - point_y;
    }

    @Override
    public Number getRadius() {
        return 1;
    }

    @Override
    public void align() {

    }


    @Override
    public Number getNormal_x() {
        return normal_x;
    }

    @Override
    public Number getNormal_y() {
        return normal_y;
    }
}
