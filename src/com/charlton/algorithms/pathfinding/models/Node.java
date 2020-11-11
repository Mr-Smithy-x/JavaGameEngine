package com.charlton.algorithms.pathfinding.models;

import java.util.ArrayList;

public abstract class Node {

    private Node parent;
    private ArrayList<Node> neighbours;
    private double cost, heuristic, function;
    private boolean valid;

    //region Getters
    public double getHeuristic() {
        return heuristic;
    }

    public double getFunction() {
        return function;
    }

    public double getCost() {
        return cost;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public Node getParent() {
        return parent;
    }
    //endregion

    //region Abstracts

    public abstract void calculateNearestNodes(Network network);

    public abstract double distanceTo(Node dest);

    public abstract double discover(Node dest);

    //endregion

    //region Setters

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setHeuristic(double heuristic) {
        this.heuristic = heuristic;
    }

    public void setFunction(double function) {
        this.function = function;
    }

    public void setNeighbours(ArrayList<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    //endregion

    public boolean isValid() {
        return valid;
    }

}
