package com.charlton.tilemap.models;

import com.charlton.GameApplet;
import com.charlton.helpers.Camera;
import com.charlton.models.SpriteSheet;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import com.charlton.algorithms.pathfinding.models.Network;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TileSet extends Network<Tile> implements Iterable<Point> {

    String map_image;
    LongMap tiles = new LongMap();
    HashMap<Point, Tile> points = new HashMap<Point, Tile>();
    int tile_width;
    int tile_height;

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
        TileSet set = new Gson().fromJson(json, TileSet.class);
        set.initializePoints();
        return set;
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


    private void initializePoints() {
        for (long p : tiles.keySet()) {
            Tile value = Tile.create(getTileAddress(p));
            Point point = Point.fromLong(p);
            value.setPoint(point);
            points.put(point, value);
        }
        points.values().forEach(t -> t.calculateNearestNodes(this));
    }


    public File getImageFile() {
        ClassLoader cl = TileSet.class.getClassLoader();
        URL resource = cl.getResource(String.format("assets/sets/%s", map_image));
        return new File(resource.getFile());
    }

    public Tile find(int x, int y) {
        for (Point point : this) {
            if (point.getX() == x && point.getY() == y)
                return points.get(point);
        }
        return null;
    }

    protected List<Point> getPoints() {
        return tiles.keySet().stream().map(Point::fromLong).collect(Collectors.toList());
    }

    public BufferedImage getImage() throws IOException {
        return ImageIO.read(getImageFile());
    }

    public String getMapImage() {
        return map_image;
    }

    public int getTileWidth() {
        return tile_width;
    }

    public int getTileHeight() {
        return tile_height;
    }

    public LongMap getTiles() {
        return tiles;
    }


    public void setTiles(@NotNull LongMap tiles) {
        this.tiles = tiles;
    }


    private Iterable<Point> layeredTiles(int level) {
        return () -> points.keySet().stream().filter(point -> {
            Tile tile = get(point);
            return tile.getLevel() == level;
        }).iterator();
    }

    @Override
    public Iterator<Point> iterator() {
        return this.points.keySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Point> action) {
        this.points.keySet().forEach(action);
    }

    @Override
    public Spliterator<Point> spliterator() {
        return this.points.keySet().spliterator();
    }

    public Tile get(Point point) {
        return this.points.get(point);
    }

    protected long getTileAddress(Long key) {
        return this.tiles.get(key);
    }

    protected long getTileAddress(Point position) {
        return this.tiles.get(position.toLong());
    }

    public boolean has(long pointKey) {
        return this.points.containsKey(Point.fromLong(pointKey));
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
        long tileAddress = getTileAddress(point);
        return !Tile.isCollisionTile(tileAddress);
    }

    @Override
    public Iterable<Tile> getNodes() {
        return () -> points.values().iterator();
    }

    //We are talking about 0,1,2,3, not 0,16,32,48

    public Iterable<Point> groundLayerTiles() {
        return layeredTiles(Tile.LEVEL_GROUND);
    }

    public Iterable<Point> midLayerTiles() {
        return layeredTiles(Tile.LEVEL_MID);
    }

    public Iterable<Point> skyLayerTiles() {
        return layeredTiles(Tile.LEVEL_SKY);
    }


    public static class LongMap extends HashMap<Long, Long> {
    }


}


