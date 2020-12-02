package com.charlton.game.models;


import com.charlton.Game;
import com.charlton.game.gfx.SubImage;
import com.charlton.game.models.base.BoundingCircle;
import com.charlton.game.states.GameState;
import com.charlton.game.states.State;
import com.charlton.game.models.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Creature {


    public Player(Game game, float x, float y) throws IOException {
        super(game, "link.png");
        this.box = new BoundingCircle(x, y, 20, 90);
        this.box.setWorld(x, y);
        setForceDimension(16);
    }



    @Override
    protected Map<Pose, java.util.List<SubImage>> initializeSheet(BufferedImage spriteSheet) {
        Map<Pose, List<SubImage>> subImages = new HashMap<>();
        subImages.put(Pose.UP, initAnimation(0, 4, 30, 30, 8));
        subImages.put(Pose.DOWN, initAnimation(0, 1, 30, 30, 8));
        subImages.put(Pose.LEFT, initAnimation(8, 1, 30, 30, 6));
        subImages.put(Pose.RIGHT, initAnimation(8, 4, 30, 30, 6));
        subImages.get(Pose.UP).add(0, initAnimation(2, 0, 30, 30, 1).get(0));
        subImages.get(Pose.DOWN).add(0, initAnimation(1, 0, 30, 30, 1).get(0));
        subImages.get(Pose.LEFT).add(0, initAnimation(5, 0, 30, 30, 1).get(0));
        subImages.get(Pose.RIGHT).add(0, initAnimation(11, 4, 30, 30, 1).get(0));


        subImages.put(Pose.ATTACK_UP, Arrays.asList(new SubImage(0, 180, 22, 25),
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
        ));
        return subImages;
    }

    @Override
    public void tick() {
        getInput();
        move();
        game.getGameCamera().centerOnEntity(this);
    }

    private void getInput() {
        setVelocityX(0);
        setVelocityY(0);

        GameState state = (GameState) State.getState();
        TileMap tiles = state.getWorld().getTiles();

        if (game.getKeyManager().up) {
            if(tiles.canMove(this, Pose.UP)){
                setVelocityX(-speed);
                setPose(Pose.UP);
            }
        }
        if (game.getKeyManager().down) {
            if(tiles.canMove(this, Pose.DOWN)) {
                setVelocityY(speed);
                setPose(Pose.DOWN);
            }
        }
        if (game.getKeyManager().left) {
            if(tiles.canMove(this, Pose.LEFT)) {
                setVelocityX(-speed);
                setPose(Pose.LEFT);
            }
        }
        if (game.getKeyManager().right) {
            if(tiles.canMove(this, Pose.RIGHT)) {
                setVelocityY(speed);
                setPose(Pose.RIGHT);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Image image;
        if (moving) {
            image = getImage();
        } else {
            image = getStillImage();
        }
        int x = (int)
                (getX().floatValue() - game.getGameCamera().getXOrigin());
        int y = (int) (getY().floatValue() - game.getGameCamera().getYOrigin());
        g.drawImage(image, x,
                y,
                getWidth().intValue(), getHeight().intValue(), null);

        if(State.DEBUG) {
            g.drawRect(x, y, getWidth().intValue(), getHeight().intValue());
        }
        moving = false;
    }
}
