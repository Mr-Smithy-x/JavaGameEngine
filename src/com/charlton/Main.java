package com.charlton;

import com.charlton.base.GameHolder;
import com.charlton.containters.CustomTileGameContainer;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        GameHolder game = CustomTileGameContainer.frame(800, 800);
        game.start();
    }

}
//(w * y + x) // linear & (x, y)