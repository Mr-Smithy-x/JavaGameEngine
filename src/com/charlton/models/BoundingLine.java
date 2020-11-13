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

    int held_at = 0;

    public BoundingLine(double x1, double y1, double x2, double y2) {
        this.point_x = x1;
        this.point_y = y1;
        this.point_x2 = x2;
        this.point_y2 = y2;
        this.computeNormal();
    }

    public void computeNormal() {
        double vx = this.point_x2 - this.point_x; //<vx, vy> ie. vector v
        double vy = this.point_y2 - this.point_y;
        double mag_v = Math.sqrt(vx * vx + vy * vy); //magnitude (length?) |v|
        double ux = vx / mag_v; // unit vector <ux, uy> ie. u
        double uy = vy / mag_v; // direction to
        this.normal_x = -uy;
        this.normal_y = ux;
        c = this.point_x * normal_x + this.point_y * normal_y;
    }


    @Override
    public Number distanceTo(Number x, Number y) {
        //double vx = point_x - x.doubleValue(); // |v| <vx, vy>
        //double vy = point_y - y.doubleValue();
        double vx = x.doubleValue() - point_x;
        double vy = y.doubleValue() - point_y;
        //return -d;
        //x.doubleValue() * normal_x + y.intValue() * normal_y - c;
        double distance = normal_x * vx + normal_y * vy;
        return distance;
    }


    @Override
    public void draw(Graphics g) {
        g.drawLine((int) point_x, (int) point_y, (int) point_x2, (int) point_y2);
    }

    @Override
    public void drawRelativeToCamera(Graphics g) {

    }

    @Override
    public Number getX() {
        return point_x;
    }

    @Override
    public Number getY() {
        return point_y;
    }


    public void draggedBy(Number dx, Number dy) {

        if (held_at == 1) {
            this.point_x += dx.doubleValue();
            this.point_y += dy.doubleValue();
        }
        if (held_at == 2) {
            this.point_x2 += dx.doubleValue();
            this.point_y2 += dy.doubleValue();
        }
        this.computeNormal();
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
    public int getType() {
        return 0;
    }


    @Override
    public Number getNormalX() {
        return normal_x;
    }

    @Override
    public Number getNormalY() {
        return normal_y;
    }


    public boolean isHeld() {
        return held_at != 0;
    }

    public void grabbed(int mx, int my) {

        double dx;
        double dy;
        dx = point_x - mx;
        dy = point_y - my;
        if (dx * dx + dy + dy < 49) held_at = 1;

        dx = point_x2 - mx;
        dy = point_y2 - my;
        if (dx * dx + dy + dy < 49) held_at = 2;
    }

    public void released() {
        held_at = 0;
    }

}
