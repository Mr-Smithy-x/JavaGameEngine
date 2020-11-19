package com.charlton.game.display;

import com.charlton.Game;
import com.charlton.game.contracts.Movable;

public class Camera extends GlobalCamera {

    private Game game;

    public Camera(Game game, int x_origin, int y_origin) {
        super(x_origin, y_origin);
        this.game = game;
        Camera.camera = this;
    }

    public void centerOnEntity(Movable e) {
        x_origin = e.getX().intValue() - game.getWidth() / 4 + e.getWidth().intValue() / 2;
        y_origin = e.getY().intValue() - game.getHeight() / 4 + e.getHeight().intValue() / 2;
    }

}
