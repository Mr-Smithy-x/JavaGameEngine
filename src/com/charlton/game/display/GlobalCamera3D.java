package com.charlton.game.display;

import com.charlton.game.models.base.model3d.contracts.Boundable3D;

public class GlobalCamera3D extends BaseCamera {

    private int zOrigin;
    private int z;

    protected static GlobalCamera3D camera;


    public static GlobalCamera3D getInstance() {
        if (camera == null) {
            camera = new GlobalCamera3D(0, 0, 0);
        }
        return camera;
    }

    public GlobalCamera3D(int x_origin, int y_origin, int zOrigin) {
        super(x_origin, y_origin);
        this.zOrigin = zOrigin;
    }


    public void setZ(int z) {
        this.z = z;
    }

    public int getZOrigin() {
        return zOrigin;
    }

    public int getZ() {
        return z;
    }

    public void setOrigin(Boundable3D e, int screenWidth, int screenHeight) {
        int x = e.getX().intValue();
        int y = e.getY().intValue();
        int z = e.getZ().intValue();

        this.xOrigin = x - (screenWidth / 2);
        this.yOrigin = y - (screenHeight / 2);
        this.zOrigin = z;

        this.x = xOrigin;
        this.y = yOrigin;
        this.z = zOrigin;
        // System.out.printf("(X: %s,Y: %s), ORIGIN: (x: %s, y: %s)", x, y, x_origin, y_origin);
    }

    public void moveBy(double dx, double dy, double dz) {
        super.moveBy(dx, dy);
        z += dz;
    }
}
