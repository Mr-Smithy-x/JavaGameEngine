package com.charlton.game.models.base;

import com.charlton.game.contracts.*;
import com.charlton.game.models.base.model2d.AIObject2D;
import com.charlton.game.models.base.model2d.contracts.CollisionDetection2D;

import java.awt.*;
import java.util.Arrays;

public class PolygonModel2D extends AIObject2D implements Drawable, CollisionDetection2D {

    protected BoundingBox boundingBox = new BoundingBox(0, 0, 0, 0);
    private int[][] structure = {};


    public PolygonModel2D(int[][] structure, int world_x, int world_y, int world_angle) {
        this.structure = structure;
        this.position_x = world_x;
        this.position_y = world_y;
        this.world_angle = world_angle;
        updateBounds();
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
                    x_points[vertex] = (int) ((_x * getCosAngle() - _y * getSinAngle()) + position_x);
                    y_points[vertex] = (int) ((_y * getCosAngle() + _x * getSinAngle()) + position_y);
                }
                g.drawPolygon(x_points, y_points, structure[polygon].length);
            }

        }
        if (TESTING) {
            boundingBox.render(g);
        }
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
}
