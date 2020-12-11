package com.charlton.containters;

import com.charlton.game.contracts.Drawable;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.display.GlobalCamera3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Billboard implements Drawable {
    private final BufferedImage image;
    double x;
    double y;
    double z;

    double w;
    double h;

    double d = 512;


    public Billboard(String name, double x, double y, double z) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        File file = new File("assets/res/" + name);
        this.image = ImageIO.read(file);
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = image.getWidth();
        this.h = image.getHeight();
    }

    @Override
    public void render(Graphics g) {
        double sx = d * (x - GlobalCamera3D.getInstance().getX()) / (z - GlobalCamera3D.getInstance().getZ());
        double sy = d * (y - GlobalCamera3D.getInstance().getZ()) / (z - GlobalCamera3D.getInstance().getZ());

        double sw = d * w / (z - GlobalCamera3D.getInstance().getZ());
        double sh = d * h / (z - GlobalCamera3D.getInstance().getZ());
        if (z - GlobalCamera3D.getInstance().getZ() > 10) {
            g.drawImage(
                    image,
                    (int) (sx - sw / 2) + GlobalCamera3D.getInstance().getXOrigin(),
                    (int) (sy - sh) + GlobalCamera.getInstance().getYOrigin(),
                    (int) sw,
                    (int) sh,
                    null
            );
        }
    }
}
