package com.charlton.game.models.base.model3d;

import com.charlton.game.contracts.Drawable;
import com.charlton.game.display.GlobalCamera3D;

import java.awt.*;

public class Cube extends AIObject3D implements Drawable {

    public Cube() {
        this(0, 0, 0);
    }

    public Cube(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world_angle = 0;
        distance = 512;
    }

    double[][] x_struct =
            {
                    {-100,  100,  100, -100},
                    { 100, -100, -100,  100},
                    {-100, -100, -100, -100},
                    { 100,  100,  100,  100},
                    {-100,  100,  100, -100},
                    {-100,  100,  100, -100},
            };

    double[][] y_struct =
            {
                    { 100,  100, -100, -100},
                    { 100,  100, -100, -100},
                    { 100,  100, -100, -100},
                    { 100,  100, -100, -100},
                    { 100,  100,  100,  100},
                    {-100, -100, -100, -100},
            };

    double[][] z_struct =
            {
                    { 100,  100,  100,  100},
                    {-100, -100, -100, -100},
                    {-100,  100,  100, -100},
                    { 100, -100, -100,  100},
                    {-100, -100,  100,  100},
                    { 100,  100, -100, -100},
            };

    double[] xw = new double[x_struct.length];
    double[] yw = new double[x_struct.length];
    double[] zw = new double[x_struct.length];


    double Lx = 0;
    double Ly = 1;
    double Lz = 0;

    Color[] grey = shadesOfGrey();

    public Color[] shadesOfGrey(){
        Color[] grey = new Color[256];
        for(int i = 0; i < grey.length; i++){
            grey[i] = new Color((i * 2) % 256,(i * 3) % 256, (i * 4) % 256);
        }
        return grey;
    }

    @Override
    public void render(Graphics g) {
        for (int polygon = 0; polygon < x_struct.length; polygon++) {
            for (int vertex = 0; vertex < x_struct[polygon].length; vertex++) {
                //Translate Cube out into the world
                xw[vertex] = x_struct[polygon][vertex] * getCosAngle() + y_struct[polygon][vertex] * getSinAngle() + x;
                yw[vertex] = y_struct[polygon][vertex] * getCosAngle() - x_struct[polygon][vertex] * getSinAngle() + y;
                zw[vertex] = z_struct[polygon][vertex] + z;
            }

            double Ux = xw[1] - xw[0];
            double Uy = yw[1] - yw[0];
            double Uz = zw[1] - zw[0];

            double Vx = xw[0] - xw[2];
            double Vy = yw[0] - yw[2];
            double Vz = zw[0] - zw[2];

            double Nx = Uy * Vz - Uz * Vy;
            double Ny = Uz * Vx - Ux * Vz;
            double Nz = Ux * Vy - Uy * Vx;

            double Wx = -xw[0];
            double Wy = -yw[0];
            double Wz = -zw[0];

            double mag = Math.sqrt(Nx * Nx + Ny * Ny + Nz * Nz);

            Nx /= mag;
            Ny /= mag;
            Nz /= mag;

            int[] xs = new int[x_struct[polygon].length];
            int[] ys = new int[x_struct[polygon].length];
            for (int vertex = 0; vertex < x_struct[polygon].length; vertex++) {
                //3d perspective Transformation + shift to the center of the screen
                int x = (int) (distance * xw[vertex] / zw[vertex]);
                int y = (int) (distance * yw[vertex] / zw[vertex]);
                xs[vertex] = x + GlobalCamera3D.getInstance().getX();
                ys[vertex] = y + GlobalCamera3D.getInstance().getY();
            }
            if (Nx * Wx + Ny * Wy + Nz * Wz > 0) {

                double q = 255 * (Nx * Lx + Ny * Ly + Nz * Lz + 1) / 2;

                g.setColor(grey[(int)q]);
                g.fillPolygon(xs, ys, xs.length);
                g.setColor(Color.black);
                g.drawPolygon(xs, ys, xs.length);
            }
        }
        //System.exit(0);
    }
}
