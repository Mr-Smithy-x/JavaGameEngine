package com.charlton;

import com.charlton.helpers.Camera;
import com.charlton.models.SpriteSheet;
import com.charlton.sprites.Dog;
import com.charlton.sprites.Link;
import com.charlton.tilemap.models.Point;
import com.charlton.tilemap.models.Tile;
import com.charlton.tilemap.models.TileSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CustomTileGame extends GameApplet {


    private TileSet tileSet;
    private BufferedImage image;
    private SpriteSheet link = new Link(400, 250, 1) {
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
            image = toCompatibleImage(tileSet.getImage());
            this.image.flush();
            Camera.setOrigin(link);
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


            subimage.flush();
            int scaled_x = Camera.scaling_factor * p.getX();
            int scaled_y = Camera.scaling_factor * p.getY();
            int camera_offset_x = (int) Camera.x + Camera.x_origin;
            int camera_offset_y = (int) Camera.y + Camera.y_origin;
            int scaled_width = subimage.getWidth() * Camera.scaling_factor;
            int scaled_height = subimage.getHeight() * Camera.scaling_factor;

            g.drawImage(subimage,
                    scaled_x - camera_offset_x,
                    scaled_y - camera_offset_y,
                    scaled_width,
                    scaled_height,
                    null);

            if(DEBUG) {
                g.setColor(Color.RED);
                if (Tile.isCollisionTile(tileAddress)) {
                    g.fillRect(scaled_x - camera_offset_x,
                            scaled_y - camera_offset_y,
                            scaled_width,
                            scaled_height);

                } else {
                    g.drawRect(scaled_x - camera_offset_x,
                            scaled_y - camera_offset_y,
                            scaled_width,
                            scaled_height);
                }
            }

        }
        link.draw(g);
    }


    @Override
    public void inGameLoop() {
        super.inGameLoop();
        if(pressing[_Z]){
            ((Link)link).spin();
        }else if(pressing[SPACE]) {
            ((Link)link).attack();
        }else {
            if (pressing[UP]) {

                link.setPose(SpriteSheet.UP);
                if (tileSet.canMove(link, UP)) {
                    //link.moveBy(0, -Camera.scaling_factor);
                    Camera.moveUp(Camera.scaling_factor * Camera.scaling_factor);
                }
            }
            if (pressing[DN]) {

                link.setPose(SpriteSheet.DOWN);
                if (tileSet.canMove(link, DN)) {
                    //link.moveBy(0, Camera.scaling_factor);
                    Camera.moveDown(Camera.scaling_factor * Camera.scaling_factor);
                }
            }
            if (pressing[LT]) {

                link.setPose(SpriteSheet.LEFT);
                if (tileSet.canMove(link, LT)) {
                    //link.moveBy(-Camera.scaling_factor, 0);
                    Camera.moveLeft(Camera.scaling_factor * Camera.scaling_factor);
                }
            }
            if (pressing[RT]) {

                link.setPose(SpriteSheet.RIGHT);
                if (tileSet.canMove(link, RT)) {
                    //link.moveBy(Camera.scaling_factor, 0);
                    Camera.moveRight(Camera.scaling_factor * Camera.scaling_factor);
                }
            }
        }
        Camera.update();
    }
}
