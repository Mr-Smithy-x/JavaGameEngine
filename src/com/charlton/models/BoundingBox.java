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
        object.setX(this.position_x + width / 2);
        object.setY(this.position_y + height / 2);
    }

    @Override
    public int getType() {
        return TYPE_POLY;
    }

    public BoundingBox(int x, int y, int w, int h) {
        this.position_x = x;
        this.position_y = y;
        this.width = w;
        this.height = h;
    }

    public boolean contains(int mx, int my) {
        return (mx > position_x) && (mx < position_x + width) &&
                (my > position_y) && (my < position_y + height);
    }

    @Override
    public void draw(Graphics g) {

        g.drawRect((int) position_x, (int) position_y, (int) width, (int) height);
    }

    @Override
    public void pushes(BoundingContract<Number> contract) {
        double dx = position_x - contract.getX().doubleValue();
        double dy = position_y - contract.getY().doubleValue();
        double d = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / d;
        double uy = dy / d;
        double ri = getRadius().doubleValue() + contract.getRadius().doubleValue();
        double p = ri - d;
        position_x += ux * p / 2;
        position_y += uy * p / 2;
        contract.setX(contract.getX().doubleValue() - (ux * p / 2));
        contract.setY(contract.getY().doubleValue() - (uy * p / 2));

    }

    @Override
    public void pushedBackBy(BoundingContractLine line) {
        double d = line.distanceTo(position_x, position_y).doubleValue();
        double p = getRadius().doubleValue() - d;
        this.position_x += p * line.getNormalX().doubleValue();
        this.position_y += p * line.getNormalY().doubleValue();
    }

    @Override
    public boolean overlaps(BoundingContract<Number> box) {
        boolean collides = false;
        collides = (box.getX().doubleValue() + box.getWidth().doubleValue() >= position_x) &&
                (position_x + width >= box.getX().doubleValue()) &&
                (box.getY().doubleValue() + box.getHeight().doubleValue() >= position_y) &&
                (position_y + height >= box.getY().doubleValue());

        if (collides) {
            pushes(box);
        }
        return collides;
    }

    @Override
    public boolean overlaps(BoundingContractLine line) {
        double d = line.distanceTo(position_x, position_y).doubleValue();
        boolean overlaps = d < width;
        if (overlaps) {
            pushedBackBy(line);
            bounce();
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
        return (width + height) / 2;
    }
}
