package com.charlton.sprites;

import com.charlton.contracts.Drawable;
import com.charlton.models.MovableObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SpriteSheet extends MovableObject implements Drawable {


    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int JUMP = 4;
    protected BufferedImage spriteSheet;
    protected SubImage[][] subImages = new SubImage[4][];
    protected SubImage[] stillImages = new SubImage[4];
    private File file;
    protected int delay = 0;
    protected int duration = 0;
    protected int current_column = 0;
    protected int pose = 0;

    public SpriteSheet(String name) throws IOException {
        initializeSheet(name);
    }

    @Deprecated
    public void initEvenly(int rows, int columns, int width, int height) {
        for (int i_row = 0; i_row < rows; i_row++) {
            int roster_height = spriteSheet.getHeight();
            int real_height = height;
            for (int j_column = 0; j_column < columns; j_column++) {
                int roster_width = spriteSheet.getWidth();
                int real_width = width;
                //BufferedImage subimage = spriteSheet.getSubimage(j_column * width, i_row * height, width, height);
                //subimages[i_row][j_column] = new SubImage(subimage);
            }
        }
    }


    protected SubImage[] initAnimation(int column, int row, int width, int height, int size) {
        SubImage[] images = new SubImage[size];
        for (int i = 0; i < size; i++) {
            images[i] = new SubImage(new Point((column * width) + (i * width), row * height), width, height);
        }
        return images;
    }

    protected void validate() {
        if (current_column >= subImages[pose].length) {
            current_column = 0;
        }
        if (current_column < 0) {
            current_column = subImages[pose].length - 1;
        }
    }

    protected void nextImageColumn() {
        if (delay == 0) {
            current_column++;
            validate();
            delay = duration;
        }
        delay--;
    }

    protected void prevImageColumn() {
        if (delay == 0) {
            current_column--;
            validate();
            delay = duration;
        }
        delay--;
    }


    public Image getImage() {
        validate();
        SubImage[] subImage = subImages[pose];
        if (subImage.length <= current_column) {
            current_column = 0;
        }
        SubImage sub = subImage[current_column];
        return spriteSheet.getSubimage(sub.getPoint().x, sub.getPoint().y, sub.getWidth(), sub.getHeight());
    }


    public Image getStillImage() {
        SubImage stillImage = stillImages[pose];
        return spriteSheet.getSubimage(stillImage.getPoint().x, stillImage.getPoint().y, stillImage.getWidth(), stillImage.getHeight());
    }


    protected void initializeSheet(String filename) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        file = new File(cl.getResource("res/" + filename).getFile());
        spriteSheet = ImageIO.read(file);
    }

    @Override
    public void draw(Graphics g) {

    }


    public static class SubImage {

        private final Point point;
        private final int width;
        private final int height;

        public SubImage(Point point,
                        int width,
                        int height) {
            this.point = point;
            this.width = width;
            this.height = height;
        }

        public Point getPoint() {
            return point;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

}
