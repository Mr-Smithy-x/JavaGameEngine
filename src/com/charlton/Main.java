package com.charlton;

import com.charlton.base.GameHolder;
import com.charlton.containters.CustomTileGameContainer;

public class Main {

    static GameHolder game;

    public static void main(String[] args) {
        game = CustomTileGameContainer.frame(800, 800);
        game.start();
    }

}
//(w * y + x) // linear & (x, y)