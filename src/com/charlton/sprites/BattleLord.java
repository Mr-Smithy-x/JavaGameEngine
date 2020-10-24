package com.charlton.sprites;

public class BattleLord extends Sprite {
    public final static String[] poses = new String[]{
            "bl_up",
            "bl_dn",
            "bl_lt",
            "bl_rt",
    };

    public BattleLord(int x, int y, int action) {
        super(x, y,  poses, 5, 10);
    }
}
