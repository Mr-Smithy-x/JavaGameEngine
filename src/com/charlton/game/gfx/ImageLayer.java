package com.charlton.game.gfx;

import com.charlton.game.display.GlobalCamera;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageLayer {
    Image image;

    int x;
    int y;
    int z;

    public static ImageLayer from(String name, int x, int y, int z) throws IOException {
        ClassLoader cl = ImageLayer.class.getClassLoader();
        URL resource = cl.getResource(String.format("assets/images/%s", name));
        System.out.println(resource.getFile());
        File file = new File(resource.getFile());
        BufferedImage read = ImageIO.read(file);
        return new ImageLayer(read, x, y, z);
    }


    public ImageLayer(Image image, int x, int y, int z) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public void draw(Graphics g) {
        g.drawImage(image, (int)(x - GlobalCamera.getInstance().getX()), (int)(y - GlobalCamera.getInstance().getY()), null);
    }
}
