package com.charlton.game.states;


import com.charlton.Game;
import com.charlton.game.contracts.Drawable;

public abstract class State implements Drawable {

    private static State currentState = null;

    public static boolean DEBUG = true;

    public static void setState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }

    //CLASS

    protected Game game;

    public State(Game game){
        this.game = game;
    }

    public abstract void tick();

}