package com.charlton.game.models.base;

import com.charlton.game.contracts.*;

import java.awt.*;

public class BoundingBox extends AIObject implements Drawable {


    @Override
    public int getType() {
        return 0;
    }

    public BoundingBox(int x, int y, int w, int h) {
        this.position_x = x;
        this.position_y = y;
        this.width = w;
        this.height = h;
    }

    public boolean contains(int mx, int my) {
        return (mx > position_x) && (mx < position_x + width) &&
                (my > position_y) && (my < position_y + height);
    }

    @Override
    public void render(Graphics g) {

        g.drawRect((int) position_x, (int) position_y, (int) width, (int) height);
    }

    @Override
    public boolean overlaps(Gravitational box) {
        boolean collides = (box.getX().doubleValue() + box.getWidth().doubleValue() >= position_x) &&
                (position_x + width >= box.getX().doubleValue()) &&
                (box.getY().doubleValue() + box.getHeight().doubleValue() >= position_y) &&
                (position_y + height >= box.getY().doubleValue());

        if (collides) {
            pushes(box);
        }
        return collides;
    }

    @Override
    public boolean overlaps(BoundingContractLine line, boolean action) {
        double d = line.distanceTo(position_x, position_y).doubleValue();
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
        return ((width / 2 + height / 2) / 2);
    }

    public float getSpeed() {
        return (float) getCurrentSpeed();
    }
}
