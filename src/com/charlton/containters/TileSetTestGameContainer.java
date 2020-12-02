package com.charlton.containters;

import com.charlton.base.GameHolder;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.sprites.Link;
import com.charlton.game.models.tileset.ZeldaBGTileSet;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class TileSetTestGameContainer extends GameHolder {

    ZeldaBGTileSet set;
    SpriteSheet link = new Link(32, 32, 4) {
        {
            setVelocity(0.0, 0.7);
            setAcceleration(0, 1);
        }
    };
    String[] map = {
            ".....###########################################",
            "...............................................#",
            ".....###....##.....#############################",
            "................................................",
            "#########......############...##################",
            "...#...........................................#",
            "####...##......#########...#####################",
            "...............#...........#.....................",
            "...#.......####################################",
            "...#...........................................#",
            "...######.......##########################.....#",
            ".........................................#.....#",
            "############......########################.....#",
            "#..............................................#",
            "#.......########################################",
            "#..............................................#",
            //"####.....#######################################",
            //"...#.....########################################",
            //"...#...........................................#",
            //"...####..#######################################",
            //"................................................",
            //"###########...#############...##################",
            //"...#...........................................#",
            //"####...#################...#####################",
            ////"...............#...........#.....................",
            ////"...#....#######################################",
            //"...#...........................................#",
            //"...######...##############################.....#",
            //".........................................#.....#",
            //"##########################################.....#",
            //"#..............................................#",
            //"#......#########################################",
            //"#..............................................#",
            //"################################################",

    };
    private Image tile;


    protected TileSetTestGameContainer(JFrame container, Canvas canvas) throws IOException {
        super(container, canvas);
    }

    public TileSetTestGameContainer(Applet container) throws IOException {
        super(container);
    }


    @Override
    protected void onInitialize() {
        try {
            set = new ZeldaBGTileSet(getContainer());
            tile = set.getTile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPaint(Graphics g) {
        for (int row = 0; row < map.length; row++) {
            if (row * S > this.getHeight()) {
                continue;
            }
            for (int col = 0; col < map[row].length(); col++) {
                if (col * S > this.getWidth()) {
                    continue;
                }
                char c = map[row].charAt(col);
                switch (c) {
                    case '.':
                        g.drawImage(tile, col * S, row * S, S, S, null);
                        break;
                    case '#':
                        g.fillRect(col * S, row * S, S, S);
                        break;
                }
            }
        }

        link.render(g);
    }

    int S = 16;

    @Override
    protected void onPlay() {
        int top = link.getY().intValue();
        int bottom = link.getY().intValue() + S - 1;
        int left = link.getX().intValue();
        int right = link.getX().intValue() + S - 1;

        //link.gravitate();
        try {
            if (pressing[UP]) {
                if (
                        map[(top - S / 8) / S].charAt(left / S) == '.' &&
                                map[(top - S / 8) / S].charAt(right / S) == '.') {
                    link.moveBy(0, -(S / 8));
                }
                link.setPose(Link.Pose.UP);
            }
            if (pressing[DN]) {
                if (
                        map[(bottom + S / 8) / S].charAt(left / S) == '.' &&
                                map[(bottom + S / 8) / S].charAt(right / S) == '.') {

                    link.moveBy(0, (S / 8));
                }
                link.setPose(Link.Pose.DOWN);
            }
            if (pressing[LT]) {
                if (
                        map[(top / S)].charAt((left - S / 8) / S) == '.' &&
                                map[(top / S)].charAt((left - S / 8) / S) == '.') {

                    link.moveBy(-(S / 8), 0);
                }
                link.setPose(Link.Pose.LEFT);
            }
            if (pressing[RT]) {
                if (
                        map[(top / S)].charAt((right + S / 8) / S) == '.' &&
                                map[(bottom / S)].charAt((right + S / 8) / S) == '.') {

                    link.moveBy((S / 8), 0);

                }
                link.setPose(Link.Pose.RIGHT);
            }
            if ((map[bottom / S].charAt(left + S)) != '.' && (map[bottom / S].charAt(right / S) != '.')) {

            }else{
            }
        } catch (Exception e) {

        }
    }


    public static GameHolder holder(int width, int height) throws IOException {
        JFrame frame = frame(width, height);
        Canvas canvas = canvas(width, height);
        frame.add(canvas);
        frame.pack();
        return new TileSetTestGameContainer(frame, canvas);
    }

    public static GameHolder applet(Applet applet) throws IOException {
        return new TileSetTestGameContainer(applet);
    }

}
