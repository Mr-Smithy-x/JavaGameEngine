package com.charlton.sprites;

import java.awt.*;
import java.io.IOException;

public class Zelda extends SpriteSheet {

    int rows = 10;
    int columns = 15;
    boolean moving = false;

    public Zelda() throws IOException {
        super("link.png");
        this.position_x = 200;
        this.position_y = 200;
        this.duration = 3;
        this.subImages = new SubImage[rows][];
        this.stillImages = new SubImage[rows];
        initializeSprites();
    }

    protected void initializeSprites() {
        subImages[UP] = initAnimation(0, 4, 30, 30, 8);
        subImages[DOWN] = initAnimation(0, 1, 30, 30, 8);
        subImages[LEFT] = initAnimation(8, 1, 30, 30, 6);
        subImages[RIGHT] = initAnimation(8, 4, 30, 30, 6);
        stillImages[UP] = initAnimation(2, 0, 30, 30, 1)[0];
        stillImages[DOWN] = initAnimation(1, 0, 30, 30, 1)[0];
        stillImages[LEFT] = initAnimation(5, 0, 30, 30, 1)[0];
        stillImages[RIGHT] = initAnimation(11, 4, 30, 30, 1)[0];

    }


    @Override
    public void moveBy(double dx, double dy) {
        super.moveBy(dx, dy);
        if (dx < 0) {
            pose = LEFT;
        }
        if (dx > 0) {
            pose = RIGHT;
        }
        if (dy > 0) {
            pose = DOWN;
        }
        if (dy < 0) {
            pose = UP;
        }
        nextImageColumn();
        moving = true;
    }

    @Override
    public void draw(Graphics g) {
        Image image;
        if (moving) {
            image = getImage();
        } else {
            image = getStillImage();
        }
        g.drawImage(image, (int) position_x, (int) position_y, 3 * image.getWidth(null), 3 * image.getHeight(null), null);
        moving = false;
    }

    @Override
    public int getType() {
        return SpriteSheet.TYPE_POLY;
    }
}
