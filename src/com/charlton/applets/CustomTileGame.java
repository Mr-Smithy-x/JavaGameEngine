package com.charlton.applets;

import com.charlton.applets.base.GameApplet;
import com.charlton.game.algorithms.pathfinding.AStar;
import com.charlton.game.algorithms.pathfinding.models.Node;
import com.charlton.game.audioplayer.SoundTrack;
import com.charlton.game.contracts.MovableCollision;
import com.charlton.game.helpers.GlobalCamera;
import com.charlton.game.helpers.SpriteHelper;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.sprites.Dog;
import com.charlton.game.models.sprites.Link;
import com.charlton.game.models.tilemap.Point;
import com.charlton.game.models.tilemap.Tile;
import com.charlton.game.models.tilemap.TileMap;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class CustomTileGame extends GameApplet {

    private TileMap tileSet;
    private SpriteSheet link = null;
    private Dog dog = null;
    private AStar<TileMap, Tile> aStar;

    ArrayList<MovableCollision> objectList = new ArrayList<MovableCollision>() {
        {

            //add(new Link(400, 400, 1));
            //add(new Dog(300, 300, 1));

        }
    };
    private ArrayList<Node> path;


    public CustomTileGame() {

    }

    @Override
    public void init() {
        try {
            tileSet = TileMap.from("collision_with_layer.json");
            aStar = new AStar<>(tileSet);

            objectList.forEach(obj -> obj.setAcceleration(0, 1)
                    .setVelocity(0, 0)
                    .setDrag(0.01, 0.01));

            link = new Link(getWidth() / 2, getHeight() / 2, 1) {
                {
                    setVelocity(0.0, 0.7);
                    setAcceleration(0, 1);
                }
            };
            dog = new Dog(getWidth() / 4, getHeight() / 4, 1) {
                {
                    setVelocity(0.0, 0.7);
                    setAcceleration(0, 1);
                }
            };
            GlobalCamera.setOrigin(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.init();
        SoundTrack s = new SoundTrack("overworld.wav");
        try {
            s.play();
        } catch (IOException | UnsupportedAudioFileException | InvalidMidiDataException | MidiUnavailableException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void paint(Graphics g) {
        for (Point p : tileSet) {
            Tile tile = tileSet.get(p);
            BufferedImage subimage = tile.getImage();
            int scaled_x = GlobalCamera.scaling_factor * p.getX();
            int scaled_y = GlobalCamera.scaling_factor * p.getY();
            int camera_offset_x = (int) GlobalCamera.x + GlobalCamera.x_origin;
            int camera_offset_y = (int) GlobalCamera.y + GlobalCamera.y_origin;
            int scaled_width = subimage.getWidth() * GlobalCamera.scaling_factor;
            int scaled_height = subimage.getHeight() * GlobalCamera.scaling_factor;
            g.drawImage(subimage, scaled_x - camera_offset_x, scaled_y - camera_offset_y,
                    scaled_width, scaled_height,
                    null);
            if (path != null) {
                if (path.contains(tile)) {
                    g.setColor(new Color(0, 0.2f, 0, 0.4f));
                    g.fillRect(scaled_x - camera_offset_x,
                            scaled_y - camera_offset_y,
                            scaled_width,
                            scaled_height);
                }
            }

            if (DEBUG) {
                g.setColor(new Color(0.2f, 0, 0, 0.4f));
                if (tile.isCollision()) {
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


        link.drawRelativeToCamera(g);
        dog.drawRelativeToCamera(g);
        //objectList.forEach(obj -> ((Drawable) obj).draw(g));


    }


    @Override
    public void inGameLoop() {
        super.inGameLoop();
        if (pressing[_Z]) {
            ((Link) link).spin();
        } else if (pressing[SPACE]) {
            ((Link) link).attack();
        } else {
            if (pressing[UP]) {
                link.setPose(SpriteSheet.UP);
                if (tileSet.canMove(link, UP)) {
                    //link.moveBy(0, -CameraHelper.scaling_factor);
                    GlobalCamera.moveUp(16);
                }
            }
            if (pressing[DN]) {

                link.setPose(SpriteSheet.DOWN);
                if (tileSet.canMove(link, DN)) {
                    //link.moveBy(0, CameraHelper.scaling_factor);
                    GlobalCamera.moveDown(16);
                }
            }
            if (pressing[LT]) {

                link.setPose(SpriteSheet.LEFT);
                if (tileSet.canMove(link, LT)) {
                    //link.moveBy(-CameraHelper.scaling_factor, 0);
                    GlobalCamera.moveLeft(16);
                }
            }
            if (pressing[RT]) {

                link.setPose(SpriteSheet.RIGHT);
                if (tileSet.canMove(link, RT)) {
                    //link.moveBy(CameraHelper.scaling_factor, 0);
                    GlobalCamera.moveRight(16);
                }
            }
        }

/*
        objectList.forEach(obj -> {
            obj.gravitate();

            if (!obj.inVicinity(link, 80)) {
                obj.chase(link);
            }
            obj.overlaps(link);
        });

        Camera.update();*/
    }


    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);


        if (e.getKeyCode() == SPACE) {
            // new Thread(this::find).start();
        }

        if (e.getKeyCode() == _D) {
            DEBUG = !DEBUG;
        }
    }

    private void find() {
        Tile currentTile = SpriteHelper.getCurrentTile(tileSet, dog);
        Tile currentTile1 = SpriteHelper.getCurrentTile(tileSet, link);
        System.out.printf("DOG: (%s) - Link: (%s)\n", currentTile, currentTile1);
        aStar.setStartNode(currentTile);
        aStar.setEndNode(currentTile1);
        aStar.solve();
        this.path = aStar.getPath();
        System.out.println(path);
    }
}
