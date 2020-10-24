package com.charlton;

import com.charlton.models.BoundingBox;
import com.charlton.models.BoundingCircle;
import com.charlton.models.BoundingLine;
import com.charlton.sprites.Zelda;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameF20 extends GameApplet {

    BoundingLine[] L = new BoundingLine[3];
    BoundingCircle p = new BoundingCircle(300, 100, 40, 90);
    BoundingCircle c = new BoundingCircle(500, 200, 40, 90);
    BoundingBox box = new BoundingBox(200, 400, 90, 90);
    Zelda z = new Zelda();
    public GameF20() throws IOException {}
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        p.draw(g);
        g.setColor(Color.BLACK);
        c.draw(g);
        box.draw(g);
        for (BoundingLine boundingLine : L) {
            boundingLine.draw(g);
        }
        z.draw(g);
    }

    public void init() {
        double gravity = 0.7;


        p.setAcceleration(0, gravity)
                .setVelocity(0, -10)
                .setDrag(0, 0.3);
        box.setAcceleration(0, gravity)
                .setVelocity(0, -10)
                .setDrag(0, 0.3);
        c.setAcceleration(0, gravity)
                .setVelocity(0, -10)
                .setDrag(0, 0.3);
        double[][] v = {
                {getWidth(), getHeight() - 200, 0, getHeight() - 200},
                {getWidth() - 100, 0, getWidth() - 100, getHeight()},
                {100, getHeight(), 100, 0},
        };
        for (int i = 0; i < v.length; i++) {
            L[i] = new BoundingLine(v[i][0], v[i][1], v[i][2], v[i][3]);
        }
        super.init();
    }

    @Override
    public void inGameLoop() {
        super.inGameLoop();
        p.gravitate();
        c.gravitate();
        box.gravitate();
        double multiplier = 1D;
        if (pressing[SPACE]) multiplier = 1.8D;
        if (pressing[UP]) {
            z.moveBy(0, multiplier* -5.0);
        }
        if (pressing[DN]) {
            z.moveBy(0, 5.0 * multiplier);
        }
        if (pressing[LT]) {
            z.moveBy(-5.0 * multiplier, 0);
        }
        if (pressing[RT]) {
            z.moveBy(5.0 * multiplier, 0);
        }

        for (BoundingLine boundingLine : L) {
            p.overlaps(boundingLine);
            c.overlaps(boundingLine);
            box.overlaps(boundingLine);
        }
        p.overlaps(c);
        //p.overlaps(box);
        //c.overlaps(p);
        //c.overlaps(box);
        box.overlaps(p);
        box.overlaps(c);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        mx = e.getX();
        my = e.getY();

        for (BoundingLine boundingLine : L) {
            boundingLine.grabbed(mx, my);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        for (BoundingLine boundingLine : L) {
            boundingLine.released();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        int nx = e.getX();
        int ny = e.getY();
        int dx = nx - mx;
        int dy = ny - my;
        mx = nx;
        my = ny;

        for (BoundingLine boundingLine : L) {
            boundingLine.draggedBy(dx, dy);
        }
    }
}
