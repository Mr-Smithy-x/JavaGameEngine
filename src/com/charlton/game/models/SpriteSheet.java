package com.charlton.game.models;

import com.charlton.game.contracts.*;
import com.charlton.game.display.Camera;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.gfx.SubImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SpriteSheet implements Drawable, AI {

    public enum Pose {
        UP, DOWN, LEFT, RIGHT, JUMP, ATTACK_UP, ATTACK_DOWN, ATTACK_LEFT, ATTACK_RIGHT, SPIN_ATTACK
    }


    protected Pose pose = Pose.DOWN;
    protected boolean moving = false;
    protected BufferedImage spriteSheet;
    protected Map<Pose, List<SubImage>> images = new HashMap<>();
    protected File file;
    protected int delay = 0;
    protected int duration = 10;
    protected int current_column = 0;
    protected AI circle;


    public SpriteSheet(String name) throws IOException {
        initializeSheet(name);
        images.putAll(initializeSheet(spriteSheet));
    }


    protected abstract Map<Pose, List<SubImage>> initializeSheet(BufferedImage spriteSheet);

    @Override
    public void setWorldAngle(int world_angle) {
        circle.setWorldAngle(world_angle);
    }

    @Override
    public int getWorldAngle() {
        return circle.getWorldAngle();
    }


    @Override
    public void moveBy(Number dx, Number dy) {
        moving = true;
        circle.moveBy(dx, dy);
        if (dx.doubleValue() < dy.doubleValue()) {
            if (dy.doubleValue() > 0) {
                pose = Pose.DOWN;
            }
            if (dy.doubleValue() < 0) {
                pose = Pose.UP;
            }
            if (dx.doubleValue() < 0) {
                pose = Pose.LEFT;
            }
            if (dx.doubleValue() > 0) {
                pose = Pose.RIGHT;
            }
        } else if (dy.doubleValue() < dx.doubleValue()) {
            if (dy.doubleValue() > 0) {
                pose = Pose.DOWN;
            }
            if (dy.doubleValue() < 0) {
                pose = Pose.UP;
            }
            if (dx.doubleValue() < 0) {
                pose = Pose.LEFT;
            }
            if (dx.doubleValue() > 0) {
                pose = Pose.RIGHT;
            }
        }
        switch (pose) {
            case LEFT:
                circle.setWorldAngle(180);
                break;
            case RIGHT:
                circle.setWorldAngle(0);
                break;
            case UP:
                circle.setWorldAngle(270);
                break;
            case DOWN:
            default:
                circle.setWorldAngle(90);
        }
        nextImageColumn();
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

    protected List<SubImage> initAnimation(int column, int row, int width, int height, int size) {
        List<SubImage> subImages = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            subImages.add(new SubImage((column * width) + (i * width), row * height, width, height));
        }
        return subImages;
    }


    protected void validate() {
        if (current_column >= images.get(pose).size()) {
            current_column = 0;
        }
        if (current_column < 0) {
            current_column = images.get(pose).size() - 1;
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
        List<SubImage> subImage = images.get(pose);
        if (subImage.size() <= current_column) {
            current_column = 0;
        }
        SubImage sub = subImage.get(current_column);
        sub.setImage(spriteSheet);
        return sub.getImage();
    }

    public Image getStillImage() {
        SubImage stillImage = images.get(pose).get(0);
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

        int width = image.getWidth(null) * GlobalCamera.getInstance().getScaling();
        int height = image.getHeight(null) * GlobalCamera.getInstance().getScaling();
        g.drawImage(image,
                getGlobalCameraOffsetX().intValue(),
                getGlobalCameraOffsetY().intValue(),
                width,
                height,
                null
        );
        moving = false;
        if (GlobalCamera.DEBUG) {
            drawBounds(g);
        }
    }

    protected void drawBounds(Graphics g) {
        g.drawRect(
                getGlobalCameraOffsetX().intValue(),
                getGlobalCameraOffsetY().intValue(),
                circle.getWidth().intValue(),
                circle.getHeight().intValue()
        );
    }

    public void setPose(Pose pose) {
        moving = false;
        this.pose = pose;

        switch (pose) {
            case LEFT:
                circle.setWorldAngle(180);
                break;
            case RIGHT:
                circle.setWorldAngle(0);
                break;
            case UP:
                circle.setWorldAngle(270);
                break;
            case DOWN:
            default:
                circle.setWorldAngle(90);
        }
        nextImageColumn();
    }

    public Pose getPose() {
        return pose;
    }

    @Override
    public int getType() {
        return 0;
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
        return circle.getWidth();
    }

    @Override
    public Number getHeight() {
        return circle.getHeight();
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
    public void setDragX(Number dragX) {
        circle.setDragX(dragX);
    }

    @Override
    public void setDragY(Number dragY) {
        circle.setDragY(dragY);
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
    public void toss(Number velocity_x, Number velocity_y) {
        circle.toss(velocity_x, velocity_y);
        if (velocity_x.intValue() > 0) {
            pose = Pose.RIGHT;
        }
        if (velocity_x.intValue() < 0) {
            pose = Pose.LEFT;
        }
    }

    @Override
    public void gravitate() {
        circle.gravitate();
        nextImageColumn();
        moving = true;
    }

    @Override
    public Number getDragX() {
        return circle.getDragX();
    }

    @Override
    public Number getDragY() {
        return circle.getDragY();
    }


    @Override
    public void setChaseSpeed(int speed) {
        circle.setChaseSpeed(speed);
    }

    @Override
    public void setTurnSpeed(int turnspeed) {
        circle.setTurnSpeed(turnspeed);
    }
}
