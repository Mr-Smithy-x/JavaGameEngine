package com.charlton.applets;

import com.charlton.applets.base.GameApplet;
import com.charlton.game.gfx.ImageLayer;
import com.charlton.game.display.GlobalCamera;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CameraTest  extends GameApplet {

    ImageLayer mountains = ImageLayer.from("mountains.gif",0,0, 0);


    public CameraTest() throws IOException { }

    @Override
    public void paint(Graphics g) {
        mountains.draw(g);
    }

    public void init() {
        GlobalCamera.getInstance().setup(0, 0);
        super.init();
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

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);

    }
}
