package com.charlton.contracts;


public interface CollisionDetection {
    boolean TESTING = true;
    MovableCollision getBoundingObject();
    boolean overlaps(MovableCollision box);
    boolean overlaps(BoundingContractLine line);
    void pushes(MovableCollision contract);
    void pushedBackBy(BoundingContractLine line);
    void bind(MovableCollision object);
}
