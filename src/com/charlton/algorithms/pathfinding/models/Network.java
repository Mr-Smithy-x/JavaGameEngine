package com.charlton.algorithms.pathfinding.models;

public abstract class Network<N extends Node> {
    
    public abstract Iterable<N> getNodes();

}
