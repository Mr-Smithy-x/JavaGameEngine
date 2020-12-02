package com.charlton;

import com.charlton.base.GameHolder;
import com.charlton.containters.BillboardingContainer;
import com.charlton.containters.PlatformerContainer;

import java.io.IOException;

public class Main {

    static GameHolder game;

    /**
     * Start of platformer
     * @param args
     */
    public static void main(String[] args) throws IOException {
        game = BillboardingContainer.holder(800, 800);
        //game = PlatformerContainer.holder(800, 800);
        //game = CustomTileGameContainer.holder(800, 800);

        game.start();
    }

}
//(w * y + x) // linear & (x, y)