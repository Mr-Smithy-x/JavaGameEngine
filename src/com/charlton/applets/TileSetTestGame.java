package com.charlton.applets;

import com.charlton.applets.base.GameApplet;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.tileset.ZeldaBGTileSet;
import com.charlton.game.models.sprites.Link;

import java.awt.*;
import java.io.IOException;

public class TileSetTestGame extends GameApplet {


    ZeldaBGTileSet set = new ZeldaBGTileSet(this);
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

    public TileSetTestGame() throws IOException {
    }


    @Override
    public void init() {
        super.init();
        tile = set.getTile();
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

        link.draw(g);
    }

    int S = 16;

    @Override
    public void inGameLoop() {
        super.inGameLoop();
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
}
