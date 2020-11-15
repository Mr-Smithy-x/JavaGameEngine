package com.charlton;

import com.charlton.base.GameHolder;
import com.charlton.containters.PlatformerContainer;

import java.io.IOException;

public class Main {

    static GameHolder game;

    /**
     * Start of platformer
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        game = PlatformerContainer.frame(800, 800);
        //game = GameF20Container.frame(800, 800);

        game.start();
    }

}
//(w * y + x) // linear & (x, y)