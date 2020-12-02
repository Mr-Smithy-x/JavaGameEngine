package com.charlton.game.contracts;

import com.charlton.game.display.BaseCamera;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.models.base.model2d.contracts.Boundable2D;

public interface CameraContract extends Boundable2D {

    default Number getCameraOffsetX(BaseCamera camera) {
        if (camera == null) {
            return getX();
        }
        return getX().doubleValue() - camera.getX();
    }

    default Number getCameraOffsetY(BaseCamera camera) {
        if (camera == null) {
            return getY();
        }
        return getY().doubleValue() - camera.getY();
    }

    default Number getCameraOffsetX2(BaseCamera camera) {
        if (camera == null) {
            return getX2();
        }
        return getX2().doubleValue() - camera.getX();
    }

    default Number getCameraOffsetY2(BaseCamera camera) {
        if (camera == null) {
            return getY();
        }
        return getY2().doubleValue() - camera.getY();
    }

    default Number getGlobalCameraOffsetX() {
        return getX().doubleValue() - GlobalCamera.getInstance().getX();
    }

    default Number getGlobalCameraOffsetY() {
        return getY().doubleValue() - GlobalCamera.getInstance().getY();
    }

    default Number getGlobalCameraOffsetX2() {
        return getX2().doubleValue() - GlobalCamera.getInstance().getX();
    }

    default Number getGlobalCameraOffsetY2() {
        return getY2().doubleValue() - GlobalCamera.getInstance().getY();
    }

}
