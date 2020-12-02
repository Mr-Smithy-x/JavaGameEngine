package com.charlton.containters;

import com.charlton.base.GameHolder;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.ImageLayer;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
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
    protected void onPaint(Graphics g) {
        mountains.draw(g);
    }

    @Override
    protected void onInitialize() {
        GlobalCamera.getInstance().setup(0, 0);
    }

    @Override
    protected void onPlay() {
        if(pressing[LT]){
            GlobalCamera.getInstance().moveLeft(4);
        }
        if(pressing[RT]){
            GlobalCamera.getInstance().moveRight(4);
        }
    }



    public static GameHolder holder(int width, int height) throws IOException {
        JFrame frame = frame(width, height);
        Canvas canvas = canvas(width, height);
        frame.add(canvas);
        frame.pack();
        return new CameraTestContainer(frame, canvas);
    }

    public static GameHolder applet(Applet applet) throws IOException {
        return new CameraTestContainer(applet);
    }
}
