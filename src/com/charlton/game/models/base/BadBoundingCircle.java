package com.charlton.game.models.base;

public class BadBoundingCircle extends BoundingCircle {


    public BadBoundingCircle(double x, double y, double r, int world_angle) {
        super(x, y, r, world_angle);
    }


    public void shoot(){
        //(x, y, z) -> (d x/z, d y/z)
    }


}
