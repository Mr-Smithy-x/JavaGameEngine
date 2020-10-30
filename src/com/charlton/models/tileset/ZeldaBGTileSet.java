package com.charlton.models.tileset;

import com.charlton.contracts.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ZeldaBGTileSet extends BaseTileSet implements Drawable {

    public static BufferedImage tile;
    private Component frame;

    public static final Point[] points = new Point[]{
            Point.create(1, 1, 17, 17),
            Point.create(19, 1, 19 + 16, 17),
    };

    public ZeldaBGTileSet(Component frame) throws IOException {
        this.frame = frame;
        if (tile == null) {
            tile = super.initializeTileSet("bg_kit_3.png");
        }
    }

    public Image getTile() {
        Point point = points[0];
        return tile.getSubimage(point.getPointX(), point.getPointY(), point.getWidth(), point.getHeight());
    }

    @Override
    public void draw(Graphics g) {
        Image tile = getTile();
        int tw = tile.getWidth(null);
        int th = tile.getHeight(null);
        int w = frame.getWidth();
        int h = frame.getHeight();
        int columns = w / th;
        int rows = h / th;
        if (w % tw > 0) {
            columns += 1;
        }
        if (h % th > 0) {
            rows += 1;
        }
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                g.drawImage(tile, column * tw, row * th, tw, th, null);
            }
        }
    }
}
