package com.charlton.sprites;

import com.charlton.models.Sprite;

public class Soldier extends Sprite {
    public final static String[] poses = new String[]{
            "g_up",
            "g_dn",
            "g_lt",
            "g_rt",
    };

    public Soldier(int x, int y, int action) {
        super(x, y,  poses, 5, 10);
    }
}
