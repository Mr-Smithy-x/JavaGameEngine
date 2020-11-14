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
    public void init() {
        try {
            set = new ZeldaBGTileSet(getContainer());
            tile = set.getTile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void paint(Graphics g) {
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
    public void inGameLoop() {
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
                link.setPose(Link.UP);
            }
            if (pressing[DN]) {
                if (
                        map[(bottom + S / 8) / S].charAt(left / S) == '.' &&
                                map[(bottom + S / 8) / S].charAt(right / S) == '.') {

                    link.moveBy(0, (S / 8));
                }
                link.setPose(Link.DOWN);
            }
            if (pressing[LT]) {
                if (
                        map[(top / S)].charAt((left - S / 8) / S) == '.' &&
                                map[(top / S)].charAt((left - S / 8) / S) == '.') {

                    link.moveBy(-(S / 8), 0);
                }
                link.setPose(Link.LEFT);
            }
            if (pressing[RT]) {
                if (
                        map[(top / S)].charAt((right + S / 8) / S) == '.' &&
                                map[(bottom / S)].charAt((right + S / 8) / S) == '.') {

                    link.moveBy((S / 8), 0);

                }
                link.setPose(Link.RIGHT);
            }
            if ((map[bottom / S].charAt(left + S)) != '.' && (map[bottom / S].charAt(right / S) != '.')) {

            }else{
            }
        } catch (Exception e) {

        }
    }


    public static GameHolder frame(int width, int height) throws IOException {
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
        return new TileSetTestGameContainer(frame, canvas);
    }

    public static GameHolder applet(Applet applet) throws IOException {
        return new TileSetTestGameContainer(applet);
    }

}
