package com.charlton.game.display;

import com.charlton.Game;
import com.charlton.game.contracts.Movable;

public class Camera extends GlobalCamera {

    private Game game;

    public Camera(Game game, float x_origin, float y_origin) {
        super(x_origin, y_origin);
        this.game = game;
        Camera.camera = this;
    }

    public void centerOnEntity(Movable e) {
        x_origin = e.getX().floatValue() - game.getWidth() / 2 + e.getWidth().floatValue() / 2;
        y_origin = e.getY().floatValue() - game.getHeight() / 2 + e.getHeight().floatValue() / 2;
    }

}
