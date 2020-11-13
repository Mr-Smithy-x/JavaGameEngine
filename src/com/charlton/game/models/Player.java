package com.charlton.game.models;


import com.charlton.Game;
import com.charlton.game.gfx.SubImage;
import com.charlton.game.models.base.BoundingCircle;
import com.charlton.game.states.GameState;
import com.charlton.game.states.State;
import com.charlton.game.models.tilemap.TileMap;

import java.awt.*;
import java.io.IOException;

public class Player extends Creature {


    public Player(Game game, float x, float y) throws IOException {
        super(game, "link.png");
        this.subImages = new SubImage[16][];
        this.stillImages = new SubImage[16];
        this.circle = new BoundingCircle(x, y, 20, 90);
        this.circle.setWorld(x, y);
        this.circle.bind(this);
        setForceDimension(16);
        initializeSprites();
    }


    @Override
    protected void initializeSprites() {
        subImages[UP] = initAnimation(0, 4, 30, 30, 8);
        subImages[DOWN] = initAnimation(0, 1, 30, 30, 8);
        subImages[LEFT] = initAnimation(8, 1, 30, 30, 6);
        subImages[RIGHT] = initAnimation(8, 4, 30, 30, 6);
        stillImages[UP] = initAnimation(2, 0, 30, 30, 1)[0];
        stillImages[DOWN] = initAnimation(1, 0, 30, 30, 1)[0];
        stillImages[LEFT] = initAnimation(5, 0, 30, 30, 1)[0];
        stillImages[RIGHT] = initAnimation(11, 4, 30, 30, 1)[0];


        subImages[ATTACK_UP] = new SubImage[]{
                new SubImage(0, 180, 22, 25),
                new SubImage(30, 177, 22, 30),
                new SubImage(61, 174, 20, 35),
                new SubImage(89, 177, 24, 30),
        };
        subImages[ATTACK_DOWN] = new SubImage[]{
                new SubImage(0, 90, 21, 23),
                new SubImage(30, 90, 22, 23),
                new SubImage(61, 85, 20, 32),
                new SubImage(91, 85, 20, 32),
                new SubImage(115, 87, 28, 29),
        };

        subImages[ATTACK_LEFT] = new SubImage[]{
                new SubImage(242, 90, 260 - 242, 23),
                new SubImage(268, 90, 294 - 268, 24),
                new SubImage(295, 91, 326 - 295, 21),
                new SubImage(327, 91, 355 - 327, 21),
        };

        subImages[ATTACK_RIGHT] = new SubImage[]{

                new SubImage(242, 180, 260 - 242, 23),
                new SubImage(268, 180, 294 - 268, 24),
                new SubImage(295, 181, 326 - 295, 21),
                new SubImage(327, 181, 355 - 327, 21)
        };

        subImages[SPIN_ATTACK] = new SubImage[]{
                new SubImage(115, 180, 32, 23), // Up
                new SubImage(359, 86, 382 - 359, 31), //LEFT,
                new SubImage(145, 88, 31, 27), // Down
                new SubImage(359, 176, 382 - 359, 31), // RIGHT
        };

                /*subImages[ATTACK_UP] = initAnimation(0, 6, 30, 30, 5);
                subImages[ATTACK_DOWN] = initAnimation(0, 3, 28, 28, 6);
                subImages[ATTACK_LEFT] = initAnimation(8, 3, 29, 30, 5);
                subImages[ATTACK_RIGHT] = initAnimation(8, 6, 29, 30, 5);*/

        stillImages[ATTACK_UP] = initAnimation(2, 0, 30, 30, 1)[0];
        stillImages[ATTACK_DOWN] = initAnimation(1, 0, 30, 30, 1)[0];
        stillImages[ATTACK_LEFT] = initAnimation(5, 0, 30, 30, 1)[0];
        stillImages[ATTACK_RIGHT] = initAnimation(11, 4, 30, 30, 1)[0];
        stillImages[SPIN_ATTACK] = stillImages[DOWN];
    }

    @Override
    public void tick() {
        getInput();
        move();
        game.getGameCamera().centerOnEntity(this);
    }

    private void getInput() {
        setxMove(0);
        setyMove(0);

        GameState state = (GameState) State.getState();
        TileMap tiles = state.getWorld().getTiles();
        if (game.getKeyManager().up) {
            if(tiles.canMove(this, UP)){
                setyMove(-speed);
                setPose(UP);
            }
        }
        if (game.getKeyManager().down) {
            if(tiles.canMove(this, DOWN)) {
                setyMove(speed);
                setPose(DOWN);
            }
        }
        if (game.getKeyManager().left) {
            if(tiles.canMove(this, LEFT)) {
                setxMove(-speed);
                setPose(LEFT);
            }
        }
        if (game.getKeyManager().right) {
            if(tiles.canMove(this, RIGHT)) {
                setxMove(speed);
                setPose(RIGHT);
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
