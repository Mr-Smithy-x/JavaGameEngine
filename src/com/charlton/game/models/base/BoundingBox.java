package com.charlton.game.models.base;

import com.charlton.game.contracts.*;
import com.charlton.game.models.base.model2d.AIObject2D;
import com.charlton.game.models.base.model2d.contracts.Gravitational2D;

import java.awt.*;

public class BoundingBox extends AIObject2D implements Drawable {


    @Override
    public int getType() {
        return 0;
    }

    public BoundingBox(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public boolean contains(int mx, int my) {
        return (mx > x) && (mx < x + width) &&
                (my > y) && (my < y + height);
    }

    @Override
    public void render(Graphics g) {
        g.drawRect(getGlobalCameraOffsetX().intValue(), getGlobalCameraOffsetY().intValue(), (int) width, (int) height);
    }

    @Override
    public boolean overlaps(Gravitational2D box) {
        boolean collides = (box.getX().doubleValue() + box.getWidth().doubleValue() >= x) &&
                (x + width >= box.getX().doubleValue()) &&
                (box.getY().doubleValue() + box.getHeight().doubleValue() >= y) &&
                (y + height >= box.getY().doubleValue());

        if (collides) {
            pushes(box);
        }
        return collides;
    }

    @Override
    public boolean overlaps(BoundingContractLine line, boolean action) {
        double d = line.distanceTo(x, y).doubleValue();
        boolean overlaps = d < width;
        if (overlaps && action) {
            pushedBackBy(line);
            bounce();
        }
        return overlaps;
    }

    @Override
    public boolean overlaps(BoundingContractLine line) {
        return overlaps(line, false);
    }


    @Override
    public Number getWidth() {
        return width;
    }

    @Override
    public Number getHeight() {
        return height;
    }

    @Override
    public Number getRadius() {
        return ((width / 2 + height / 2));
    }

    public float getSpeed() {
        return (float) getCurrentSpeed();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
