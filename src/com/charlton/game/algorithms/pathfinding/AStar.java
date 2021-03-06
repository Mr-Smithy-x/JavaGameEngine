package com.charlton.game.algorithms.pathfinding;

import com.charlton.game.algorithms.pathfinding.models.Network;
import com.charlton.game.algorithms.pathfinding.models.Node;

import java.util.*;

public class AStar<T extends Network<N>, N extends Node> extends Observable {

    private T network;
    private PriorityQueue<Node> path;

    private Node start;
    private Node end;

    private Set<Node> openList;
    private Set<Node> closedList;

    public AStar(T network) {
        this.network = network;
    }

    public void solve() {
        if(path != null && !path.isEmpty()){
            path.clear();
        }

        if (start == null && end == null) {
            return;
        }

        if (start.equals(end)) {
            this.path = new PriorityQueue<>(1000);
            return;
        }

        this.path = new PriorityQueue<>(1000);

        this.openList = new HashSet<>();
        this.closedList = new HashSet<>();

        this.openList.add(start);

        while (!openList.isEmpty()) {
            Node current = getLowestFunction();

            if (current.equals(end)) {
                retrace(current);
                break;
            }

            openList.remove(current);
            closedList.add(current);

            //System.out.printf("CURRENT: (%s)\n", current);
            for (Node n : current.getNeighbours()) {

                if (closedList.contains(n) || !n.isValid()) {
                    continue;
                }

                double tempScore = current.getCost() + current.distanceTo(n);

                if (openList.contains(n)) {
                    if (tempScore < n.getCost()) {
                        n.setCost(tempScore);
                        n.setParent(current);
                    }
                } else {
                    n.setCost(tempScore);
                    openList.add(n);
                    n.setParent(current);
                }

                n.setHeuristic(n.discover(end));
                n.setFunction(n.getCost() + n.getHeuristic());

            }

        }

        //updateUI();
    }

    public void reset() {
        this.start = null;
        this.end = null;
        this.path = null;
        this.openList = null;
        this.closedList = null;
        for (N node : network.getNodes()) {
            node.setValid(true);
        }
    }

    private void retrace(Node current) {
        Node temp = current;
        this.path.add(current);

        while (temp.getParent() != null) {
            this.path.add(temp.getParent());
            temp = temp.getParent();
        }

        this.path.add(start);
    }

    private Node getLowestFunction() {
        Node lowest = openList.stream().findFirst().orElse(null);
        for (Node n : openList) {
            if (n.getFunction() < lowest.getFunction()) {
                lowest = n;
            }
        }
        return lowest;
    }

    public void updateUI() {
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public T getNetwork() {
        return network;
    }

    public PriorityQueue<Node> getPath() {
        return path;
    }

    public Node getStartNode() {
        return start;
    }

    public Node getEndNode() {
        return end;
    }

    public void setStartNode(Node start) {
        this.start = start;
    }

    public void setEndNode(Node end) {
        this.end = end;
    }

}
