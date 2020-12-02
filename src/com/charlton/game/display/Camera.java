package com.charlton.game.display;

import com.charlton.Game;
import com.charlton.game.models.base.model2d.contracts.Movable2D;

public class Camera extends GlobalCamera {

    private Game game;

    public Camera(Game game, int x_origin, int y_origin) {
        super(x_origin, y_origin);
        this.game = game;
        Camera.camera = this;
    }

    public void centerOnEntity(Movable2D e) {
        xOrigin = e.getX().intValue() - game.getWidth() / 4 + e.getWidth().intValue() / 2;
        yOrigin = e.getY().intValue() - game.getHeight() / 4 + e.getHeight().intValue() / 2;
    }

}
