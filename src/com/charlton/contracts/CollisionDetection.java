package com.charlton.contracts;


public interface CollisionDetection {
    boolean TESTING = true;
    BoundingContract<Number> getBoundingObject();
    boolean overlaps(BoundingContract<Number> box);
    boolean overlaps(BoundingContractLine line);
    void pushes(BoundingContract<Number> contract);
    void pushedBackBy(BoundingContractLine line);
    void bind(BoundingContract<Number> object);

}
