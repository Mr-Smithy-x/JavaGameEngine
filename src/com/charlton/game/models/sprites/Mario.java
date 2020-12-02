package com.charlton.game.models.sprites;

import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.SubImage;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.base.BoundingBox;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mario extends SpriteSheet {

    public Mario(int position_x, int position_y) throws IOException {
        this(position_x, position_y, 2);
    }

    public Mario(int position_x, int position_y, int duration) throws IOException {
        super("mario.png");
        this.duration = duration;
        int width = getStillImage().getWidth(null);
        int height = getStillImage().getHeight(null);
        this.box = new BoundingBox(position_x, position_y,
                width,
                width);
        this.box.setWorld(position_x, position_y);
    }


    @Override
    protected Map<Pose, List<SubImage>> initializeSheet(BufferedImage spriteSheet) {
        Map<Pose, List<SubImage>> subImages = new HashMap<>();
        //subImages.put(Pose.UP, initAnimation(0, 4, 30, 30, 8));
        //subImages.put(Pose.DOWN, initAnimation(0, 1, 30, 30, 8));
        List<SubImage> left = initAnimation(4, 0, 39, 32, 1);
        left.addAll(initAnimation(1, 0, 39, 32, 1));
        subImages.put(Pose.LEFT, left);


        List<SubImage> right = initAnimation(5, 0, 39, 32, 1);
        right.addAll(initAnimation(8, 0, 39, 32, 1));
        subImages.put(Pose.RIGHT, right);

        List<SubImage> down = initAnimation(2, 1, 39, 32, 1);
        subImages.put(Pose.DOWN, down);


        List<SubImage> up = initAnimation(3, 1, 39, 32, 1);
        subImages.put(Pose.UP, up);


        //subImages.get(Pose.UP).add(0, initAnimation(2, 0, 30, 30, 1).get(0));
        //subImages.get(Pose.DOWN).add(0, initAnimation(1, 0, 30, 30, 1).get(0));
        //subImages.get(Pose.LEFT).add(0, initAnimation(5, 0, 30, 30, 1).get(0));
        //subImages.get(Pose.RIGHT).add(0, initAnimation(11, 4, 30, 30, 1).get(0));


        /*subImages.put(Pose.ATTACK_UP, Arrays.asList(new SubImage(0, 180, 22, 25),
                initAnimation(2, 0, 30, 30, 1).get(0),
                new SubImage(30, 177, 22, 30),
                new SubImage(61, 174, 20, 35),
                new SubImage(89, 177, 24, 30)
        ));
        subImages.put(Pose.ATTACK_DOWN, Arrays.asList(
                initAnimation(1, 0, 30, 30, 1).get(0),
                new SubImage(0, 90, 21, 23),
                new SubImage(30, 90, 22, 23),
                new SubImage(61, 85, 20, 32),
                new SubImage(91, 85, 20, 32),
                new SubImage(115, 87, 28, 29)
        ));

        subImages.put(Pose.ATTACK_LEFT, Arrays.asList(
                initAnimation(5, 0, 30, 30, 1).get(0),
                new SubImage(242, 90, 260 - 242, 23),
                new SubImage(268, 90, 294 - 268, 24),
                new SubImage(295, 91, 326 - 295, 21),
                new SubImage(327, 91, 355 - 327, 21)
        ));

        subImages.put(Pose.ATTACK_RIGHT, Arrays.asList(
                initAnimation(11, 4, 30, 30, 1).get(0),
                new SubImage(242, 180, 260 - 242, 23),
                new SubImage(268, 180, 294 - 268, 24),
                new SubImage(295, 181, 326 - 295, 21),
                new SubImage(327, 181, 355 - 327, 21)
        ));

        subImages.put(Pose.SPIN_ATTACK, Arrays.asList(
                subImages.get(Pose.DOWN).get(0),
                new SubImage(115, 180, 32, 23), // Up
                new SubImage(359, 86, 382 - 359, 31), //LEFT,
                new SubImage(145, 88, 31, 27), // Down
                new SubImage(359, 176, 382 - 359, 31) // RIGHT
        ));*/
        return subImages;
    }

    @Override
    public double getSpeed() {
        return GlobalCamera.getInstance().getScaling() * 4;
    }
}
