package com.charlton.containters;

import com.charlton.base.GameHolder;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.ImageLayer;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CameraTestContainer extends GameHolder {

    ImageLayer mountains = ImageLayer.from("mountains.gif",0,0, 0);

    protected CameraTestContainer(JFrame container, Canvas canvas) throws IOException {
        super(container, canvas);
    }

    public CameraTestContainer(Applet container) throws IOException {
        super(container);
    }

    @Override
    public void paint(Graphics g) {
        mountains.draw(g);
    }

    public void init() {
        GlobalCamera.getInstance().setup(0, 0);
    }

    @Override
    public void inGameLoop() {
        super.inGameLoop();
        if(pressing[LT]){
            GlobalCamera.getInstance().moveLeft(4);
        }
        if(pressing[RT]){
            GlobalCamera.getInstance().moveRight(4);
        }
    }



    public static GameHolder frame(int width, int height) throws IOException {
        JFrame frame = new JFrame("Zelda Game");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Canvas canvas = new Canvas();
        canvas.setFocusable(true);
        canvas.setFocusTraversalKeysEnabled(true);
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack();
        return new CameraTestContainer(frame, canvas);
    }

    public static GameHolder applet(Applet applet) throws IOException {
        return new CameraTestContainer(applet);
    }
}
