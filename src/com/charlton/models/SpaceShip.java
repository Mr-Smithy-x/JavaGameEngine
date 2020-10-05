package com.charlton.models;

import com.charlton.contracts.BoundingContract;

public class SpaceShip extends PolygonModel2D {

    static final int[][] SPACESHIP_STRUCTURE = new int[][]{
            {0, 50, -50},
            {50, -50, -50}
    };

    public SpaceShip(int world_x, int world_y) {
        super(SPACESHIP_STRUCTURE, world_x, world_y, 90);
    }

    @Override
    public boolean overlaps(BoundingContract<Number> box) {
        boolean fix = super.overlaps(box);
        if(fix){
            updateBounds();
        }
        return fix;
    }
}
