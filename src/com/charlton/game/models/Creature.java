package com.charlton.game.models;

import com.charlton.Game;

import java.io.IOException;

public abstract class Creature extends SpriteSheet {

    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 1.5f;
    public static final int DEFAULT_CREATURE_WIDTH = 64,
            DEFAULT_CREATURE_HEIGHT = 64;

    protected int health;
    protected float speed;
    protected Game game;

    private int dimension;
    protected void setForceDimension(int dimension) {
        this.dimension = dimension;
    }

    public Creature(Game game, String name) throws IOException {
        super(name);
        this.game = game;
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
    }

    public void move() {
        moveBy(getVelocityX(), getVelocityY());
    }

    //GETTERS SETTERS


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


    public abstract void tick();
}