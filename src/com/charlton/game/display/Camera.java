package com.charlton.game.display;

import com.charlton.Game;
import com.charlton.game.models.SpriteSheetEntity;

public class Camera {

    private Game game;
    private float xOffset, yOffset;
    private float scale =  1;

    public Camera(Game game, float xOffset, float yOffset) {
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centerOnEntity(SpriteSheetEntity e) {
        xOffset = e.getX().floatValue() - game.getWidth() / 2 + e.getWidth().floatValue() / 2;
        yOffset = e.getY().floatValue() - game.getHeight() / 2 + e.getHeight().floatValue() / 2;
    }

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
