package com.charlton.game.models.tileset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BaseTileSet {


    private File file;

    protected BufferedImage initializeTileSet(String filename) throws IOException {
        file = new File("assets/res/" + filename);
        return ImageIO.read(file);
    }


    public static class Point {

        private int point_x;
        private int point_y;
        private int point_x2;
        private int point_y2;

        public int getPointX() {
            return point_x;
        }

        public int getPointY() {
            return point_y;
        }

        public int getPointX2() {
            return point_x2;
        }

        public int getPointY2() {
            return point_y2;
        }

        protected Point(){}

        public int getWidth(){
            return point_x2 - point_x;
        }

        public int getHeight(){
            return point_y2 - point_y;
        }

        protected Point(int point_x, int point_y, int point_x2, int point_y2){
            this.point_x = point_x;
            this.point_y = point_y;
            this.point_x2 = point_x2;
            this.point_y2 = point_y2;
        }

        public Point setPointX(int point_x) {
            this.point_x = point_x;
            return this;
        }

        public Point setPointY(int point_y) {
            this.point_y = point_y;
            return this;
        }

        public Point setPointX2(int point_x2) {
            this.point_x2 = point_x2;
            return this;
        }

        public Point setPointY2(int point_y2) {
            this.point_y2 = point_y2;
            return this;
        }

        public static Point create(){
            return new Point();
        }

        public static Point create(int point_x, int point_y, int point_x2, int point_y2){
            return new Point(point_x, point_y, point_x2, point_y2);
        }

    }


}
