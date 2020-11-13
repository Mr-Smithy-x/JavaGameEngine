package com.charlton.game.models;

import com.charlton.Game;

import java.io.IOException;

public abstract class Creature extends SpriteSheetEntity {

    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 1.5f;
    public static final int DEFAULT_CREATURE_WIDTH = 64,
            DEFAULT_CREATURE_HEIGHT = 64;

    protected int health;
    protected float speed;

    public Creature(Game game, String name) throws IOException {
        super(game, name);
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
    }

    public void move() {
        setWorld(getX().floatValue() + getxMove(), getY().floatValue() + getyMove());
    }

    //GETTERS SETTERS



    public float getxMove() {
        return getVelocityX().floatValue();
    }

    public void setxMove(float xMove) {
        setVelocityX(xMove);
    }

    public float getyMove() {
        return getVelocityY().floatValue();
    }

    public void setyMove(float yMove) {
        setVelocityY(yMove);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}