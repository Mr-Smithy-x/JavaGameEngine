package com.charlton.game.models.base;

import com.charlton.game.contracts.*;
import com.charlton.game.display.GlobalCamera;

import java.awt.*;

public class BoundingCircle extends AIObject implements Drawable, CollisionDetection  {

    protected double radius;
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
            obj[launch_delay].velocity_x = speed * getCosAngle();
            obj[launch_delay].velocity_y = speed * getSinAngle();
            launch_delay = launch_countdown;
        }
        launch_delay -= 1;
    }

    public BoundingCircle(double x, double y, double r, int world_angle) {
        this.position_x = x;
        this.position_y = y;
        this.world_angle = world_angle;
        this.radius = r;
    }

    @Override
    public void render(Graphics g) {
        int x_radius = (int) (position_x - radius);
        int y_radius = (int) (position_y - radius);
        g.drawOval((int) (x_radius - GlobalCamera.getInstance().getX()), (int) (y_radius - GlobalCamera.getInstance().getY()), (int) (2.0 * radius), (int) (2.0 * radius));
        g.drawLine((int) (position_x - GlobalCamera.getInstance().getX()),
                (int) (position_y - GlobalCamera.getInstance().getY()),
                (int) (position_x - GlobalCamera.getInstance().getX() + radius * getCosAngle()),
                (int) (position_y - GlobalCamera.getInstance().getY() + radius * getSinAngle()));
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
    public int getType() {
        return 0;
    }

    public float getSpeed() {
        return speed;
    }
}
