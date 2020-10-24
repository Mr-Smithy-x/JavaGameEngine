package com.charlton.sprites;

import com.charlton.contracts.BoundingContract;
import com.charlton.contracts.BoundingContractLine;
import com.charlton.contracts.CollisionDetection;
import com.charlton.contracts.Movable;
import com.charlton.models.BoundingCircle;

import java.awt.*;
import java.io.IOException;

public class Zelda extends SpriteSheet implements CollisionDetection {

    boolean moving = false;
    private BoundingCircle circle;


    public Zelda() throws IOException {
        this(200, 200, 3);
    }

    public Zelda(int duration) throws IOException {
        this(200, 200, duration);
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
        this.circle = new BoundingCircle(position_x, position_y, 30, 90);
        this.duration = duration;
        this.subImages = new SubImage[4][];
        this.stillImages = new SubImage[4];
        initializeSprites();
        this.circle.bind(this);
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

    }


    @Override
    public void moveBy(double dx, double dy) {
        super.moveBy(dx, dy);
        circle.moveBy(dx, dy);
        if (dx < 0) {
            pose = LEFT;
        }
        if (dx > 0) {
            pose = RIGHT;
        }
        if (dy > 0) {
            pose = DOWN;
        }
        if (dy < 0) {
            pose = UP;
        }
        nextImageColumn();
        moving = true;
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
    public void draw(Graphics g) {
        Image image;
        if (moving) {
            image = getImage();
        } else {
            image = getStillImage();
        }
        g.drawImage(image, (int) position_x, (int) position_y, 3 * image.getWidth(null), 3 * image.getHeight(null), null);
        moving = false;
        //circle.draw(g);
    }

    @Override
    public void toss(double velocity_x, double velocity_y) {
        super.toss(velocity_x, velocity_y);
        if(velocity_x > 0){
            pose = RIGHT;
        }
        if(velocity_x < 0){
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
    public void bind(BoundingContract<Number> object) {
        circle.bind(this);
    }
}
