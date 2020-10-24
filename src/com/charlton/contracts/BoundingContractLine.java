package com.charlton.contracts;

public interface BoundingContractLine extends BoundingContract<Number> {
    Number distanceTo(Number x, Number y);
    Number getNormalX();
    Number getNormalY();
}
