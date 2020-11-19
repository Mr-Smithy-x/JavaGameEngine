package com.charlton.game.models.sprites;

import com.charlton.game.contracts.AI;
import com.charlton.game.display.Camera;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.SubImage;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.base.BoundingBox;
import com.charlton.game.models.base.BoundingCircle;
import com.charlton.game.models.contracts.Animal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

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
        this.circle = new BoundingBox(position_x, position_y, 64, 64);
        this.circle.setWorld(position_x, position_y);
    }

    public void spin() {
        attacking = true;
        pose = Pose.SPIN_ATTACK;
        nextImageColumn();
    }


    public void attackDog(Animal animal, SpriteSheet collision) {
        if (inVicinity(collision, 400)) {
            if (dogAttack) {
                //animal.getSpriteSheet().chase(collision);
                double distance = animal.getSpriteSheet().distanceTo(collision).doubleValue();
                if (Math.abs(distance) < 3) {
                    hit(collision);
                    dogAttack = false;
                }
            }
        }
    }

    public void hit(SpriteSheet obj) {
        double speed = 2000;
        double cosAngle = speed * getCosAngle();
        double sinAngle = speed * getSinAngle();

        switch (getPose()) {
            case ATTACK_UP:
                sinAngle *= 1;
                cosAngle = 0;
                break;
            case ATTACK_DOWN:
                sinAngle *= -1;
                cosAngle = 0;
                break;
            case ATTACK_LEFT:
                sinAngle = 0;
                cosAngle *= -1;
                break;
            case ATTACK_RIGHT:
                sinAngle = 0;
                cosAngle *= 1;
                break;
        }

        System.out.printf("COS SPEED: %s, SIN SPEED: %s\n", cosAngle, sinAngle);
        obj.setVelocity(cosAngle, sinAngle);
    }

    public void attack(ArrayList<SpriteSheet> objects) {
        this.attack();
        objects.stream()
                .filter(o -> o.inVicinity(this, 80))
                .forEach(this::hit);
    }

    public void attack() {
        attacking = true;
        switch (pose) {
            case UP:
                pose = Pose.ATTACK_UP;
                break;
            case LEFT:
                pose = Pose.ATTACK_LEFT;
                break;
            case RIGHT:
                pose = Pose.ATTACK_RIGHT;
                break;
            case DOWN:
            case SPIN_ATTACK:
                pose = Pose.ATTACK_DOWN;
                break;
        }
        nextImageColumn();
    }


    @Override
    protected Map<Pose, List<SubImage>> initializeSheet(BufferedImage spriteSheet) {
        Map<Pose, List<SubImage>> subImages = new HashMap<>();
        subImages.put(Pose.UP, initAnimation(0, 4, 30, 30, 8));
        subImages.put(Pose.DOWN, initAnimation(0, 1, 30, 30, 8));
        subImages.put(Pose.LEFT, initAnimation(8, 1, 30, 30, 6));
        subImages.put(Pose.RIGHT, initAnimation(8, 4, 30, 30, 6));
        subImages.get(Pose.UP).add(0, initAnimation(2, 0, 30, 30, 1).get(0));
        subImages.get(Pose.DOWN).add(0, initAnimation(1, 0, 30, 30, 1).get(0));
        subImages.get(Pose.LEFT).add(0, initAnimation(5, 0, 30, 30, 1).get(0));
        subImages.get(Pose.RIGHT).add(0, initAnimation(11, 4, 30, 30, 1).get(0));


        subImages.put(Pose.ATTACK_UP, Arrays.asList(new SubImage(0, 180, 22, 25),
                initAnimation(2, 0, 30, 30, 1).get(0),
                new SubImage(30, 177, 22, 30),
                new SubImage(61, 174, 20, 35),
                new SubImage(89, 177, 24, 30)
        ));
        subImages.put(Pose.ATTACK_DOWN, Arrays.asList(
                initAnimation(1, 0, 30, 30, 1).get(0),
                new SubImage(0, 90, 21, 23),
                new SubImage(30, 90, 22, 23),
                new SubImage(61, 85, 20, 32),
                new SubImage(91, 85, 20, 32),
                new SubImage(115, 87, 28, 29)
        ));

        subImages.put(Pose.ATTACK_LEFT, Arrays.asList(
                initAnimation(5, 0, 30, 30, 1).get(0),
                new SubImage(242, 90, 260 - 242, 23),
                new SubImage(268, 90, 294 - 268, 24),
                new SubImage(295, 91, 326 - 295, 21),
                new SubImage(327, 91, 355 - 327, 21)
        ));

        subImages.put(Pose.ATTACK_RIGHT, Arrays.asList(
                initAnimation(11, 4, 30, 30, 1).get(0),
                new SubImage(242, 180, 260 - 242, 23),
                new SubImage(268, 180, 294 - 268, 24),
                new SubImage(295, 181, 326 - 295, 21),
                new SubImage(327, 181, 355 - 327, 21)
        ));

        subImages.put(Pose.SPIN_ATTACK, Arrays.asList(
                subImages.get(Pose.DOWN).get(0),
                new SubImage(115, 180, 32, 23), // Up
                new SubImage(359, 86, 382 - 359, 31), //LEFT,
                new SubImage(145, 88, 31, 27), // Down
                new SubImage(359, 176, 382 - 359, 31) // RIGHT
        ));
        return subImages;
    }

    @Override
    public void render(Graphics g) {
        if (attacking) {
            moving = attacking;
        }
        super.render(g);
        attacking = false;

        String string = "I'm working on final project which is a game.";
        int width = g.getFontMetrics().stringWidth(string);
        g.drawString(string, (int) (getX().intValue() - GlobalCamera.getInstance().getX()) - width / 2, (int) (getY().intValue() - GlobalCamera.getInstance().getY()) - getHeight().intValue() / 2);
    }

    public void sendAttackDog() {
        dogAttack = true;
    }

    @Override
    public Number getCurrentSpeed() {
        return getSpeed();
    }

    public float getSpeed() {
        return Camera.getInstance().getScaling() * 4;
    }
}
