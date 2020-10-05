package com.charlton.models;

import com.charlton.contracts.BoundingContract;
import com.charlton.contracts.BoundingContractLine;
import com.charlton.contracts.CollisionDetection;
import com.charlton.contracts.Drawable;

import java.awt.*;

public class BoundingCircle extends MovableObject implements Drawable, CollisionDetection {

    double radius;
    private BoundingContract<Number> object;

    public BoundingCircle(int x, int y, double r, int world_angle) {
        this.world_x = x;
        this.world_y = y;
        this.world_angle = world_angle;
        this.radius = r;
    }

    @Override
    public void draw(Graphics g) {
        g.drawOval((int) (world_x - radius), (int) (world_y - radius), (int) (2.0 * radius), (int) (2.0 * radius));
        g.drawLine((int) world_x, (int) world_y, (int) (world_x + radius * cos_angle), (int) (world_y + radius * sin_angle));
    }


    @Override
    public boolean overlaps(BoundingContract<Number> c) {
        double dx = world_x - c.getX().doubleValue();
        double dy = world_y - c.getY().doubleValue();
        double d2 = dx * dx + dy * dy;
        double ri = radius + c.getRadius().doubleValue();
        boolean collides = d2 <= ri * ri;
        if(collides){
            pushes(c);
        }
        return collides;
    }


    @Override
    public boolean overlaps(BoundingContractLine line) {
        double d = line.distanceTo(world_x, world_y).doubleValue();
        boolean overlaps = d * d < radius * radius;
        if(overlaps){
            pushedBackBy(line);
        }
        return overlaps;
    }

    @Override
    public BoundingContract<Number> getBoundingObject() {
        return this;
    }

    @Override
    public void pushedBackBy(BoundingContractLine line){
        double d = line.distanceTo(world_x, world_y).doubleValue();
        double p = radius - d;
        this.setX(world_x + p * line.getNx().doubleValue());
        this.setY(world_y += p * line.getNy().doubleValue());
    }

    @Override
    public void bind(BoundingContract<Number> object) {
        this.object = object;
    }

    @Override
    public void pushes(BoundingContract<Number> contract) {
        double dx = world_x - contract.getX().doubleValue();
        double dy = world_y - contract.getY().doubleValue();
        double d = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / d;
        double uy = dy / d;
        double ri = radius + contract.getRadius().doubleValue();
        double p = ri - d;
        world_x += ux * p / 2;
        world_y += uy * p / 2;
        contract.setX(contract.getX().doubleValue() - (ux * p / 2));
        contract.setY(contract.getY().doubleValue() - (uy * p / 2));
    }

    @Override
    public Number getRadius() {
        return radius;
    }

    @Override
    public void align() {
        double x = this.world_x + radius / 2;
        double y = this.world_y + radius / 2;
        System.out.println(x);
        if(object != null) {
            object.setX(x);
            object.setY(y);
        }
    }
}
