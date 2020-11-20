package com.charlton.game.models.base.model3d.contracts;


import com.charlton.game.contracts.BoundingContractLine;
import com.charlton.game.models.base.model2d.contracts.CollisionDetection2D;

public interface CollisionDetection3D extends CollisionDetection2D, Gravitational3D {

    default boolean overlaps(BoundingContractLine line) {
        return this.overlaps(line, true);
    }


    default boolean isOverlapping(Boundable3D box) {
        return (box.getX().doubleValue() + box.getWidth().doubleValue() >= getX().doubleValue()) &&
                (getX().doubleValue() + getWidth().doubleValue() >= box.getX().doubleValue()) &&
                (box.getY().doubleValue() + box.getHeight().doubleValue() >= getY().doubleValue()) &&
                (getY().doubleValue() + getHeight().doubleValue() >= box.getY().doubleValue());
    }


    default boolean willOverlap(Boundable3D r, int dx, int dy) {
        return (getX().intValue() + dx > r.getX2().intValue() || getY().intValue() + dy > r.getY2().intValue() ||
                r.getX().intValue() > getX2().intValue() + dx || r.getY().intValue() > getY2().intValue() + dy);
    }



    default boolean overlaps(BoundingContractLine line, boolean action) {
        double distance = line.distanceTo(getX(), getY()).doubleValue();
        boolean overlaps = distance < getRadius().doubleValue();
        if (overlaps && action) {
            pushedBackBy(line);
            bounceOffLine(line);
        }
        return overlaps;
    }

    default boolean overlaps(Gravitational3D contract) {
        double dx = getX().doubleValue() - contract.getX().doubleValue();
        double dy = getY().doubleValue() - contract.getY().doubleValue();
        double d2 = dx * dx + dy * dy;
        double ri = getRadius().doubleValue() + contract.getRadius().doubleValue();
        boolean collides = d2 <= ri * ri;
        if (collides) {
            pushes(contract);
            bounceOff(contract);
        }
        return collides;
    }


    default void pushedBackBy(BoundingContractLine line) {
        double distance = line.distanceTo(getX(), getY()).doubleValue();
        double p = getRadius().doubleValue() - distance;
        moveBy(p * line.getNormalX().doubleValue(), p * line.getNormalY().doubleValue());
    }

    default void pushes(Boundable3D contract) {
        double dx = getX().doubleValue() - contract.getX().doubleValue();
        double dy = getY().doubleValue() - contract.getY().doubleValue();
        double d = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / d;
        double uy = dy / d;
        double ri = getRadius().doubleValue() + contract.getRadius().doubleValue();
        double p = ri - d;
        moveBy(ux * p / 2, uy * p / 2);
        double set_pos_x = contract.getX().doubleValue() - (ux * p / 2);
        double set_pos_y = contract.getY().doubleValue() - (uy * p / 2);
        contract.setWorld(set_pos_x, set_pos_y);
    }

    default void bounceOff(Gravitational3D contract) {
        double dx = contract.getX().doubleValue() - getX().doubleValue();
        double dy = contract.getY().doubleValue() - getY().doubleValue();
        double mag = Math.sqrt(dx * dx + dy * dy);
        double ux = dx / mag; //in this case unit vector
        double uy = dy / mag;
        double tx = -uy; //tangent vector
        double ty = ux;
        double u = getVelocityX().doubleValue() * ux + getVelocityY().doubleValue() * uy;
        double t = getVelocityX().doubleValue() * tx + getVelocityY().doubleValue() * ty;
        double cu = contract.getVelocityX().doubleValue() * ux + contract.getVelocityY().doubleValue() * uy;
        double ct = contract.getVelocityX().doubleValue() * tx + contract.getVelocityY().doubleValue() * ty;
        setVelocity(.9 * (t * tx + cu * ux), .9 * (t * ty + cu * uy));
        contract.setVelocity(.9 * (ct * tx + u * ux), .9 * (ct * ty + u * uy));
    }

    default void bounceOffLine(BoundingContractLine line) {
        double d = line.distanceTo(getX(), getY()).doubleValue();
        double p = getRadius().doubleValue() - d;
        moveBy(1.9 * (p * line.getNormalX().doubleValue()), 1.9 * (p * line.getNormalY().doubleValue()));
        double mag = 1.9 * (getVelocityX().doubleValue() * line.getNormalX().doubleValue() + getVelocityY().doubleValue() * line.getNormalY().doubleValue());
        double tx = mag * line.getNormalX().doubleValue();
        double ty = mag * line.getNormalY().doubleValue();
        setVelocity(getVelocityX().doubleValue() - tx, getVelocityY().doubleValue() - ty);
    }


}
