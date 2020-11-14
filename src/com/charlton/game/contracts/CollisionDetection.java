package com.charlton.game.contracts;


public interface CollisionDetection {
    boolean TESTING = true;
    MovableCollision getBoundingObject();
    boolean overlaps(MovableCollision box);
    boolean overlaps(BoundingContractLine line, boolean action);
    default boolean overlaps(BoundingContractLine line) {
        return this.overlaps(line, true);
    }
    void pushes(MovableCollision contract);
    void pushedBackBy(BoundingContractLine line);
    void bind(MovableCollision object);
}
