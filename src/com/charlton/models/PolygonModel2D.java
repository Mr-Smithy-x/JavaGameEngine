package com.charlton.models;

import com.charlton.contracts.BoundingContract;
import com.charlton.contracts.BoundingContractLine;
import com.charlton.contracts.CollisionDetection;
import com.charlton.contracts.Drawable;

import java.awt.*;
import java.util.Arrays;

public class PolygonModel2D extends MovableObject implements Drawable, CollisionDetection {

    protected BoundingBox boundingBox = new BoundingBox(0, 0, 0, 0);
    private int[][] structure = {};

    private PolygonModel2D() {
    }

    public PolygonModel2D(int[][] structure, int world_x, int world_y, int world_angle) {
        this.structure = structure;
        this.world_x = world_x;
        this.world_y = world_y;
        this.world_angle = world_angle;
        this.cos_angle = cos[world_angle];
        this.sin_angle = sin[world_angle];
        updateBounds();
        boundingBox.bind(this);
    }

    @Override
    public void moveBy(double dx, double dy) {
        super.moveBy(dx, dy);
        boundingBox.moveBy(dx, dy);
    }

    @Override
    public void draw(Graphics g) {
        if (structure.length > 0) {
            int[] x_points = new int[structure[0].length];
            int[] y_points = new int[structure[0].length];
            int _x, _y;
            for (int polygon = 0; polygon < structure.length / 2; polygon++) {
                for (int vertex = 0; vertex < structure[polygon].length; vertex++) {
                    _x = structure[polygon * 2][vertex];
                    _y = structure[polygon * 2 + 1][vertex];
                    x_points[vertex] = (int) ((_x * cos_angle - _y * sin_angle) + world_x);
                    y_points[vertex] = (int) ((_y * cos_angle + _x * sin_angle) + world_y);
                }
                g.drawPolygon(x_points, y_points, structure[polygon].length);
            }

        }
        if (TESTING) {
            boundingBox.draw(g);
        }
    }

    @Override
    public boolean overlaps(BoundingContract<Number> box) {
        boolean collision = boundingBox.overlaps(box);
        if(collision) {
            box.align();
        }
        return collision;
    }

    @Override
    public boolean overlaps(BoundingContractLine line) {
        boolean overlaps = boundingBox.overlaps(line);
        if(overlaps){
            align();
        }
        return overlaps;
    }

    @Override
    public void pushes(BoundingContract<Number> contract) {
        boundingBox.pushes(contract);
    }

    @Override
    public void pushedBackBy(BoundingContractLine line) {
        boundingBox.pushedBackBy(line);
    }

    @Override
    public void bind(BoundingContract<Number> object) {

    }

    @Override
    public BoundingBox getBoundingObject() {
        return boundingBox;
    }

    @Override
    public void setWorld(int x, int y) {
        super.setWorld(x, y);
        updateBounds();
    }

    protected void updateBounds() {
        if (structure.length > 0) {
            long ms = System.currentTimeMillis();
            int lowest = Arrays.stream(structure).mapToInt(value -> {
                int lowest1 = value[0];
                for (int j : value) {
                    if (j < lowest1) {
                        lowest1 = j;
                    }
                }
                return lowest1;
            }).min().getAsInt();
            int highest = Arrays.stream(structure).mapToInt(value -> {
                int highest1 = value[0];
                for (int j : value) {
                    if (j > highest1) {
                        highest1 = j;
                    }
                }
                return highest1;
            }).max().getAsInt();
            boundingBox.world_x = world_x - highest;
            boundingBox.world_y = world_y - highest;
            boundingBox.width = Math.abs(lowest) + highest;
            boundingBox.height = Math.abs(lowest) + highest;
            long done = System.currentTimeMillis();
            System.out.printf("end: %s - start: %s = difference: %s\n", done, ms, done - ms);
        }
    }

    @Override
    public void setX(Number x) {
        super.setX(x);
        boundingBox.setX(x.doubleValue() - getWidth().doubleValue() / 2);
    }

    @Override
    public void setY(Number y) {
        super.setY(y);
        boundingBox.setY(y.doubleValue() - getHeight().doubleValue() / 2);
    }

    @Override
    public Number getWidth() {
        return boundingBox.width;
    }

    @Override
    public Number getHeight() {
        return boundingBox.height;
    }

    @Override
    public Number getRadius() {
        return boundingBox.width / 2;
    }

    @Override
    public void align() {
        boundingBox.align();
    }
}
