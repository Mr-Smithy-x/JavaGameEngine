package com.charlton;

import com.charlton.contracts.CollisionDetection;
import com.charlton.contracts.Drawable;
import com.charlton.models.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameF20 extends GameApplet {

    BoundingLine line, wall;
    Tank tank = new Tank(200, 200, 90);
    SpaceShip ship = new SpaceShip(400, 400);
    BoundingCircle[] c = new BoundingCircle[1];
    BoundingCircle p = new BoundingCircle(100, 400, 40, 90);
    Random rnd = new Random(System.currentTimeMillis());
    List<MovableObject> tanks = new ArrayList<MovableObject>() {
        {
            add(new Tank(600, 400, 0));
            add(new BoundingCircle(200, 300, 20, 90));
            add(new BoundingCircle(300, 300, 20, 90));
            add(new BoundingCircle(400, 300, 20, 90));
            add(new BoundingCircle(500, 300, 20, 90));
        }
    };

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < c.length; i++) {
            if (i == 0) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            c[i].draw(g);
            g.setColor(Color.BLACK);
        }

        tanks.forEach(obj -> ((Drawable)obj).draw(g));
        p.draw(g);
        line.draw(g);
        wall.draw(g);
    }

    public void init() {
        double gravity = 0.7;
        for (int i = 0; i < c.length; i++) {
            double radius;
            double x;
            double y;
            if(i == 0){
                radius = 30;
                x = 100;
                y = 100;
            }else{
                x = i * 50;
                y = i * 50;
                radius = ((rnd.nextDouble() * 60) % 40);
            }
            c[i] = new BoundingCircle(x , y, radius, 0);
            c[i].setAcceleration(0, gravity)
                    .setVelocity(0, -10)
                    .setDrag(1, 0.3);

        }
        tanks.forEach(obj -> {
            obj.setVelocity(0, -8)
                    .setAcceleration(0, gravity)
                    .setDrag(1, 0.3);
        });
        p.setAcceleration(0, gravity)
                .setVelocity(0, -10)
                .setDrag(1, 0.3);
        line = new BoundingLine(0, getHeight() - 200, getWidth(), getHeight() - 200);
        wall = new BoundingLine(getWidth() - 100, 0, getWidth() - 200, getHeight() - 200);
        super.init();
    }

    @Override
    public void inGameLoop() {
        super.inGameLoop();


        tanks.forEach(obj -> {
            if(((CollisionDetection) obj).overlaps(line)){
                ((CollisionDetection)obj).pushedBackBy(line);
            }
            obj.move();
        });

        c[0].move();
        p.move();
        double multiplier = 1D;
        if(pressing[SPACE]) multiplier = 1.8D;
        if (pressing[UP]) c[0].jump(8 * multiplier);
        if (pressing[DN]) c[0].moveBackwardBy(5);
        if (pressing[LT]) c[0].toss(-8, -10);
        if (pressing[RT]) c[0].toss(8, -10);

        if(c[0].overlaps(line)) {
            c[0].pushedBackBy(line);
        }
        if(c[0].overlaps(p)) {
            c[0].pushes(p);
        }
        if(p.overlaps(c[0])){
            p.pushes(c[0]);
        }
        if(p.overlaps(line)){
            p.pushedBackBy(line);
        }

        /*
        if (pressing[UP]) c[0].moveForwardBy(7);
        if (pressing[DN]) c[0].moveBackwardBy(5);
        if (pressing[LT]) c[0].turnLeft(5);
        if (pressing[RT]) c[0].turnRight(5);

        //if (pressing[UP]) c[0].moveForwardBy(7);
        //if (pressing[DN]) c[0].moveBackwardBy(5);
        //if (pressing[LT]) c[0].turnLeft(5);
        //if (pressing[RT]) c[0].turnRight(5);
        //p.overlaps(tank1);
        //p.overlaps(line);
        //tank1.overlaps(p);
        //tank1.overlaps(line);




        for (int i = 1; i < c.length; i++) {
            c[i].moveForwardBy(rnd.nextInt(15));
            boolean turn = rnd.nextBoolean();
            if (turn) {
                c[i].turnLeft(rnd.nextInt(10));
            }
        }*/
    }

}
