package com.charlton.game.models.base;

import com.charlton.game.contracts.*;

import java.awt.*;

public class BoundingCircle extends MovableObject implements Drawable, MovableCollision {

    protected double radius;
    private Movable object;
    private boolean bounded = false;
    protected int launch_delay = 20;
    protected int launch_countdown = 20;


    /**
     * Sloppy shoot
     *
     * @param obj
     */
    public void launch(BoundingCircle[] obj) {
        if (launch_delay == 0) {
            double speed = 25;
            obj[launch_delay].position_x = position_x;
            obj[launch_delay].position_y = position_y;
            obj[launch_delay].velocity_x = speed * cos_angle;
            obj[launch_delay].velocity_y = speed * sin_angle;
            launch_delay = launch_countdown;
        }
        launch_delay -= 1;
    }

    public BoundingCircle(double x, double y, double r, int world_angle) {
        this.position_x = x;
        this.position_y = y;
        this.world_angle = world_angle;
        this.radius = r;
        this.cos_angle = cos[world_angle];
        this.sin_angle = sin[world_angle];
    }

    @Override
    public void draw(Graphics g) {
        g.drawOval((int) (position_x - radius), (int) (position_y - radius), (int) (2.0 * radius), (int) (2.0 * radius));
        g.drawLine((int) position_x, (int) position_y, (int) (position_x + radius * cos_angle), (int) (position_y + radius * sin_angle));
    }

    @Override
    public void drawRelativeToCamera(Graphics g) {
        g.drawOval((int) (position_x - radius),
                (int) (position_y - radius), (int) (2.0 * radius), (int) (2.0 * radius));
        g.drawLine((int) position_x,
                (int) position_y,
                (int) (position_x + radius * cos_angle),
                (int) (position_y + radius * sin_angle));
    }

    @Override
    public boolean overlaps(BoundingContractLine line) {
        double distance = line.distanceTo(position_x, position_y).doubleValue();
        boolean overlaps = distance < radius;
        if (overlaps) {
            pushedBackBy(line);
            //bounce();
            bounceOffLine(line);
        }
        return overlaps;
    }


    @Override
    public boolean overlaps(MovableCollision c) {
        double dx = position_x - c.getX().doubleValue();
        double dy = position_y - c.getY().doubleValue();
        double d2 = dx * dx + dy * dy;
        double ri = radius + c.getRadius().doubleValue();
        boolean collides = d2 <= ri * ri;
        if (collides) {
            pushes(c);
            bounceOff(c);
        }
        return collides;
    }

    public double distanceBetween(BoundingCircle c) {
        double dx = c.position_x - position_x;
        double dy = c.position_y - position_y;
        double mag = Math.sqrt(dx * dx + dy + dy);
        return mag;
    }


    @Override
    public MovableCollision getBoundingObject() {
        return this;
    }

    @Override
    public void pushedBackBy(BoundingContractLine line) {
        double distance = line.distanceTo(position_x, position_y).doubleValue();
        //double p = (radius - radius - radius) - distance;
        double p = radius - distance;
        position_x += p * line.getNormalX().doubleValue();
        position_y += p * line.getNormalY().doubleValue();
    }


    @Override
    public void bind(MovableCollision object) {
        this.object = object;
        this.bounded = true;
        System.out.println("BINDED");
    }

    @Override
    public void pushes(MovableCollision contract) {
        double dx = position_x - contract.getX().doubleValue();
        double dy = position_y - contract.getY().doubleValue();
        double d = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / d;
        double uy = dy / d;
        double ri = radius + contract.getRadius().doubleValue();
        double p = ri - d;
        position_x += ux * p / 2;
        position_y += uy * p / 2;
        double set_pos_x = contract.getX().doubleValue() - (ux * p / 2);
        double set_pos_y = contract.getY().doubleValue() - (uy * p / 2);
        contract.setX(set_pos_x);
        contract.setY(set_pos_y);
    }


    public void bounceOff(Movable c) {
        double dx = c.getX().doubleValue() - position_x;
        double dy = c.getY().doubleValue() - position_y;
        double mag = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / mag; //in this case unit vector
        double uy = dy / mag;
        double tx = -uy; //tangent vector
        double ty = ux;

        double u = velocity_x * ux + velocity_y * uy;
        double t = velocity_x * tx + velocity_y * ty;

        double cu = c.getVelocityX().doubleValue() * ux + c.getVelocityY().doubleValue() * uy;
        double ct = c.getVelocityX().doubleValue() * tx + c.getVelocityY().doubleValue() * ty;
        velocity_x = .9 * (t * tx + cu * ux);
        velocity_y = .9 * (t * ty + cu * uy);
        c.setVelocityX(.9 * (ct * tx + u * ux));
        c.setVelocityY(.9 * (ct * ty + u * uy));
    }

    public void bounceOffLine(BoundingContractLine line) {
        double d = line.distanceTo(position_x, position_y).doubleValue();
        double p = radius - d;
        position_x += 1.9 * (p * line.getNormalX().doubleValue());
        position_y += 1.9 * (p * line.getNormalY().doubleValue());
        double mag = 1.9 * (velocity_x * line.getNormalX().doubleValue() + velocity_y * line.getNormalY().doubleValue());
        double tx = mag * line.getNormalX().doubleValue();
        double ty = mag * line.getNormalY().doubleValue();
        velocity_x -= tx;
        velocity_y -= ty;
    }


    @Override
    public Number getRadius() {
        return radius;
    }

    @Override
    public Number getWidth() {
        return radius * 2;
    }

    @Override
    public Number getHeight() {
        return radius * 2;
    }


    @Override
    public void gravitate() {
        super.gravitate();
    }

    @Override
    public int getType() {
        return TYPE_CIRCLE;
    }
}
