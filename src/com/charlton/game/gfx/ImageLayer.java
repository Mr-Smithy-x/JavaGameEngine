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


    public static BufferedImage get(String name) throws IOException {
        ClassLoader cl = ImageLayer.class.getClassLoader();
        URL resource = cl.getResource(String.format("assets/images/%s", name));
        System.out.println(resource.getFile());
        File file = new File(resource.getFile());
        BufferedImage read = ImageIO.read(file);
        return read;
    }

    public static ImageLayer from(String name, int x, int y, int z) throws IOException {
        return new ImageLayer(get(name), x, y, z);
    }

    public static BufferedImage sub(String name, int x, int y, int w, int h) throws IOException {
        BufferedImage bufferedImage = get(name).getSubimage(x, y, w, h);
        return bufferedImage;
    }

    public static ImageLayer create(BufferedImage image) {
        return new ImageLayer(image, 0, 0, 0);
    }


    public ImageLayer(Image image, int x, int y, int z) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public void draw(Graphics g) {
        for (int i = 0; i < 10; i++) {
            int width = (int) (image.getWidth(null) * 2);
            int height = (int) (image.getHeight(null) * 2);
            int x = (this.x - GlobalCamera.getInstance().getX()) + (width * i);
            g.drawImage(image, x, y, width, height, null);
        }
    }
}
