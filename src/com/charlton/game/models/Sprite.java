package com.charlton.game.models;

import com.charlton.game.contracts.Drawable;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.models.base.model2d.MovableObject2D;
import com.charlton.game.models.sprites.Animation;

import java.awt.*;

public class Sprite extends MovableObject2D implements Drawable {

    private int action = 0;
    private boolean moving = false;
    private Animation[] anim;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int JUMP = 4;



    @Override
    public void render(Graphics g) {
        if(moving)
            g.drawImage(anim[action].getCurrentImage(), (int) x - GlobalCamera.getInstance().getX(), (int) y, null);
        else
            g.drawImage(anim[action].getStillImage(), (int) x - GlobalCamera.getInstance().getX(), (int) y, null);
        moving = false;
    }


    public Sprite(int x, int y, String[] poses, int count, int duration)
    {
        this.x = x;
        this.y = y;
        anim = new Animation[poses.length];
        for(int i = 0; i < poses.length; i++)
            anim[i] = new Animation(poses[i] + "_" + i, count, duration);
    }


    public void moveUp(int dist)
    {
        y -= dist;
        action = UP;
        moving = true;
        world_angle = 0;
    }

    public void moveDown(int dist)
    {
        y += dist;
        action = DOWN;
        moving = true;
        world_angle = 180;
    }

    public void moveLeft(int dist)
    {
        x -= dist;
        action = LEFT;
        moving = true;
        world_angle = 270;
    }

    public void moveRight(int dist)
    {
        x += dist;
        action = RIGHT;
        moving = true;
        world_angle = 90;
    }

    @Override
    public int getType() {
        return 0;
    }

}
