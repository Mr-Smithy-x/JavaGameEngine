package com.charlton.containters;

import com.charlton.base.GameHolder;
import com.charlton.game.algorithms.pathfinding.AStar;
import com.charlton.game.algorithms.pathfinding.models.Node;
import com.charlton.game.audioplayer.SoundTrack;
import com.charlton.game.display.GlobalCamera;
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
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.PriorityQueue;

public class CustomTileGameContainer extends GameHolder {

    private TileMap tileSet;
    private Link link = null;
    private Dog dog = null;
    private AStar<TileMap, Tile> aStar;
    private PriorityQueue<Node> path;


    @Override
    protected void init() {
        try {
            tileSet = TileMap.from("collision_with_layer.json");
            aStar = new AStar<>(tileSet);
            link = new Link(getWidth() / 2, getHeight() / 2, 1) {
                {
                    setVelocity(0.0, 0.7);
                    setAcceleration(0, 1);
                }
            };
            dog = new Dog(getWidth() / 4, getHeight() / 4, 1) {
                {
                    setAcceleration(1, 1);
                    setVelocity(0, 0);
                    setDrag(0.04, 0.04);
                }
            };
            GlobalCamera.getInstance().setOrigin(link, getWidth(), getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            SoundTrack s = new SoundTrack("overworld.wav");
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
            int scaled_x = GlobalCamera.getInstance().getScaling() * p.getX();
            int scaled_y = GlobalCamera.getInstance().getScaling() * p.getY();
            int camera_offset_x = (int) ((int) GlobalCamera.getInstance().getX());
            int camera_offset_y = (int) ((int) GlobalCamera.getInstance().getY());
            int scaled_width = subimage.getWidth() * GlobalCamera.getInstance().getScaling();
            int scaled_height = subimage.getHeight() * GlobalCamera.getInstance().getScaling();
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

            if (GlobalCamera.DEBUG) {
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

                g.setColor(new Color(1, 1, 1));
                String format = String.format("(%s, %s)", p.getX(), p.getY());
                int formatWidth = g.getFontMetrics().stringWidth(format);

                g.drawString(format, (scaled_x - camera_offset_x) + (scaled_width / 2) - (formatWidth / 2), (scaled_y - camera_offset_y) + scaled_height / 2);
            }

        }
        link.render(g);
        dog.render(g);
    }

    @Override
    public void inGameLoop() {
        super.inGameLoop();
        dog.gravitate();
        if (dog.inVicinity(link, 400)) {
            if (dog.getY().intValue() + 16 * GlobalCamera.getInstance().getScaling() > link.getY().intValue()) {
                if (tileSet.canMove(dog, SpriteSheet.UP)) {
                    dog.moveUp();
                }
            }
            if (dog.getY().intValue() - 16 * GlobalCamera.getInstance().getScaling() < link.getY().intValue()) {
                if (tileSet.canMove(dog, SpriteSheet.DOWN)) {
                    dog.moveDown();
                }
            }
            if (dog.getX().intValue() + 16 * GlobalCamera.getInstance().getScaling() < link.getX().intValue()) {
                if (tileSet.canMove(dog, SpriteSheet.RIGHT)) {
                    dog.moveRight();
                }
            }
            if (dog.getX().intValue() - 16 * GlobalCamera.getInstance().getScaling() > link.getX().intValue()) {
                if (tileSet.canMove(dog, SpriteSheet.LEFT)) {
                    dog.moveLeft();
                }
            }
            if (dog.inVicinity(link, 100)) {
                if (pressing[SPACE] || pressing[_Z]) {
                    link.hit(dog);
                }
            }
            if (dog.overlaps(link)) {
                GlobalCamera.getInstance().setOrigin(link, getWidth(), getHeight());
            }
        }
        if (pressing[_Z]) {
            link.spin();
        } else if (pressing[SPACE]) {
            link.attack();
        } else {
            if (pressing[UP]) {
                link.setPose(SpriteSheet.UP);
                if (tileSet.canMove(link, SpriteSheet.UP)) {
                    link.moveUp();
                    GlobalCamera.getInstance().moveUp(link.getSpeed());
                }
            }
            if (pressing[DN]) {

                link.setPose(SpriteSheet.DOWN);
                if (tileSet.canMove(link, SpriteSheet.DOWN)) {
                    link.moveDown();
                    GlobalCamera.getInstance().moveDown(link.getSpeed());
                }
            }
            if (pressing[LT]) {

                link.setPose(SpriteSheet.LEFT);
                if (tileSet.canMove(link, SpriteSheet.LEFT)) {
                    link.moveLeft();
                    GlobalCamera.getInstance().moveLeft(link.getSpeed());
                }
            }
            if (pressing[RT]) {

                link.setPose(SpriteSheet.RIGHT);
                if (tileSet.canMove(link, SpriteSheet.RIGHT)) {
                    link.moveRight();
                    GlobalCamera.getInstance().moveRight(link.getSpeed());
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if (e.getKeyCode() == _D) {
            GlobalCamera.DEBUG = !GlobalCamera.DEBUG;
        }

        if(e.getKeyCode() == _A){
            find();
        }
    }

    private void find() {
        Tile currentTile = SpriteHelper.getCurrentTile(tileSet, dog);
        Tile currentTile1 = SpriteHelper.getCurrentTile(tileSet, link);
        System.out.printf("DOG: (%s) - Link: (%s)\n", currentTile, currentTile1);
        //aStar.setStartNode(currentTile);
        //aStar.setEndNode(currentTile1);

        if(currentTile.isCollision() || currentTile1.isCollision()){
            System.out.println("Someone is in a collision tile..");
            return;
        }


        System.out.println("This feature runs oom when you are too close...");


        //aStar.solve();
        //this.path = aStar.getPath();
        //System.out.println(path);
    }


    protected CustomTileGameContainer(JFrame container, Canvas canvas) {
        super(container, canvas);
    }

    protected CustomTileGameContainer(Applet applet) {
        super(applet);
    }

    public static GameHolder frame(int width, int height) {
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
        return new CustomTileGameContainer(frame, canvas);
    }

    public static GameHolder applet(Applet applet) {
        return new CustomTileGameContainer(applet);
    }


}
