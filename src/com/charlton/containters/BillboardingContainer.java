package com.charlton.containters;

import com.charlton.base.GameHolder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BillboardingContainer extends GameHolder {

    Billboard billboard;

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
    }

    @Override
    protected void paint(Graphics g) {
        billboard.draw(g);
    }

    @Override
    protected void onPlay() {
        if (pressing[LT]) {
            billboard.x -= 10;
        }
        if (pressing[RT]) {
            billboard.x += 10;
        }
        if (pressing[UP]) {

            billboard.z += 10;
        }
        if (pressing[DN]) {

            billboard.z -= 10;
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
