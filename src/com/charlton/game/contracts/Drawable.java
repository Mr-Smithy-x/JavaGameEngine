package com.charlton.game.contracts;

import java.awt.*;

public interface Drawable {
    void draw(Graphics g);
    void drawRelativeToCamera(Graphics g);
}
