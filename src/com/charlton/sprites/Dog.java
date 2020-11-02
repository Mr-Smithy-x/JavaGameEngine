package com.charlton.sprites;

import com.charlton.models.BoundingCircle;
import com.charlton.models.SpriteSheet;

import java.awt.*;
import java.io.IOException;

public class Dog extends SpriteSheet {


    public Dog(int position_x, int position_y, int duration) throws IOException {
        super("dog.png", 2);
        this.duration = duration;
        this.subImages = new SubImage[16][];
        this.stillImages = new SubImage[16];
        this.circle = new BoundingCircle(position_x, position_y, 20, 90);
        this.circle.setWorld(position_x, position_y);
        this.circle.bind(this);
        initializeSprites();
    }

    private void initializeSprites() {
        subImages[DOWN] = initAnimation(0, 0, 32, 32, 4);
        subImages[RIGHT] = initAnimation(0, 1, 32, 32, 4);
        subImages[UP] = initAnimation(0, 2, 32, 32, 4);
        subImages[LEFT] = initAnimation(0, 3, 32, 32, 4);

        stillImages[UP] = subImages[UP][0];
        stillImages[DOWN] = subImages[DOWN][0];
        stillImages[LEFT] = subImages[LEFT][0];
        stillImages[RIGHT] = subImages[RIGHT][0];
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setPaintMode();
        g.drawString("Skeet", getX().intValue()-10, getY().intValue() - 15);
    }
}
