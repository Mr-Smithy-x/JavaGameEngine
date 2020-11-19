package com.charlton.game.contracts;

public interface BoundingContractLine extends Boundable{
    Number distanceTo(Number x, Number y);
    Number getNormalX();
    Number getNormalY();
}
