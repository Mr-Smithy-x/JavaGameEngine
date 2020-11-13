package com.charlton.game.models.sprites;

import com.charlton.game.contracts.MovableCollision;
import com.charlton.game.gfx.SubImage;
import com.charlton.game.models.base.BoundingCircle;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.contracts.Animal;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Link extends SpriteSheet {

    boolean attacking = false;
    boolean dogAttack = false;

    public Link() throws IOException {
        this(200, 200, 5);
    }

    public Link(int duration) throws IOException {
        this(200, 200, duration);
    }

    public void shoot(BoundingCircle[] circles) {
        //gun.setWorld(position_x, position_y);
        //gun.setWorldAngle(world_angle);
        //gun.launch(circles);
    }


    public Link(int position_x, int position_y, int duration) throws IOException {
        super("link.png");
        this.duration = duration;
        this.subImages = new SubImage[16][];
        this.stillImages = new SubImage[16];
        this.circle = new BoundingCircle(position_x, position_y, 20, 90);
        this.circle.setWorld(position_x, position_y);
        this.circle.bind(this);
        initializeSprites();
    }

    public Link(int position_x, int position_y, int duration, int scale) throws IOException {
        super("link.png");
        this.duration = duration;
        this.subImages = new SubImage[16][];
        this.stillImages = new SubImage[16];
        this.circle = new BoundingCircle(position_x, position_y, 20, 90);
        this.circle.setWorld(position_x, position_y);
        this.circle.bind(this);
        initializeSprites();
    }

    public void spin() {
        attacking = true;
        pose = SPIN_ATTACK;
        nextImageColumn();
    }


    public void attackDog(Animal animal, MovableCollision collision) {
        if (inVicinity(collision, 400)) {
            if (dogAttack) {
                animal.getSpriteSheet().chase(collision);
                double distance = animal.getSpriteSheet().distanceTo(collision);
                if (Math.abs(distance) < 3) {
                    hit(collision);
                    dogAttack = false;
                }
            }
        }
    }

    public void hit(MovableCollision obj) {
        double speed = 2000;
        System.out.printf("COS SPEED: %s, SIN SPEED: %s\n", speed * getCosAngle(), speed * getSinAngle());
        obj.setVelocity(speed * getCosAngle(), speed * getSinAngle());
    }

    public void attack(ArrayList<MovableCollision> objects) {
        this.attack();
        objects.stream()
                .filter(o -> o.inVicinity(this, 80))
                .forEach(this::hit);
    }

    public void attack() {
        attacking = true;
        switch (pose) {
            case UP:
                pose = ATTACK_UP;
                break;
            case LEFT:
                pose = ATTACK_LEFT;
                break;
            case RIGHT:
                pose = ATTACK_RIGHT;
                break;
            case DOWN:
            case SPIN_ATTACK:
                pose = ATTACK_DOWN;
                break;
        }
        nextImageColumn();
    }


    protected void initializeSprites() {
        subImages[UP] = initAnimation(0, 4, 30, 30, 8);
        subImages[DOWN] = initAnimation(0, 1, 30, 30, 8);
        subImages[LEFT] = initAnimation(8, 1, 30, 30, 6);
        subImages[RIGHT] = initAnimation(8, 4, 30, 30, 6);
        stillImages[UP] = initAnimation(2, 0, 30, 30, 1)[0];
        stillImages[DOWN] = initAnimation(1, 0, 30, 30, 1)[0];
        stillImages[LEFT] = initAnimation(5, 0, 30, 30, 1)[0];
        stillImages[RIGHT] = initAnimation(11, 4, 30, 30, 1)[0];


        subImages[ATTACK_UP] = new SubImage[]{
                new SubImage(0, 180, 22, 25),
                new SubImage(30, 177, 22, 30),
                new SubImage(61, 174, 20, 35),
                new SubImage(89, 177, 24, 30),
        };
        subImages[ATTACK_DOWN] = new SubImage[]{
                new SubImage(0, 90, 21, 23),
                new SubImage(30, 90, 22, 23),
                new SubImage(61, 85, 20, 32),
                new SubImage(91, 85, 20, 32),
                new SubImage(115, 87, 28, 29),
        };

        subImages[ATTACK_LEFT] = new SubImage[]{
                new SubImage(242, 90, 260 - 242, 23),
                new SubImage(268, 90, 294 - 268, 24),
                new SubImage(295, 91, 326 - 295, 21),
                new SubImage(327, 91, 355 - 327, 21),
        };

        subImages[ATTACK_RIGHT] = new SubImage[]{

                new SubImage(242, 180, 260 - 242, 23),
                new SubImage(268, 180, 294 - 268, 24),
                new SubImage(295, 181, 326 - 295, 21),
                new SubImage(327, 181, 355 - 327, 21)
        };

        subImages[SPIN_ATTACK] = new SubImage[]{
                new SubImage(115, 180, 32, 23), // Up
                new SubImage(359, 86, 382 - 359, 31), //LEFT,
                new SubImage(145, 88, 31, 27), // Down
                new SubImage(359, 176, 382 - 359, 31), // RIGHT
        };

                /*subImages[ATTACK_UP] = initAnimation(0, 6, 30, 30, 5);
                subImages[ATTACK_DOWN] = initAnimation(0, 3, 28, 28, 6);
                subImages[ATTACK_LEFT] = initAnimation(8, 3, 29, 30, 5);
                subImages[ATTACK_RIGHT] = initAnimation(8, 6, 29, 30, 5);*/

        stillImages[ATTACK_UP] = initAnimation(2, 0, 30, 30, 1)[0];
        stillImages[ATTACK_DOWN] = initAnimation(1, 0, 30, 30, 1)[0];
        stillImages[ATTACK_LEFT] = initAnimation(5, 0, 30, 30, 1)[0];
        stillImages[ATTACK_RIGHT] = initAnimation(11, 4, 30, 30, 1)[0];
        stillImages[SPIN_ATTACK] = stillImages[DOWN];
    }

    @Override
    public void draw(Graphics g) {
        if (attacking) {
            moving = attacking;
        }
        super.draw(g);
        attacking = false;
    }

    @Override
    public void bind(MovableCollision object) {
        circle.bind(this);
    }

    public void sendAttackDog() {
        dogAttack = true;
    }
}
