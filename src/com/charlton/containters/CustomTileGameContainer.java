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
    protected void onInitialize() {
        try {
            tileSet = TileMap.from("collision_with_layer.json");
            aStar = new AStar<>(tileSet);
            link = new Link(getWidth() / 2, getHeight() / 2, 2) {
                {
                    setVelocity(0.0, 0.7);
                    setAcceleration(0, 1);
                }
            };
            dog = new Dog(getWidth() / 4, getHeight() / 4, 2) {
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
            SoundTrack s = new SoundTrack("soundtrack.wav");
            s.play();
        } catch (IOException | UnsupportedAudioFileException | InvalidMidiDataException | MidiUnavailableException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void paint(Graphics g) {
        tileSet.render(g);
        link.render(g);
        dog.render(g);

        //GlobalCamera.getInstance().render(g);
    }

    @Override
    protected void onPlay() {
        dog.gravitate();

        if (dog.inVicinity(link, 400)) {
            if (dog.getY().intValue() + 16 * GlobalCamera.getInstance().getScaling() > link.getY().intValue()) {
                if (tileSet.canMove(dog, SpriteSheet.Pose.UP)) {
                    dog.moveUp(dog.getSpeed());
                }
            }
            if (dog.getY().intValue() - 16 * GlobalCamera.getInstance().getScaling() < link.getY().intValue()) {
                if (tileSet.canMove(dog, SpriteSheet.Pose.DOWN)) {
                    dog.moveDown(dog.getSpeed());
                }
            }
            if (dog.getX().intValue() + 16 * GlobalCamera.getInstance().getScaling() < link.getX().intValue()) {
                if (tileSet.canMove(dog, SpriteSheet.Pose.RIGHT)) {
                    dog.moveRight(dog.getSpeed());
                }
            }
            if (dog.getX().intValue() - 16 * GlobalCamera.getInstance().getScaling() > link.getX().intValue()) {
                if (tileSet.canMove(dog, SpriteSheet.Pose.LEFT)) {
                    dog.moveLeft(dog.getSpeed());
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
                link.setPose(SpriteSheet.Pose.UP);
                if (tileSet.canMove(link, SpriteSheet.Pose.UP)) {
                    link.moveUp(link.getSpeed());
                    GlobalCamera.getInstance().moveUp(link.getSpeed());
                }
            }
            if (pressing[DN]) {

                link.setPose(SpriteSheet.Pose.DOWN);
                if (tileSet.canMove(link, SpriteSheet.Pose.DOWN)) {
                    link.moveDown(link.getSpeed());
                    GlobalCamera.getInstance().moveDown(link.getSpeed());
                }
            }
            if (pressing[LT]) {

                link.setPose(SpriteSheet.Pose.LEFT);
                if (tileSet.canMove(link, SpriteSheet.Pose.LEFT)) {
                    link.moveLeft(link.getSpeed());
                    GlobalCamera.getInstance().moveLeft(link.getSpeed());
                }
            }
            if (pressing[RT]) {

                link.setPose(SpriteSheet.Pose.RIGHT);
                if (tileSet.canMove(link, SpriteSheet.Pose.RIGHT)) {
                    link.moveRight(link.getSpeed());
                    GlobalCamera.getInstance().moveRight(link.getSpeed());
                }
            }
        }
        GlobalCamera.getInstance().setOrigin(link, getWidth(), getHeight());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
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



    public static GameHolder holder(int width, int height) {
        JFrame frame = frame(width, height);
        Canvas canvas = canvas(width, height);
        frame.add(canvas);
        frame.pack();
        return new CustomTileGameContainer(frame, canvas);
    }

    public static Canvas canvas(int width, int height) {
        Canvas canvas = new Canvas();
        canvas.setFocusable(true);
        canvas.setFocusTraversalKeysEnabled(true);
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        return canvas;
    }
    public static JFrame frame(int width, int height) {
        JFrame frame = new JFrame("Zelda Game");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    public static GameHolder applet(Applet applet) {
        return new CustomTileGameContainer(applet);
    }


}
