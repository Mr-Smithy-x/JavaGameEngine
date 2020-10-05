package com.charlton.models;

public class Tank extends PolygonModel2D {

    static final int[][] TANK_STRUCTURE = new int[][]{
        {-40, -40, 40, 40}, //body_x
        {-25, 25, 25, -25}, //body_y
        {-10, -10, 10, 10}, //hatch_x
        {-10, 10, 10, -10}, //hatch_y
        {35, 35, 10, 10}, //gun_x
        {-3, 3, 3, -3}, //gun_y
        {-35, -35, 35, 35}, //trackL_x
        {-35, -25, -25, -35}, //trackL_y
        {-35, -35, 35, 35}, //trackR_x
        {35, 25, 25, 35}, //trackR_y
    };

    public Tank(int world_x, int world_y, int world_angle) {
        super(TANK_STRUCTURE, world_x, world_y, world_angle);
    }


}
