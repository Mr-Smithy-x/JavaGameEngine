package com.charlton.sprites;

import com.charlton.contracts.BoundingContract;
import com.charlton.contracts.BoundingContractLine;
import com.charlton.contracts.CollisionDetection;
import com.charlton.contracts.Movable;
import com.charlton.models.BoundingCircle;
import com.charlton.models.MovableObject;
import com.charlton.models.SpriteSheet;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Zelda extends SpriteSheet implements CollisionDetection {

    boolean moving = false;
    boolean attacking = false;
    public BoundingCircle circle;
    private BoundingCircle gun;


    public Zelda() throws IOException {
        this(200, 200, 5);
    }

    public Zelda(int duration) throws IOException {
        this(200, 200, duration);
    }

    public void shoot(BoundingCircle[] circles) {
        gun.setWorld(position_x, position_y);
        gun.setWorldAngle(world_angle);
        gun.launch(circles);
    }

    @Override
    public void setVelocityX(Number velocity_x) {
        super.setVelocityX(velocity_x);
        circle.setVelocityX(velocity_x);
    }


    @Override
    public void setVelocityY(Number velocity_y) {
        super.setVelocityY(velocity_y);
        circle.setVelocityY(velocity_y);
    }

    public Zelda(int position_x, int position_y, int duration) throws IOException {
        super("link.png");
        this.position_x = position_x;
        this.position_y = position_y;
        this.circle = new BoundingCircle(position_x, position_y, 20, 90);
        this.duration = duration;
        this.subImages = new SubImage[16][];
        this.stillImages = new SubImage[16];
        initializeSprites();
        this.circle.bind(this);
        this.gun = new Bullet();
    }


    public void spin() {
        attacking = true;
        pose = SPIN_ATTACK;
        nextImageColumn();
    }


    public void hit(MovableObject obj) {
        double speed = 2000;
        //obj.setWorld(position_x, position_y);
        System.out.printf("COS SPEED: %s, SIN SPEED: %s\n", speed * cos_angle, speed * sin_angle);
        obj.setVelocity(speed * cos_angle, speed * sin_angle);
    }


    public void attack(ArrayList<MovableObject> objects) {
        this.attack();
        objects.stream().filter(o -> o.inVicinity(this, 80))
                .forEach(this::hit);

    }

    protected void attack() {
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
    public void moveBy(double dx, double dy) {
        moving = true;
        super.moveBy(dx, dy);
        circle.moveBy(dx, dy);
        circle.setWorldAngle(world_angle);
    }



    @Override
    public Movable setDrag(double drag_x, double drag_y) {
        return circle.setDrag(drag_x, drag_y);
    }

    @Override
    public Movable setVelocity(double velocity_x, double velocity_y) {
        return circle.setVelocity(velocity_x, velocity_y);
    }

    @Override
    public Movable setAcceleration(double accelerate_x, double accelerate_y) {
        return circle.setAcceleration(accelerate_x, accelerate_y);
    }


    @Override
    public void chase(MovableObject circle) {
        this.gravitate();
        this.circle.chase(circle);
        this.circle.align();
        double dx = this.circle.last_dx;
        double dy = this.circle.last_dy;
        this.moving = true;
        if(dx < dy){
            if (dy > 0) {
                pose = DOWN;
                System.out.println("DOWN");
            }else if (dy < 0) {
                pose = UP;
                System.out.println("UP");
            }
        }else if (dy < dx){
            if (dx < 0) {
                pose = LEFT;
                System.out.println("LEFT");
            }else if (dx > 0) {
                pose = RIGHT;
                System.out.println("RIGHT");
            }
        }
        switch (pose){
            case LEFT:
                world_angle = 180;
            case DOWN:
                world_angle = 90;
            case RIGHT:
                world_angle = 0;
            case UP:
                world_angle = 270;
        }
        cos_angle = cos[(int) world_angle];
        sin_angle = sin[(int) world_angle];
        nextImageColumn();
    }

    @Override
    public void draw(Graphics g) {
        Image image;
        if (moving || attacking) {
            image = getImage();
        } else {
            image = getStillImage();
        }

        //g.drawImage(image, ((int) position_x + (image.getWidth(null) / 2)), ((int) position_y + (image.getHeight(null)) / 2), 3 * image.getWidth(null), 3 * image.getHeight(null), null);
        int scale_computed_x = image.getWidth(null) * 2;
        int scale_computed_y = image.getHeight(null) * 2;

        int destination_x = (int) this.position_x;
        int destination_y = (int) this.position_y;
        int destination_x2 = (int) this.position_x;
        int destination_y2 = (int) this.position_y;

        //Center image
        destination_x -= (scale_computed_x / 2);
        destination_y -= (scale_computed_y / 2);
        destination_x2 += (scale_computed_x / 2);
        destination_y2 += (scale_computed_y / 2);


        int source_x = 0;
        int source_y = 0;
        int source_x2 = image.getWidth(null);
        int source_y2 = image.getHeight(null);
        //Aligns image to the bottom
        g.drawImage(image, destination_x, destination_y, destination_x2, destination_y2, source_x, source_y, source_x2, source_y2, null);
        moving = false;
        attacking = false;
        //circle.draw(g);
    }

    @Override
    public void toss(double velocity_x, double velocity_y) {
        super.toss(velocity_x, velocity_y);
        if (velocity_x > 0) {
            pose = RIGHT;
        }
        if (velocity_x < 0) {
            pose = LEFT;
        }
    }

    @Override
    public int getType() {
        return SpriteSheet.TYPE_POLY;
    }

    @Override
    public Number getWidth() {
        return getImage().getWidth(null) * 3;
    }

    @Override
    public Number getHeight() {
        return getImage().getHeight(null) * 3;
    }

    @Override
    public BoundingContract<Number> getBoundingObject() {
        return this.circle;
    }

    @Override
    public boolean overlaps(BoundingContract<Number> box) {
        boolean overlaps = circle.overlaps(box);
        circle.align();
        return overlaps;
    }

    @Override
    public boolean overlaps(BoundingContractLine line) {
        boolean overlaps = circle.overlaps(line);
        circle.align();
        return overlaps;
    }

    @Override
    public void pushes(BoundingContract<Number> contract) {
        circle.pushes(contract);
        circle.align();
    }

    @Override
    public void gravitate() {
        circle.gravitate();
        circle.align();
        nextImageColumn();
        moving = true;
    }

    @Override
    public void pushedBackBy(BoundingContractLine line) {
        circle.pushedBackBy(line);
        circle.align();
    }

    @Override
    public void setWorld(double x, double y) {
        super.setWorld(x, y);
        circle.setWorld(x, y);
        align();
    }

    @Override
    public void bind(BoundingContract<Number> object) {
        circle.bind(this);
    }
}
