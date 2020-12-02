package com.charlton.game.models.sprites;

import com.charlton.game.display.Camera;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.SubImage;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.base.BoundingCircle;
import com.charlton.game.models.contracts.Animal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dog extends SpriteSheet implements Animal {

    int speed = 3;

    public Dog(int position_x, int position_y, int duration) throws IOException {
        super("dog.png");
        this.duration = duration;
        this.box = new BoundingCircle(position_x, position_y, 20, 90);
        this.box.setWorld(position_x, position_y);
    }


    @Override
    protected Map<Pose, List<SubImage>> initializeSheet(BufferedImage spriteSheet) {
        Map<Pose, List<SubImage>> images = new HashMap<>();
        images.put(Pose.DOWN, initAnimation(0, 0, 32, 32, 4));
        images.put(Pose.RIGHT, initAnimation(0, 1, 32, 32, 4));
        images.put(Pose.UP, initAnimation(0, 2, 32, 32, 4));
        images.put(Pose.LEFT, initAnimation(0, 3, 32, 32, 4));
        return images;
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
    public double getSpeed() {
        return Camera.getInstance().getScaling() * speed;
    }
}
