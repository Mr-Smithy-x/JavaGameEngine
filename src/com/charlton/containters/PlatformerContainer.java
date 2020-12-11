package com.charlton.containters;

import com.charlton.base.GameHolder;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.ImageLayer;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.base.BoundingLine;
import com.charlton.game.models.sprites.Link;
import com.charlton.game.models.sprites.Mario;
import com.charlton.game.models.tilemap.Tile;
import com.charlton.game.models.tilemap.TileMap;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class PlatformerContainer extends GameHolder {

    ImageLayer mountains;

    {
        int width = (1032 / 2) - 4;
        int height = (2174 / 5) - 4;
        int x = 2;
        int y = 2;
        mountains = ImageLayer.create(ImageLayer.sub("backgrounds.png", x, y, width, height));
    }

    SpriteSheet link = new Link(getWidth() / 4, getHeight() + 50*2,1);
    BoundingLine line;
    TileMap map;

    @Override
    protected void onInitialize() {

        GlobalCamera.getInstance().setScaling(2);
        try {
            map = TileMap.from("mario4.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        line = new BoundingLine(getWidth(), getHeight() - 50, 0, getHeight() - 50);
        link.setAcceleration(0, 1);
        link.setVelocity(0, 1);
        link.setDrag(0.5, 1);
        link.setPose(SpriteSheet.Pose.RIGHT);
        GlobalCamera.getInstance().setOrigin(link, getWidth(), getHeight());
    }

    @Override
    protected void onPaint(Graphics g) {
        mountains.draw(g);
        map.render(g);
        link.render(g);
    }

    @Override
    protected void onPlay() {
        if (map.canMove(link, SpriteSheet.Pose.DOWN, true)) {
            link.gravitate();
        } else {
            if (pressing[UP]) {
                //if(map.canMove(link, SpriteSheet.Pose.UP, true)) {
                Tile tileAbo = map.getTileAbove(link, true);
                if (tileAbo == null) {
                    link.jump(15);
                    link.gravitate();

                } else {
                    if (tileAbo.isCollision()) {
                        System.out.println("IS COLLISION");
                    } else {
                        System.out.println("NOT COLLISION");
                    }
                }
            }
        }


        if (pressing[LT]) {
            if (map.canMove(link, SpriteSheet.Pose.LEFT, true)) {
                link.moveLeft(link.getSpeed());
                GlobalCamera.getInstance().moveLeft(link.getSpeed());
            }
        } else if (pressing[RT]) {
            if (map.canMove(link, SpriteSheet.Pose.RIGHT, true)) {
                link.moveRight(link.getSpeed());
                GlobalCamera.getInstance().moveLeft(link.getSpeed());
            }
        }


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
