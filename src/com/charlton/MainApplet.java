package com.charlton;

import com.charlton.base.GameHolder;
import com.charlton.containters.CustomTileGameContainer;

import java.applet.Applet;

public class MainApplet extends Applet {

    GameHolder game;


    @Override
    public void init() {
        super.init();
        game = CustomTileGameContainer.applet(this);
        game.start();
    }

}
