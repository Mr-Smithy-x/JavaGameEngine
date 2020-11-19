package com.charlton.containters;

import com.charlton.base.GameHolder;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.ImageLayer;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.base.BoundingLine;
import com.charlton.game.models.sprites.Link;
import com.charlton.game.models.tilemap.Point;
import com.charlton.game.models.tilemap.Tile;
import com.charlton.game.models.tilemap.TileMap;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PlatformerContainer extends GameHolder {

    ImageLayer mountains = ImageLayer.from("mountains.gif", 0, 0, 0);

    TileMap map = null;
    Link link = new Link(getWidth()/4, getHeight() - 50, 1);
    BoundingLine line;

    @Override
    protected void onInitialize() {

        GlobalCamera.getInstance().setScaling(6);
        try {
            map = TileMap.from("platformer.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        line = new BoundingLine(getWidth(), getHeight() - 50, 0, getHeight() - 50);
        link.setAcceleration(0, 1);
                link.setVelocity(0, 1);
                link.setDrag(0, 0.95);;
        link.setPose(SpriteSheet.Pose.RIGHT);
        GlobalCamera.getInstance().setOrigin(link, getWidth(), getHeight());
    }

    @Override
    protected void paint(Graphics g) {
        mountains.draw(g);
        map.render(g);
        link.render(g);
        line.render(g);
    }

    @Override
    protected void onPlay() {

        if(pressing[UP]){
            if (map.canMove(link, SpriteSheet.Pose.UP)) {
                link.jump(25);
            }
        }
        if (pressing[LT]) {
            link.moveLeft(link.getSpeed() * 2);
            GlobalCamera.getInstance().moveLeft(link.getSpeed() * 2);
        } else if (pressing[RT]) {
            link.moveRight(link.getSpeed() * 2);
            GlobalCamera.getInstance().moveRight(link.getSpeed() * 2);
        }

        System.out.printf("Velocity: %s, Acceleration: %s\n", link.getVelocityY(), link.getAccelerationY());

        if(map.canMove(link, SpriteSheet.Pose.DOWN)){
            link.gravitate();
        }
        /*
        if(!link.overlaps(line, true) && Math.abs(link.getVelocityY().doubleValue()) > 0.07){
            link.gravitate();
        }else{
            link.pushedBackBy(line);
            link.bounce();
        }*/
        GlobalCamera.getInstance().setOrigin(link, getWidth(), getHeight());
    }


    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }

    protected PlatformerContainer(JFrame container, Canvas canvas) throws IOException {
        super(container, canvas);
    }

    public PlatformerContainer(Applet container) throws IOException {
        super(container);
    }

    public static GameHolder holder(int width, int height) throws IOException {
        JFrame frame = frame(width, height);
        Canvas canvas = canvas(width, height);
        frame.add(canvas);
        frame.pack();
        return new PlatformerContainer(frame, canvas);
    }

    public static GameHolder applet(Applet applet) throws IOException {
        return new PlatformerContainer(applet);
    }


}
