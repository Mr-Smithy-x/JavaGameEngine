package com.charlton.tilemap.models;

import com.charlton.GameApplet;
import com.charlton.helpers.Camera;
import com.charlton.models.SpriteSheet;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class TileSet implements Iterable<Long> {
    String map_image;
    LongMap tiles = new LongMap();
    int tile_width;
    int tile_height;

    public File getImageFile() {
        ClassLoader cl = TileSet.class.getClassLoader();
        URL resource = cl.getResource(String.format("assets/sets/%s", map_image));
        return new File(resource.getFile());
    }


    public static TileSet from(String json) throws IOException {
        if (json.endsWith(".json")) {
            ClassLoader cl = TileSet.class.getClassLoader();
            URL resource = cl.getResource(String.format("assets/maps/%s", json));
            System.out.println(resource.getFile());
            File file = new File(resource.getFile());

            //String fileString = String.format("%s/assets/maps/%s", System.getProperty("user.dir"), json);
            //File file = new File(fileString);
            return from(file);
        }
        return new Gson().fromJson(json, TileSet.class);
    }


    public BufferedImage getImage() throws IOException {
        return ImageIO.read(getImageFile());
    }

    public String getMapImage() {
        return map_image;
    }

    public void setMapImage(String map_image) {
        this.map_image = map_image;
    }

    public int getTileWidth() {
        return tile_width;
    }

    public void setTileWidth(int tile_width) {
        this.tile_width = tile_width;
    }

    public int getTileHeight() {
        return tile_height;
    }

    public void setTileHeight(int tile_height) {
        this.tile_height = tile_height;
    }

    public LongMap getTiles() {
        return tiles;
    }

    public void put(Long key, Long value) {
        tiles.put(key, value);
    }

    public void remove(Long key) {
        tiles.remove(key);
    }

    public void setTiles(@NotNull LongMap tiles) {
        this.tiles = tiles;
    }

    public void clear() {
        this.tiles.clear();
    }

    public void putAll(@NotNull LongMap tiles) {
        this.tiles.putAll(tiles);
    }

    public Iterable<Point> pointIterator() {
        return () -> tiles.keySet().stream().map(Point::fromLong).iterator();
    }

    public Iterable<Point> nonCollisionTitles() {
        return () -> tiles.keySet().stream().filter(point -> {
            long tile = get(point);
            return !Tile.isCollisionTile(tile);
        }).map(Point::fromLong).iterator();
    }

    public Iterable<Point> collisionTitles() {
        return () -> tiles.keySet().stream().filter(point -> {
            long tile = get(point);
            return Tile.isCollisionTile(tile);
        }).map(Point::fromLong).iterator();
    }

    @Override
    public Iterator<Long> iterator() {
        return this.tiles.keySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Long> action) {
        this.tiles.keySet().forEach(action);
    }

    @Override
    public Spliterator<Long> spliterator() {
        return this.tiles.keySet().spliterator();
    }

    public long get(Long key) {
        return this.tiles.get(key);
    }

    public long get(Point position) {
        return this.tiles.get(position.longValue());
    }

    public boolean has(long pointKey) {
        return this.tiles.containsKey(pointKey);
    }

    public boolean canMove(SpriteSheet sprite, int direction) {
        //int sprite_position_x = sprite.getX().intValue(); //X & Y in the world, ie (0,0) - (600,600) window
        //int sprite_position_y = sprite.getY().intValue();

        //int camera_offset_x = (int) (Camera.x + Camera.x_origin); //Origin of where the camera
        //int camera_offset_y = (int) (Camera.y + Camera.y_origin); //is on the screen + distance

        int sprite_position_x = (int) (Camera.x + Camera.x_origin); //X & Y in the world, ie (0,0) - (600,600) window
        int sprite_position_y = (int) (Camera.y + Camera.y_origin);

        int camera_offset_x = sprite.getX().intValue(); //Origin of where the camera
        int camera_offset_y = sprite.getY().intValue(); //is on the screen + distance


        int scaled_sprite_position_x = (sprite_position_x);// * Camera.scaling_factor);
        int scaled_sprite_position_y = (sprite_position_y);// * Camera.scaling_factor);

        int scaled_tile_width = tile_width * Camera.scaling_factor; //Size of the tile, now scaled
        int scaled_tile_height = tile_height * Camera.scaling_factor;

        int scaled_sprite_position_offset_x = scaled_sprite_position_x + camera_offset_x;
        int scaled_sprite_position_offset_y = scaled_sprite_position_y + camera_offset_y;

        int scaled_tile_position_x = scaled_sprite_position_offset_x - (scaled_sprite_position_offset_x % scaled_tile_width);
        int scaled_tile_position_y = scaled_sprite_position_offset_y - (scaled_sprite_position_offset_y % scaled_tile_height);

        System.out.printf("Character WORLD (%s, %s). Scaled: %s x (%s, %s)\n",
                sprite_position_x, sprite_position_y,
                Camera.scaling_factor,
                scaled_sprite_position_x,
                scaled_sprite_position_y);
        System.out.printf("ScaledXY: (%s, %s) - Camera Offset: (%s, %s)\n",
                scaled_tile_position_x, scaled_tile_position_y,
                camera_offset_x, camera_offset_y);
        switch (direction) {
            case GameApplet.LT:
                scaled_tile_position_x -= tile_width * Camera.scaling_factor;
                break;
            case GameApplet.RT:
                scaled_tile_position_x += tile_width * Camera.scaling_factor;
                break;
            case GameApplet.DN:
                scaled_tile_position_y += tile_height * Camera.scaling_factor;
                break;
            case GameApplet.UP:
                scaled_tile_position_y -= tile_height * Camera.scaling_factor;
                break;
        }

        int real_tile_pos_x = scaled_tile_position_x / Camera.scaling_factor;
        int real_tile_pos_y = scaled_tile_position_y / Camera.scaling_factor;
        System.out.printf("REAL: (%s, %s)\n", real_tile_pos_x, real_tile_pos_y);
        long point = Point.toLong(real_tile_pos_x, real_tile_pos_y);
        if (!tiles.containsKey(point)) {
            System.out.println("OUT OF BOUNDS!!!");
            return false;
        }
        long tileAddress = get(point);
        return !Tile.isCollisionTile(tileAddress);
    }

    public static class LongMap extends HashMap<Long, Long> {
    }


    public static TileSet from(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String stub = null;
        while ((stub = br.readLine()) != null) {
            sb.append(stub);
        }
        br.close();
        return from(sb.toString());
    }

}


