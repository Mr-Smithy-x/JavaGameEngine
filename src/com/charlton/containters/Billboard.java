package com.charlton.containters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Billboard {
    private final BufferedImage image;
    double x;
    double y;
    double z;

    double w;
    double h;

    double d = 512;


    public Billboard(String name, double x, double y, double z) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        File file = new File(cl.getResource("res/" + name).getFile());
        this.image = ImageIO.read(file);
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = image.getWidth();
        this.h = image.getHeight();
    }

    public void draw(Graphics g) {
        double sx = d * x / z;
        double sy = d * y / z;

        double sw = d * w / z;
        double sh = d * h / z;
        g.drawImage(
                image,
                (int) (sx - sw / 2),
                (int) (sy - sh),
                (int) sw,
                (int) sh,
                null
        );
    }
}
