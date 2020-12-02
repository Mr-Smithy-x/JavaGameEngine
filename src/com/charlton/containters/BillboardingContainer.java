package com.charlton.containters;

import com.charlton.base.GameHolder;
import com.charlton.game.display.GlobalCamera3D;
import com.charlton.game.models.base.model3d.Cube;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BillboardingContainer extends GameHolder {

    Billboard billboard;
    Cube cube;

    protected BillboardingContainer(JFrame container, Canvas canvas) {
        super(container, canvas);
    }

    @Override
    protected void onInitialize() {
        try {
            billboard = new Billboard("link.png", getWidth()/2, getHeight()/2, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cube = new Cube(getWidth()/2, getHeight()/2, 200);
        GlobalCamera3D.getInstance().setOrigin(cube, getWidth(), getHeight());

    }

    @Override
    protected void onPaint(Graphics g) {
        billboard.render(g);
        cube.render(g);
    }

    @Override
    protected void onPlay() {


        if(pressing[_U]) GlobalCamera3D.getInstance().moveBy(0, -10,0);
        if(pressing[_J]) GlobalCamera3D.getInstance().moveBy(0, 10,0);
        if(pressing[_H]) GlobalCamera3D.getInstance().moveBy(-10, 0,0);
        if(pressing[_K]) GlobalCamera3D.getInstance().moveBy(10, 0,0);
        if(pressing[_N]) GlobalCamera3D.getInstance().moveBy(0, 0, -10);
        if(pressing[_M]) GlobalCamera3D.getInstance().moveBy(0, 0,10);

        cube.turnRight(1);

        if (pressing[LT]) {
            cube.moveBy(-10,0,0);
        }
        if (pressing[RT]) {

            cube.moveBy(10,0,0);
        }
        if (pressing[UP]) {
            cube.moveBy(0,-10,0);
        }
        if (pressing[DN]) {

            cube.moveBy(0,10,0);
        }
        if (pressing[_A]) {
            cube.moveBy(0,0,-10);
        }
        if (pressing[_S]) {

            cube.moveBy(0,0, 10);
        }
    }


    public static GameHolder holder(int width, int height) {
        JFrame frame = frame(width, height);
        Canvas canvas = canvas(width, height);
        frame.add(canvas);
        frame.pack();
        return new BillboardingContainer(frame, canvas);
    }

}
