package com.charlton.models;

import com.charlton.contracts.BoundingContractLine;
import com.charlton.contracts.Drawable;

import java.awt.*;

public class BoundingLine implements Drawable, BoundingContractLine {

    double x1;
    double y1;
    double x2;
    double y2;
    double Nx;
    double Ny;

    public BoundingLine(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        double vx = x2 - x1; //<vx, vy> ie. vector v
        double vy = y2 - y1;
        double mag = Math.sqrt(vx * vx + vy * vy); //magnitude (length?) |v|
        double ux = vx / mag; // unit vector <ux, uy> ie. u
        double uy = vy / mag; // direction to
        Nx = uy;
        Ny = -ux;
    }



    @Override
    public void draw(Graphics g) {
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    @Override
    public Number getX() {
        return x1;
    }

    @Override
    public Number getY() {
        return y1;
    }

    @Override
    public void setX(Number x) {

    }

    @Override
    public void setY(Number y) {

    }

    @Override
    public Number getWidth() {
        return x2 - x1;
    }

    @Override
    public Number getHeight() {
        return y2 - y1;
    }

    @Override
    public Number getRadius() {
        return 1;
    }

    @Override
    public void align() {

    }

    @Override
    public Number distanceTo(Number x, Number y) {
        double vx = x1 - x.doubleValue(); // |v| <vx, vy>
        double vy = y1 - y.doubleValue();
        double d = Nx * vx + Ny * vy;
        return d;
    }

    @Override
    public Number getNx() {
        return Nx;
    }

    @Override
    public Number getNy() {
        return Ny;
    }
}
