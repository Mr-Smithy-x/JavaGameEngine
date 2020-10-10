package com.charlton.models;

import com.charlton.contracts.BoundingContract;
import com.charlton.contracts.BoundingContractLine;
import com.charlton.contracts.CollisionDetection;
import com.charlton.contracts.Drawable;

import java.awt.*;

public class BoundingCircle extends MovableObject implements Drawable, CollisionDetection {


    protected double radius;
    private BoundingContract<Number> object;


    public BoundingCircle(double x, double y, double r, int world_angle) {
        this.position_x = x;
        this.position_y = y;
        this.world_angle = world_angle;
        this.radius = r;
        this.bouncy = true;
        cos_angle = cos[world_angle];
        sin_angle = sin[world_angle];
    }

    @Override
    public void draw(Graphics g) {
        g.drawOval((int) (position_x - radius), (int) (position_y - radius), (int) (2.0 * radius), (int) (2.0 * radius));

        g.drawLine((int) position_x, (int) position_y, (int) (position_x + radius * cos_angle), (int) (position_y + radius * sin_angle));
    }


    @Override
    public boolean overlaps(BoundingContract<Number> c) {
        double dx = position_x - c.getX().doubleValue();
        double dy = position_y - c.getY().doubleValue();
        double d2 = dx * dx + dy * dy;
        double ri = radius + c.getRadius().doubleValue();
        boolean collides = d2 < ri;
        if (collides) {
            pushes(c);
        }
        return collides;
    }


    @Override
    public boolean overlaps(BoundingContractLine line) {
        double distance = line.distanceTo(position_x, position_y).doubleValue();
        boolean overlaps = -distance < radius;
        if (automate && overlaps) {
            pushedBackBy(line);
        }
        return overlaps;
    }

    @Override
    public BoundingContract<Number> getBoundingObject() {
        return this;
    }

    @Override
    public void pushedBackBy(BoundingContractLine line) {
        double distance = line.distanceTo(position_x, position_y).doubleValue();
        double p = (radius - radius - radius) - distance;
        position_x += p * line.getNormal_x().doubleValue();
        position_y += p * line.getNormal_y().doubleValue();
        if (bouncy) {
            this.bounce();
        }
    }


    @Override
    public void bind(BoundingContract<Number> object) {
        this.object = object;
    }

    @Override
    public void pushes(BoundingContract<Number> contract) {
        double dx = position_x - contract.getX().doubleValue();
        double dy = position_y - contract.getY().doubleValue();
        double d = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / d;
        double uy = dy / d;
        double ri = radius + contract.getRadius().doubleValue();
        double p = ri - d;
        position_x += ux * p / 2;
        position_y += uy * p / 2;
        contract.setX(contract.getX().doubleValue() - (ux * p / 2));
        contract.setY(contract.getY().doubleValue() - (uy * p / 2));
    }

    @Override
    public Number getRadius() {
        return radius;
    }

    @Override
    public void align() {
        double x = this.position_x + radius / 2;
        double y = this.position_y + radius / 2;
        System.out.println(x);
        if (object != null) {
            object.setX(x);
            object.setY(y);
        }
    }
}
