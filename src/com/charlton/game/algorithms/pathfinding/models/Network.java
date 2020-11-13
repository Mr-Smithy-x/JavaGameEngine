package com.charlton.game.algorithms.pathfinding.models;

public abstract class Network<N extends Node> {
    
    public abstract Iterable<N> getNodes();

    public abstract boolean hasCrossDirection();

}
