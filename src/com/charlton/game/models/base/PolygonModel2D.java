package com.charlton.game.models.base;

import com.charlton.game.contracts.*;

import java.awt.*;
import java.util.Arrays;

public class PolygonModel2D extends MovableObject implements Drawable, CollisionDetection {

    protected BoundingBox boundingBox = new BoundingBox(0, 0, 0, 0);
    private int[][] structure = {};

    private PolygonModel2D() { }

    @Override
    public Movable setAcceleration(double accelerate_x, double accelerate_y) {
        boundingBox.setAcceleration(accelerate_x, accelerate_y);
        return super.setAcceleration(accelerate_x, accelerate_y);
    }

    @Override
    public Movable setVelocity(double velocity_x, double velocity_y) {
        boundingBox.setVelocity(velocity_x, velocity_y);
        return super.setVelocity(velocity_x, velocity_y);
    }

    @Override
    public void jump(double velocity) {
        boundingBox.jump(velocity);
        super.jump(velocity);
    }


    @Override
    public void gravitate() {
        boundingBox.gravitate();
        super.gravitate();
        updateBounds();
    }

    public PolygonModel2D(int[][] structure, int world_x, int world_y, int world_angle) {
        this.structure = structure;
        this.position_x = world_x;
        this.position_y = world_y;
        this.world_angle = world_angle;
        this.cos_angle = cos[world_angle];
        this.sin_angle = sin[world_angle];
        updateBounds();
        //boundingBox.bind(this);
    }

    @Override
    public void moveBy(double dx, double dy) {
        super.moveBy(dx, dy);
        boundingBox.moveBy(dx, dy);
    }

    @Override
    public void render(Graphics g) {
        if (structure.length > 0) {
            int[] x_points = new int[structure[0].length];
            int[] y_points = new int[structure[0].length];
            int _x, _y;
            for (int polygon = 0; polygon < structure.length / 2; polygon++) {
                for (int vertex = 0; vertex < structure[polygon].length; vertex++) {
                    _x = structure[polygon * 2][vertex];
                    _y = structure[polygon * 2 + 1][vertex];
                    x_points[vertex] = (int) ((_x * cos_angle - _y * sin_angle) + position_x);
                    y_points[vertex] = (int) ((_y * cos_angle + _x * sin_angle) + position_y);
                }
                g.drawPolygon(x_points, y_points, structure[polygon].length);
            }

        }
        if (TESTING) {
            boundingBox.render(g);
        }
    }

    @Override
    public boolean overlaps(MovableCollision box) {
        boolean collision = boundingBox.overlaps(box);
        if(collision) {
            //TODO: box.align();
        }
        return collision;
    }

    @Override
    public boolean overlaps(BoundingContractLine line) {
        boolean overlaps = boundingBox.overlaps(line);

        return overlaps;
    }

    @Override
    public void pushes(MovableCollision contract) {
        boundingBox.pushes(contract);
    }

    @Override
    public void pushedBackBy(BoundingContractLine line) {
        boundingBox.pushedBackBy(line);
    }

    @Override
    public void bind(MovableCollision object) {

    }


    @Override
    public MovableCollision getBoundingObject() {
        return boundingBox;
    }

    @Override
    public void setWorld(double x, double y) {
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
            boundingBox.position_x = position_x - highest;
            boundingBox.position_y = position_y - highest;
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
    public int getType() {
        return TYPE_POLY;
    }
}
