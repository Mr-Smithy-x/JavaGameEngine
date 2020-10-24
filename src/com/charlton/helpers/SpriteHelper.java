package com.charlton.helpers;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteHelper {




    public static Image test(BufferedImage spriteSheet, int x, int y) {
        Image desired = spriteSheet.getSubimage(x, y, 30 * 3, 30 * 3);
        return desired;

    }
}
