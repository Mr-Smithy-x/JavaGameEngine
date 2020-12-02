package com.charlton.game.contracts;

import com.charlton.game.models.base.model2d.contracts.Boundable2D;

public interface BoundingContractLine extends Boundable2D {
    Number distanceTo(Number x, Number y);
    Number getNormalX();
    Number getNormalY();
}
