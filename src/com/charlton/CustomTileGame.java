package com.charlton;

import com.charlton.helpers.Camera;
import com.charlton.models.SpriteSheet;
import com.charlton.models.tileset.ZeldaBGTileSet;
import com.charlton.sprites.Dog;
import com.charlton.sprites.Link;
import com.charlton.tilemap.models.Point;
import com.charlton.tilemap.models.Tile;
import com.charlton.tilemap.models.TileSet;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CustomTileGame extends GameApplet {


    private TileSet tileSet;
    private BufferedImage image;
    private SpriteSheet link = new Link(400, 250, 5, 20) {
        {
            setVelocity(0.0, 0.7);
            setAcceleration(0, 1);
        }
    };

    public CustomTileGame() throws IOException {
    }

    @Override
    public void init() {
        try {
            tileSet = TileSet.from("collision_test.json");
            image = tileSet.getImage();
            image.flush();
            Camera.x = Camera.x_origin;
            Camera.y = Camera.y_origin;

                    //set(link.getX().doubleValue() * Camera.scaling_factor, link.getY().doubleValue()* Camera.scaling_factor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.init();
    }


    @Override
    public void paint(Graphics g) {
        for (Point p : tileSet.pointIterator()) {
            long tileAddress = tileSet.get(p);
            Tile tile = Tile.create(tileAddress);
            BufferedImage subimage = image.getSubimage(tile.getPositionX(), tile.getPositionY(), tile.getPixelW(), tile.getPixelH());
            g.drawImage(subimage,
                    (Camera.scaling_factor * p.getX()) - (int)Camera.x + Camera.x_origin,
                    (Camera.scaling_factor * p.getY()) - (int)Camera.y + Camera.y_origin,
                    subimage.getWidth() * Camera.scaling_factor,
                    subimage.getHeight() * Camera.scaling_factor,
                    null);
        }
        link.draw(g);
    }


    @Override
    public void inGameLoop() {
        super.inGameLoop();
        if (pressing[UP]) {
            if (tileSet.canMove(link, UP)) {
                link.moveBy(0, -1);
                Camera.moveUp(10);
            }
        }
        if (pressing[DN]) {
            if (tileSet.canMove(link, DN)) {
                link.moveBy(0, +1);
                Camera.moveDown(10);
            }
        }
        if (pressing[LT]) {

            if (tileSet.canMove(link, LT)) {
                link.moveBy(-1, 0);
                Camera.moveLeft(10);
            }
        }
        if (pressing[RT]) {

            if (tileSet.canMove(link, RT)) {
                link.moveBy(+1, 0);
                Camera.moveRight(10);
            }
        }
        Camera.update();
    }
}
