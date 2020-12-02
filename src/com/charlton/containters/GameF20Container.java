package com.charlton.containters;

import com.charlton.base.GameHolder;
import com.charlton.game.contracts.Drawable;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.base.BoundingLine;
import com.charlton.game.models.sprites.Dog;
import com.charlton.game.models.sprites.Link;
import com.charlton.game.models.tileset.ZeldaBGTileSet;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class GameF20Container extends GameHolder {


    private ZeldaBGTileSet zeldaTitles;
    ArrayList<SpriteSheet> objectList = new ArrayList<SpriteSheet>() {
        {

            add(new Link(700, 700, 5));
            //add(new Dog(300, 300, 5));
            //add(new BadBoundingCircle(500, 200, 40, 90));
        }
    };
    BoundingLine[] L = new BoundingLine[3];
    Link link = new Link(300, 300, 5);
    Dog dog = new Dog(200, 200, 5) {
        {
            this.setTurnSpeed(5);
            this.setChaseSpeed(5);
        }
    };

    @Override
    protected void onPaint(Graphics g) {
        zeldaTitles.render(g);
        g.setColor(Color.BLACK);
        for (BoundingLine boundingLine : L) {
            boundingLine.render(g);
        }
        objectList.forEach(obj -> ((Drawable) obj).render(g));
        link.render(g);
        dog.render(g);
    }

    @Override
    protected void onInitialize() {
        try {
            zeldaTitles = new ZeldaBGTileSet(getContainer());
            double gravity = 0.7;
            link.setAcceleration(0, 0);
            link.setVelocity(0, 0);
            link.setDrag(0.01, 0.01);
            objectList.forEach(obj -> {
                obj.setAcceleration(0, 1);
                obj.setVelocity(0, 0);
                obj.setDrag(0.01, 0.01);
            });
            double[][] v = {
                    {getWidth(), getHeight() - 200, 0, getHeight() - 200},
                    {getWidth() - 100, 0, getWidth() - 100, getHeight()},
                    {100, getHeight(), 100, 0},
            };
            for (int i = 0; i < v.length; i++) {
                L[i] = new BoundingLine(v[i][0], v[i][1], v[i][2], v[i][3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPlay() {
        double multiplier = 1D;
        if (pressing[_F]) {
            objectList.forEach(o -> o.setWorld(link.getX().doubleValue(), link.getY().doubleValue()));
        }

        if (pressing[_D]) {
            link.sendAttackDog();
        } else if (pressing[SPACE]) {
            link.attack(objectList);
        } else if (pressing[_Z]) {
            link.spin();
        } else {
            if (pressing[UP]) {
                link.moveBy(0, multiplier * -5.0);
            }
            if (pressing[DN]) {
                link.moveBy(0, 5.0 * multiplier);
            }
            if (pressing[LT]) {
                link.moveBy(-5.0 * multiplier, 0);
                //z.toss(-5, 0);
            }
            if (pressing[RT]) {
                link.moveBy(5.0 * multiplier, 0);
                //z.toss(5, 0);
            }
        }

        dog.gravitate();
        objectList.forEach(obj -> {
            obj.gravitate();

            link.attackDog(dog, obj);
            //z.overlaps(obj);
            if (!obj.inVicinity(link, 80)) {
                obj.chase(link, 4);
            }
            dog.overlaps(obj);
            //z.overlaps(obj);
            for (BoundingLine boundingLine : L) {
                link.overlaps(boundingLine);
                dog.overlaps(boundingLine);
                obj.overlaps(boundingLine);
            }

        });


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


    protected GameF20Container(JFrame container, Canvas canvas) throws IOException {
        super(container, canvas);
    }

    protected GameF20Container(Applet applet) throws IOException {
        super(applet);
    }

    public static GameHolder holder(int width, int height) throws IOException {
        JFrame frame = frame(width, height);
        Canvas canvas = canvas(width, height);
        frame.add(canvas);
        frame.pack();
        return new GameF20Container(frame, canvas);
    }

    public static GameHolder applet(Applet applet) throws IOException {
        return new GameF20Container(applet);
    }


}
