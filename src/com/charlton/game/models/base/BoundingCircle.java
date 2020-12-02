package com.charlton.game.models.base;

import com.charlton.game.contracts.*;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.models.base.model2d.AIObject2D;
import com.charlton.game.models.base.model2d.contracts.CollisionDetection2D;

import java.awt.*;

public class BoundingCircle extends AIObject2D implements Drawable, CollisionDetection2D {

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
            obj[launch_delay].x = x;
            obj[launch_delay].y = y;
            obj[launch_delay].velocity_x = speed * getCosAngle();
            obj[launch_delay].velocity_y = speed * getSinAngle();
            launch_delay = launch_countdown;
        }
        launch_delay -= 1;
    }

    public BoundingCircle(double x, double y, double r, int world_angle) {
        this.x = x;
        this.y = y;
        this.world_angle = world_angle;
        this.radius = r;
    }

    @Override
    public void render(Graphics g) {
        int x_radius = (int) (x - radius);
        int y_radius = (int) (y - radius);
        g.drawOval((int) (x_radius - GlobalCamera.getInstance().getX()), (int) (y_radius - GlobalCamera.getInstance().getY()), (int) (2.0 * radius), (int) (2.0 * radius));
        g.drawLine((int) (x - GlobalCamera.getInstance().getX()),
                (int) (y - GlobalCamera.getInstance().getY()),
                (int) (x - GlobalCamera.getInstance().getX() + radius * getCosAngle()),
                (int) (y - GlobalCamera.getInstance().getY() + radius * getSinAngle()));
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
