package com.charlton.game.models.base.model3d;

import com.charlton.game.contracts.Drawable;
import com.charlton.game.display.GlobalCamera3D;

import java.awt.*;

public class Cube extends AIObject3D implements Drawable {

    public Cube() {
        this(0,0,0);
    }

    public Cube(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world_angle = 0;
        distance = 512;
    }


    static final int[][] x_struct = new int[][]{
            {-100, 100, 100, -100}, //body_x
            {-100, 100, 100, -100}, //body_x
            {-100, -100, -100, -100}, //body_x
            {100, 100, 100, 100}, //body_x
            {-100, 100, 100, -100}, //body_x
            {-100, 100, 100, -100}, //body_x
    };

    static final int[][] y_struct = new int[][]{
            {100, 100, -100, -100}, //body_x
            {100, 100, -100, -100}, //body_x
            {100, 100, -100, -100}, //body_x
            {100, 100, -100, -100}, //body_x
            {100, 100, 100, 100}, //body_x
            {-100, -100, -100, -100}, //body_x

    };
    static final int[][] z_struct = new int[][]{
            {100, 100, 100, 100}, //body_x
            {-100, -100, -100, -100}, //body_x
            {-100, 100, 100, -100}, //body_x
            {-100, 100, 100, -100}, //body_x
            {-100, -100, 100, 100}, //body_x
            {-100, -100, 100, 100}, //body_x

    };

    @Override
    public void render(Graphics g) {
        int[] xs = new int[4];
        int[] ys = new int[4];
        if (x_struct.length > 0) {
            double _x, _y, _z;
            for (int polygon = 0; polygon < x_struct.length; polygon++) {
                for (int vertex = 0; vertex < x_struct[polygon].length; vertex++) {
                    //Translate Cube out into the world
                    //_x = x_struct[polygon][vertex] + x;
                    //_y = y_struct[polygon][vertex] + y;
                    //_z = z_struct[polygon][vertex] + z;

                    //_x = x_struct[polygon][vertex] * getCosAngle() - y_struct[polygon][vertex] * getSinAngle() + x;
                    //_y = y_struct[polygon][vertex] * getCosAngle() + x_struct[polygon][vertex] * getSinAngle() + y;
                    //_z = z_struct[polygon][vertex] + z;


                    _x = x_struct[polygon][vertex] * getCosAngle() - z_struct[polygon][vertex] * getSinAngle() + x;
                    _y = y_struct[polygon][vertex] + y;
                    _z = z_struct[polygon][vertex] * getCosAngle() + x_struct[polygon][vertex] * getSinAngle() + z;
                    //3d perspective Transformation + shift to the center of the screen
                    xs[vertex] = (int) (distance * _x / _z) + GlobalCamera3D.getInstance().getX();
                    ys[vertex] = (int) (distance * _y / _z) + GlobalCamera3D.getInstance().getY();
                }
                g.drawPolygon(xs, ys, xs.length);

            }

        }
    }

}
