package com.charlton.game.contracts;

import java.awt.*;

public interface Drawable {
    void draw(Graphics g);

    default void drawRelativeToCamera(Graphics g) {
        draw(g);
    }
}
