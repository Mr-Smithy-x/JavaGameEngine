package com.charlton.game.models;

import com.charlton.game.contracts.*;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.SubImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class SpriteSheet
        implements Drawable,
        BoundingContract<Number>,
        MovableCollision {


    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int JUMP = 4;
    public static final int ATTACK_UP = 5;
    public static final int ATTACK_DOWN = 6;
    public static final int ATTACK_LEFT = 7;
    public static final int ATTACK_RIGHT = 8;
    public static final int SPIN_ATTACK = 9;

    protected boolean moving = false;
    protected BufferedImage spriteSheet;
    protected SubImage[][] subImages = new SubImage[16][];
    protected SubImage[] stillImages = new SubImage[16];
    protected File file;
    protected int delay = 0;
    protected int duration = 10;
    protected int current_column = 0;
    protected int pose = 0;
    protected MovableCollision circle;


    public SpriteSheet(String name) throws IOException {
        initializeSheet(name);
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
    public Movable setDrag(double drag_x, double drag_y) {
        return circle.setDrag(drag_x, drag_y);
    }

    @Override
    public void setWorldAngle(double world_angle) {
        circle.setWorldAngle(world_angle);
    }

    @Override
    public double getWorldAngle() {
        return circle.getWorldAngle();
    }

    @Override
    public double getSinAngle() {
        return circle.getSinAngle();
    }

    @Override
    public double getCosAngle() {
        return circle.getCosAngle();
    }


    @Override
    public void moveBy(double dx, double dy) {
        moving = true;
        circle.moveBy(dx, dy);
        if (dx < dy) {
            if (dy > 0) {
                pose = DOWN;
            }
            if (dy < 0) {
                pose = UP;
            }
            if (dx < 0) {
                pose = LEFT;
            }
            if (dx > 0) {
                pose = RIGHT;
            }
        }
        else if (dy < dx) {
            if (dy > 0) {
                pose = DOWN;
            }
            if (dy < 0) {
                pose = UP;
            }
            if (dx < 0) {
                pose = LEFT;
            }
            if (dx > 0) {
                pose = RIGHT;
            }
        }
        switch (pose) {
            case LEFT:
                circle.setWorldAngle(180);
                break;
            case DOWN:
                circle.setWorldAngle(90);
                break;
            case RIGHT:
                circle.setWorldAngle(0);
                break;
            case UP:
                circle.setWorldAngle(270);
                break;
        }
        nextImageColumn();
    }

    @Override
    public void moveForwardBy(double dA) {
        double dx = (dA * circle.getCosAngle());
        double dy = (dA * circle.getSinAngle());
        this.moveBy(dx, dy);
    }

    @Override
    public void moveBackwardBy(double dA) {
        moveForwardBy(-dA);
    }

    @Deprecated
    public void initEvenly(int rows, int columns, int width, int height) {
        for (int i_row = 0; i_row < rows; i_row++) {
            int roster_height = spriteSheet.getHeight();
            int real_height = height;
            for (int j_column = 0; j_column < columns; j_column++) {
                int roster_width = spriteSheet.getWidth();
                int real_width = width;
                //BufferedImage subimage = spriteSheet.getSubimage(j_column * width, i_row * height, width, height);
                //subimages[i_row][j_column] = new SubImage(subimage);
            }
        }
    }

    protected SubImage[] initAnimation(int column, int row, int width, int height, int size) {
        SubImage[] images = new SubImage[size];
        for (int i = 0; i < size; i++) {
            images[i] = new SubImage((column * width) + (i * width), row * height, width, height);
        }
        return images;
    }


    protected void validate() {
        if (current_column >= subImages[pose].length) {
            current_column = 0;
        }
        if (current_column < 0) {
            current_column = subImages[pose].length - 1;
        }
    }

    protected void nextImageColumn() {
        if (delay == 0) {
            current_column++;
            validate();
            delay = duration;
        }
        delay--;
    }

    protected void prevImageColumn() {
        if (delay == 0) {
            current_column--;
            validate();
            delay = duration;
        }
        delay--;
    }

    public Image getImage() {
        validate();
        SubImage[] subImage = subImages[pose];
        if (subImage.length <= current_column) {
            current_column = 0;
        }
        SubImage sub = subImage[current_column];
        sub.setImage(spriteSheet);
        return sub.getImage();
    }

    public Image getStillImage() {
        SubImage stillImage = stillImages[pose];
        stillImage.setImage(spriteSheet);
        return stillImage.getImage();//spriteSheet.getSubimage(stillImage.spritePositionStartX, stillImage.spritePositionStartY, stillImage.width, stillImage.height);
    }

    protected void initializeSheet(String filename) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        file = new File(cl.getResource("res/" + filename).getFile());
        spriteSheet = ImageIO.read(file);
    }

    @Override
    public void render(Graphics g) {
        Image image;
        if (moving) {
            image = getImage();
        } else {
            image = getStillImage();
        }
        g.drawImage(image,
                (int) (getX().floatValue() - (image.getWidth(null) / 2) - GlobalCamera.getInstance().getX()),
                (int) (getY().floatValue() - (image.getHeight(null) / 2) - GlobalCamera.getInstance().getY()),
                image.getWidth(null),
                image.getHeight(null),
                null
        );
        moving = false;
        if(GlobalCamera.DEBUG) {
            ((Drawable) circle).render(g);
        }
    }

    public void setPose(int pose) {
        moving = false;
        this.pose = pose;

        switch (pose) {
            case LEFT:
                circle.setWorldAngle(180);
                break;
            case DOWN:
                circle.setWorldAngle(90);
                break;
            case RIGHT:
                circle.setWorldAngle(0);
                break;
            case UP:
                circle.setWorldAngle(270);
                break;
        }
        nextImageColumn();
    }

    public int getPose() {
        return pose;
    }

    @Override
    public int getType() {
        return SpriteSheet.TYPE_POLY;
    }

    @Override
    public Number getX() {
        return circle.getX();
    }

    @Override
    public Number getY() {
        return circle.getY();
    }

    @Override
    public void setX(Number x) {
        circle.setX(x);
    }

    @Override
    public void setY(Number y) {
        circle.setY(y);
    }

    @Override
    public Number getWidth() {
        return getImage().getWidth(null);
    }

    @Override
    public Number getHeight() {
        return getImage().getHeight(null);
    }

    @Override
    public Number getRadius() {
        return circle.getRadius();
    }

    @Override
    public Number getVelocityX() {
        return circle.getVelocityX();
    }

    @Override
    public Number getVelocityY() {
        return circle.getVelocityY();
    }

    @Override
    public void setVelocityX(Number velocity_x) {
        circle.setVelocityX(velocity_x);
    }

    @Override
    public void setVelocityY(Number velocity_y) {
        circle.setVelocityY(velocity_y);
    }

    @Override
    public Number getAccelerationX() {
        return circle.getAccelerationX();
    }

    @Override
    public Number getAccelerationY() {
        return circle.getAccelerationY();
    }

    @Override
    public void setAccelerationX(Number acceleration_x) {
        this.circle.setAccelerationX(acceleration_x);
    }

    @Override
    public void setAccelerationY(Number acceleration_y) {
        this.circle.setAccelerationY(acceleration_y);
    }

    @Override
    public void chase(Movable movable) {
        circle.chase(movable);
    }

    @Override
    public Movable setChaseSpeed(int speed) {
        return circle.setChaseSpeed(speed);
    }

    @Override
    public Movable setTurnSpeed(int turnspeed) {
        return circle.setTurnSpeed(turnspeed);
    }

    @Override
    public void turnToward(Movable circle) {
        this.circle.turnToward(circle);
    }

    @Override
    public boolean toTheLeftOf(Movable c) {
        return this.circle.toTheLeftOf(c);
    }

    @Override
    public boolean toTheRightOf(Movable c) {
        return this.circle.toTheRightOf(c);
    }

    @Override
    public boolean inFrontOf(Movable c) {
        return this.circle.inFrontOf(c);
    }

    @Override
    public boolean inVicinity(Movable c, double pixels) {
        return this.circle.inVicinity(c, pixels);
    }


    @Override
    public MovableCollision getBoundingObject() {
        return this.circle;
    }

    @Override
    public boolean overlaps(MovableCollision box) {
        return circle.overlaps(box);
    }


    @Override
    public boolean overlaps(BoundingContractLine line) {
        return circle.overlaps(line);
    }

    @Override
    public void toss(double velocity_x, double velocity_y) {
        circle.toss(velocity_x, velocity_y);
        if (velocity_x > 0) {
            pose = RIGHT;
        }
        if (velocity_x < 0) {
            pose = LEFT;
        }
    }

    @Override
    public void turnLeft(int dA) {
        circle.turnRight(dA);
    }

    @Override
    public void turnRight(int dA) {
        circle.turnLeft(dA);
    }


    @Override
    public void pushes(MovableCollision contract) {
        circle.pushes(contract);
    }


    @Override
    public void gravitate() {
        circle.gravitate();
        nextImageColumn();
        moving = true;
    }

    @Override
    public void rotateBy(int dA) {
        this.circle.rotateBy(dA);
    }

    @Override
    public void jump(double velocity) {
        this.circle.jump(velocity);
    }

    @Override
    public void pushedBackBy(BoundingContractLine line) {
        circle.pushedBackBy(line);
    }

    @Override
    public void bind(MovableCollision object) {
        this.circle = object;
    }

    @Override
    public void setWorld(double x, double y) {
        circle.setWorld(x, y);
    }

    @Override
    public double distanceTo(Movable c) {
        return this.circle.distanceTo(c);
    }

    @Override
    public void bounce() {
        this.circle.bounce();
    }


}
