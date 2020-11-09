package com.charlton.tilemap.models;

import com.charlton.GameApplet;
import com.charlton.helpers.Camera;
import com.charlton.models.SpriteSheet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    public boolean canMove(SpriteSheet link, int direction) {
        int x = link.getX().intValue() / Camera.scaling_factor;
        int y = link.getY().intValue() / Camera.scaling_factor;
        //(Camera.scaling_factor * p.getX()) - (int)Camera.x + Camera.x_origin,
        //(Camera.scaling_factor * p.getY()) - (int)Camera.y + Camera.y_origin,
        int tile_pos_x = x - (x % tile_width) ;
        int tile_pos_y = y - (y % tile_height);
        switch (direction) {
            case GameApplet.LT:
                tile_pos_x -= tile_width;
                break;
            case GameApplet.RT:
                tile_pos_x += tile_width;
                break;
            case GameApplet.DN:
                tile_pos_y += tile_height;
                break;
            case GameApplet.UP:
                tile_pos_y -= tile_height;
                break;
        }

        long point = Point.toLong(tile_pos_x, tile_pos_y);
        if(!tiles.containsKey(point)){
            System.out.println("OUT OF BOUNDS!!!");

            return false;
        }
        System.out.printf("TILE_POS: (%s, %s)\n", tile_pos_x, tile_pos_y);
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


