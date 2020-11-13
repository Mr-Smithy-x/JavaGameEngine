package com.charlton.game.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SubImage {

    private final int spritePositionStartX;
    private final int spritePositionStartY;
    private final int width;
    private final int height;
    private Image image;

    public SubImage(int x, int y, int width, int height) {
        this.spritePositionStartX = x;
        this.spritePositionStartY = y;
        this.width = width;
        this.height = height;
    }

    public Image getImage() {
        return image;
    }


    public void setImage(BufferedImage image) {
        this.setImage(image, width);
    }

    public void setImage(BufferedImage image, int scale_aspect) {
        if (this.image == null) {
            BufferedImage subimage = image.getSubimage(spritePositionStartX, spritePositionStartY, width, height);
            this.image = subimage.getScaledInstance(scale_aspect, scale_aspect, Image.SCALE_DEFAULT);
        }
    }

    public int getSpritePositionStartX() {
        return spritePositionStartX;
    }

    public int getSpritePositionStartY() {
        return spritePositionStartY;
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }
}
