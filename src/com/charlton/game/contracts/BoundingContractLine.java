package com.charlton.game.contracts;

public interface BoundingContractLine extends BoundingContract<Number> {
    Number distanceTo(Number x, Number y);
    Number getNormalX();
    Number getNormalY();
}
