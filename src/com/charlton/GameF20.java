package com.charlton;

import com.charlton.models.BoundingCircle;
import com.charlton.models.BoundingLine;
import com.charlton.models.SpaceShip;
import com.charlton.models.Tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameF20 extends GameApplet {

    BoundingLine line;
    Tank tank = new Tank(200, 200, 90);
    Tank tank1 = new Tank(300, 300, 90);
    SpaceShip ship = new SpaceShip(400, 400);
    BoundingCircle[] c = new BoundingCircle[10];
    BoundingCircle p = new BoundingCircle(200, 200, 30, 90);
    Random rnd = new Random(System.currentTimeMillis());
    List<Tank> tanks = new ArrayList<Tank>() {
        {
            add(new Tank(400, 400, 90));
        }
    };

    @Override
    public void paint(Graphics g) {
        //int[] xp = {2, 10, 18, 42, 54, 46, 55, 32, 12};
        //int[] yp = {16, 25, 39, 40, 34, 17, 4, 7, 7};
        //drawPoly(xp, yp, g);
        for (int i = 0; i < c.length - 1; i++) {
            for (int j = i + 1; j < c.length; j++) {
                c[i].overlaps(c[j]);
                c[i].overlaps(line);
            }
        }

        for (int i = 0; i < c.length; i++) {
            if (i == 0) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            c[i].draw(g);
        }
        if (c[0].overlaps(line)) g.setColor(Color.RED);
        else g.setColor(Color.black);



        line.draw(g);
        //tank.draw(g);
        //tank1.draw(g);


    }


    public void init() {
        for (int i = 0; i < c.length; i++) {
            int j = 1;
            if( i == 0) j = 6;
            c[i] = new BoundingCircle((i+j) * 50 , i * 50 * j, (rnd.nextDouble() * 60) % 40, 90);
        }
        line = new BoundingLine(0, getHeight() - 200, getWidth(), getHeight() - 200);
        super.init();
    }

    @Override
    public void inGameLoop() {
        super.inGameLoop();
        /*
        if (pressing[UP]) c[0].moveForwardBy(7);
        if (pressing[DN]) c[0].moveBackwardBy(5);
        if (pressing[LT]) c[0].turnLeft(5);
        if (pressing[RT]) c[0].turnRight(5);
         */
        double multiplier = 1f;
        if(pressing[SPACE]) multiplier = 1.8f;
        if (pressing[UP]) c[0].moveForwardBy(7 * multiplier);
        if (pressing[DN]) c[0].moveBackwardBy(5);
        if (pressing[LT]) c[0].turnLeft(5);
        if (pressing[RT]) c[0].turnRight(5);

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
        }
    }

}
