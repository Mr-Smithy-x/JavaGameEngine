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
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CustomTileGame extends GameApplet {


    ZeldaBGTileSet set = new ZeldaBGTileSet(this);
    SpriteSheet link = new Dog(200, 150, 5) {
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
    private TileSet tileSet;
    private BufferedImage image;

    public CustomTileGame() throws IOException {
    }


    @Override
    public void init() {
        try {
            tileSet = TileSet.from("collision_test.json");
            image = tileSet.getImage();
            Camera.set(link.getX().doubleValue(), link.getY().doubleValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.init();
    }


    @Override
    public void paint(Graphics g) {
        for (Point p: tileSet.pointIterator()){
            long tileAddress = tileSet.get(p);
            Tile tile = Tile.create(tileAddress);
            BufferedImage subimage = image.getSubimage(tile.getPositionX(), tile.getPositionY(), tile.getPixelW(), tile.getPixelH());
            g.drawImage(subimage, p.getX(), p.getY(), tile.getPixelW(), tile.getPixelH(), null);
        }
        link.draw(g);
    }

    int S = 16;

    @Override
    public void inGameLoop() {
        super.inGameLoop();
        if(pressing[UP]){
            if(tileSet.canMove(link, UP)) {
                link.moveBy(0, -3);
                Camera.moveUp(3);
            }
        }if(pressing[DN]){
            if(tileSet.canMove(link, DN)) {
                link.moveBy(0, +3);
                Camera.moveDown(3);
            }
        }if(pressing[LT]){

            if(tileSet.canMove(link, LT)) {
                link.moveBy(-3, 0);
                Camera.moveLeft(3);
            }
        }if(pressing[RT]){

            if(tileSet.canMove(link, RT)) {
                link.moveBy(+3, 0);
                Camera.moveRight(3);
            }
        }
        Camera.update();
    }
}
