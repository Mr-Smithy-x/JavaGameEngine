package com.charlton.game.models.tilemap;

import com.charlton.applets.base.GameApplet;
import com.charlton.game.helpers.GlobalCamera;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.SpriteSheetEntity;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import com.charlton.game.algorithms.pathfinding.models.Network;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TileMap extends Network<Tile> implements Iterable<Point> {

    String map_image;
    LongMap tiles = new LongMap();
    HashMap<Point, Tile> points = new HashMap<Point, Tile>();
    int tile_width;
    int tile_height;

    public static TileMap from(String json) throws IOException {
        if (json.endsWith(".json")) {
            ClassLoader cl = TileMap.class.getClassLoader();
            URL resource = cl.getResource(String.format("assets/maps/%s", json));
            System.out.println(resource.getFile());
            File file = new File(resource.getFile());

            //String fileString = String.format("%s/assets/maps/%s", System.getProperty("user.dir"), json);
            //File file = new File(fileString);
            return from(file);
        }
        TileMap set = new Gson().fromJson(json, TileMap.class);
        set.initializePoints();
        return set;
    }


    public static TileMap from(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String stub = null;
        while ((stub = br.readLine()) != null) {
            sb.append(stub);
        }
        br.close();
        return from(sb.toString());
    }


    private void initializePoints() throws IOException {
        BufferedImage image = getImage();
        for (long p : tiles.keySet()) {
            Tile value = Tile.create(getTileAddress(p));
            Point point = Point.fromLong(p);
            value.setPoint(point);
            value.setImage(image.getSubimage(value.position_x, value.position_y, value.getPixelW(), value.pixel_h));
            points.put(point, value);
        }
        points.values().forEach(t -> t.calculateNearestNodes(this));
    }


    public File getImageFile() {
        ClassLoader cl = TileMap.class.getClassLoader();
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

    protected BufferedImage getImage() throws IOException {
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

    public Tile get(int x, int y) {
        return this.points.get(new Point(x, y));
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

        int sprite_position_x = (int) (GlobalCamera.x + GlobalCamera.x_origin); //X & Y in the world, ie (0,0) - (600,600) window
        int sprite_position_y = (int) (GlobalCamera.y + GlobalCamera.y_origin);

        int camera_offset_x = sprite.getX().intValue(); //Origin of where the camera
        int camera_offset_y = sprite.getY().intValue(); //is on the screen + distance


        int scaled_sprite_position_x = (sprite_position_x);// * Camera.scaling_factor);
        int scaled_sprite_position_y = (sprite_position_y);// * Camera.scaling_factor);

        int scaled_tile_width = tile_width * GlobalCamera.scaling_factor; //Size of the tile, now scaled
        int scaled_tile_height = tile_height * GlobalCamera.scaling_factor;

        int scaled_sprite_position_offset_x = scaled_sprite_position_x + camera_offset_x;
        int scaled_sprite_position_offset_y = scaled_sprite_position_y + camera_offset_y;

        int scaled_tile_position_x = scaled_sprite_position_offset_x - (scaled_sprite_position_offset_x % scaled_tile_width);
        int scaled_tile_position_y = scaled_sprite_position_offset_y - (scaled_sprite_position_offset_y % scaled_tile_height);

        System.out.printf("Character WORLD (%s, %s). Scaled: %s x (%s, %s)\n",
                sprite_position_x, sprite_position_y,
                GlobalCamera.scaling_factor,
                scaled_sprite_position_x,
                scaled_sprite_position_y);
        System.out.printf("ScaledXY: (%s, %s) - Camera Offset: (%s, %s)\n",
                scaled_tile_position_x, scaled_tile_position_y,
                camera_offset_x, camera_offset_y);
        switch (direction) {
            case GameApplet.LT:
                scaled_tile_position_x -= tile_width * GlobalCamera.scaling_factor;
                break;
            case GameApplet.RT:
                scaled_tile_position_x += tile_width * GlobalCamera.scaling_factor;
                break;
            case GameApplet.DN:
                scaled_tile_position_y += tile_height * GlobalCamera.scaling_factor;
                break;
            case GameApplet.UP:
                scaled_tile_position_y -= tile_height * GlobalCamera.scaling_factor;
                break;
        }

        int real_tile_pos_x = scaled_tile_position_x / GlobalCamera.scaling_factor;
        int real_tile_pos_y = scaled_tile_position_y / GlobalCamera.scaling_factor;
        System.out.printf("REAL: (%s, %s)\n", real_tile_pos_x, real_tile_pos_y);
        long point = Point.toLong(real_tile_pos_x, real_tile_pos_y);
        if (!tiles.containsKey(point)) {
            System.out.println("OUT OF BOUNDS!!!");
            return false;
        }
        long tileAddress = getTileAddress(point);
        return !Tile.isCollisionTile(tileAddress);
    }


    public boolean canMove(SpriteSheetEntity sprite, int direction) {
        int sprite_position_x = sprite.getX().intValue(); //Origin of where the camera
        int sprite_position_y = sprite.getY().intValue(); //is on the screen + distance


        int tile_position_x = sprite_position_x;
        int tile_position_y = sprite_position_y;

        System.out.printf("Character WORLD (%s, %s) - Tile World: (%s, %s)\n",
                sprite_position_x, sprite_position_y, tile_position_x, tile_position_y);
        switch (direction) {
            case SpriteSheetEntity.LEFT:
                tile_position_x -= tile_width;// * Camera.scaling_factor;
                break;
            case SpriteSheetEntity.RIGHT:
                tile_position_x += tile_width;// * Camera.scaling_factor;
                break;
            case SpriteSheetEntity.UP:
                tile_position_y -= tile_height;// * Camera.scaling_factor;
                break;
            case SpriteSheetEntity.DOWN:
                tile_position_y += tile_height;// * Camera.scaling_factor;
                break;
        }


        tile_position_x = tile_position_x - (tile_position_x % tile_width);
        tile_position_y = tile_position_y - (tile_position_y % tile_height);
        int real_tile_pos_x = tile_position_x;
        int real_tile_pos_y = tile_position_y;
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


