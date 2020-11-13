package com.charlton.game.states;


import com.charlton.Game;
import com.charlton.game.contracts.Renderable;

public abstract class State implements Renderable {

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