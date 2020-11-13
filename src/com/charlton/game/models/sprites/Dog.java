package com.charlton.game.models.sprites;

import com.charlton.game.display.Camera;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.SubImage;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.base.BoundingCircle;
import com.charlton.game.models.contracts.Animal;

import java.awt.*;
import java.io.IOException;

public class Dog extends SpriteSheet implements Animal {

    int speed = 3;

    public Dog(int position_x, int position_y, int duration) throws IOException {
        super("dog.png");
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
    public void render(Graphics g) {
        super.render(g);
        g.setPaintMode();
        g.drawString("Skeet", (int) (getX().intValue() - 10 - GlobalCamera.getInstance().getX()), (int) (getY().intValue() - 15 - GlobalCamera.getInstance().getY()));
    }

    @Override
    public SpriteSheet getSpriteSheet() {
        return this;
    }

    @Override
    public float getSpeed() {
        return Camera.getInstance().getScaling() * speed;
    }
}
