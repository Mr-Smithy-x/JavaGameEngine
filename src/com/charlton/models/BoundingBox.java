package com.charlton.models;

import com.charlton.contracts.BoundingContract;
import com.charlton.contracts.BoundingContractLine;
import com.charlton.contracts.CollisionDetection;
import com.charlton.contracts.Drawable;

import java.awt.*;

public class BoundingBox extends MovableObject implements Drawable, CollisionDetection {

    double width;
    double height;
    private BoundingContract<Number> object;


    public void align() {
        object.setX(this.world_x + width / 2);
        object.setY(this.world_y + height / 2);
    }

    public BoundingBox(int x, int y, int w, int h) {
        this.world_x = x;
        this.world_y = y;
        this.width = w;
        this.height = h;
    }

    public boolean contains(int mx, int my) {
        return (mx > world_x) && (mx < world_x + width) &&
                (my > world_y) && (my < world_y + height);
    }


    @Override
    public void draw(Graphics g) {
        g.drawRect((int)world_x, (int)world_y, (int)width, (int)height);
    }


    @Override
    public void pushes(BoundingContract<Number> contract) {
        double dx = world_x - contract.getX().doubleValue();
        double dy = world_y - contract.getY().doubleValue();
        double d = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / d;
        double uy = dy / d;
        double ri = getRadius().doubleValue() + contract.getWidth().doubleValue();
        double p = ri - d;
        world_x += ux * p / 2;
        world_y += uy * p / 2;
        contract.setX(contract.getX().doubleValue() - (ux * p / 2));
        contract.setY(contract.getY().doubleValue() - (uy * p / 2));
    }

    @Override
    public void pushedBackBy(BoundingContractLine line) {
        double d = line.distanceTo(world_x, world_y).doubleValue();
        double p = getRadius().doubleValue() / 2 - d;
        this.setX(world_x + p * line.getNx().doubleValue());
        this.setY(world_y + p * line.getNy().doubleValue());
    }

    @Override
    public boolean overlaps(BoundingContract<Number> box) {
        boolean collides = (box.getX().doubleValue() + box.getWidth().doubleValue() >= world_x) &&
                (world_x + width >= box.getX().doubleValue()) &&
                (box.getY().doubleValue() + box.getHeight().doubleValue() >= world_y) &&
                (world_y + height >= box.getY().doubleValue());
        if (collides) {
            pushes(box);
        }
        return collides;
    }

    @Override
    public boolean overlaps(BoundingContractLine line) {
        double d = line.distanceTo(world_x, world_y).doubleValue();
        boolean overlaps = d * d < width * width;
        if (overlaps) {
            pushedBackBy(line);
        }
        return overlaps;
    }

    @Override
    public BoundingContract<Number> getBoundingObject() {
        return this;
    }

    public void bind(BoundingContract<Number> object) {
        this.object = object;
    }

    @Override
    public Number getWidth() {
        return width;
    }

    @Override
    public Number getHeight() {
        return height;
    }

    @Override
    public Number getRadius() {
        return width / 4;
    }
}
