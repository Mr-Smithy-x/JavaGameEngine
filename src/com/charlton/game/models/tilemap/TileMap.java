package com.charlton.game.models.tilemap;

import com.charlton.game.contracts.Drawable;
import com.charlton.game.display.Camera;
import com.charlton.game.display.GlobalCamera;
import com.charlton.game.models.SpriteSheet;
import com.charlton.game.models.base.model2d.contracts.Boundable2D;
import com.charlton.game.models.base.model2d.contracts.Gravitational2D;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import com.charlton.game.algorithms.pathfinding.models.Network;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TileMap extends Network<Tile> implements Iterable<Point>, Drawable {

    String map_image;
    LongMap tiles = new LongMap();
    HashMap<Point, Tile> points = new HashMap<Point, Tile>();
    int tile_width;
    int tile_height;
    private boolean crossDirection = true;

    public static TileMap from(String json) throws IOException {
        if (json.endsWith(".json")) {
            File file = new File(String.format("assets/maps/%s", json));
            return from(file);
        }
        TileMap set = new Gson().fromJson(json, TileMap.class);
        set.crossDirection = false;
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
        return new File(String.format("assets/sets/%s", map_image));
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

    public boolean canMove(SpriteSheet sprite, SpriteSheet.Pose direction) {
        return canMove(sprite, direction, false);
    }

    public boolean canMove(SpriteSheet sprite, SpriteSheet.Pose direction, boolean ignoreInvisibles) {
        int sprite_position_x = (direction == SpriteSheet.Pose.LEFT ? sprite.getX() : sprite.getX2()).intValue() / GlobalCamera.getInstance().getScaling();
        int sprite_position_y = (direction == SpriteSheet.Pose.UP ? sprite.getY() : sprite.getY2()).intValue() / GlobalCamera.getInstance().getScaling();
        int scaled_tile_width = tile_width; //Size of the tile, now scaled
        int scaled_tile_height = tile_height;

        boolean willOverlap = false;
        switch (direction) {
            case LEFT:
                sprite_position_x -= sprite.getCurrentSpeed().intValue();
                break;
            case RIGHT:
                sprite_position_x += sprite.getCurrentSpeed().intValue();
                break;
            case DOWN:
                sprite_position_y += sprite.getCurrentSpeed().intValue();
                break;
            case UP:
                sprite_position_y -= sprite.getCurrentSpeed().intValue();
                break;
        }

        int scaled_tile_position_x = sprite_position_x - (sprite_position_x % scaled_tile_width);
        int scaled_tile_position_y = sprite_position_y - (sprite_position_y % scaled_tile_height);
        long point = Point.toLong(scaled_tile_position_x, scaled_tile_position_y);
        if (!tiles.containsKey(point)) {
            return ignoreInvisibles;
        }
        Tile tile = get(scaled_tile_position_x, scaled_tile_position_y);
        boolean collisionTile = tile.isCollision();

        if (Camera.DEBUG) {
            System.out.printf("Character Position: (%s, %s)\nTile Position: (%s, %s)\n",
                    sprite_position_x,
                    sprite_position_y,
                    scaled_tile_position_x,
                    scaled_tile_position_y
            );
        }

        if (collisionTile) {
            int speed = sprite.getCurrentSpeed().intValue();
            switch (direction) {
                case UP:
                    willOverlap = sprite.willOverlapImproved(tile, 0, -speed);
                    break;
                case DOWN:
                    willOverlap = sprite.willOverlapImproved(tile, 0, speed);
                    break;
                case LEFT:
                    willOverlap = sprite.willOverlapImproved(tile, -speed, 0);
                    break;
                case RIGHT:
                    willOverlap = sprite.willOverlapImproved(tile, speed, 0);
                    break;
            }
            if (willOverlap) {
                sprite.setVelocityY(0);
                //    sprite.setWorld(sprite.getX(), (tile.getY().intValue() - tile.getHeight().intValue()) * GlobalCamera.getInstance().getScaling());
            }
            return !willOverlap;
        }
        return !collisionTile;
    }


    @Override
    public Iterable<Tile> getNodes() {
        return () -> points.values().iterator();
    }

    @Override
    public boolean hasCrossDirection() {
        return crossDirection;
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

    @Override
    public void render(Graphics g) {
        for (Point p : this) {
            Tile tile = get(p);
            BufferedImage subimage = tile.getImage();
            int scaled_x = GlobalCamera.getInstance().getScaling() * p.getX();
            int scaled_y = GlobalCamera.getInstance().getScaling() * p.getY();
            int camera_x = GlobalCamera.getInstance().getX();
            int camera_y = GlobalCamera.getInstance().getY();
            int scaled_width = subimage.getWidth() * GlobalCamera.getInstance().getScaling();
            int scaled_height = subimage.getHeight() * GlobalCamera.getInstance().getScaling();
            g.drawImage(subimage, scaled_x - camera_x, scaled_y - camera_y, scaled_width, scaled_height, null);

            if (GlobalCamera.DEBUG) {
                g.setColor(new Color(0.2f, 0, 0, 0.4f));
                if (tile.isCollision()) {
                    g.fillRect(scaled_x - camera_x,
                            scaled_y - camera_y,
                            scaled_width,
                            scaled_height);

                } else {
                    g.drawRect(scaled_x - camera_x,
                            scaled_y - camera_y,
                            scaled_width,
                            scaled_height);
                }

                g.setColor(new Color(1, 1, 1));
                String format = String.format("(%s, %s)", p.getX(), p.getY());
                int formatWidth = g.getFontMetrics().stringWidth(format);

                g.drawString(format, (scaled_x - camera_x) + (scaled_width / 2) - (formatWidth / 2), (scaled_y - camera_y) + scaled_height / 2);
            }
        }
    }

    public Tile getTileBelow(Boundable2D bound, boolean jump) {
        int y = (jump ? bound.getY2() : bound.getY()).intValue() / GlobalCamera.getInstance().getScaling();
        int x = bound.getX().intValue() / GlobalCamera.getInstance().getScaling();

        x -= (x % tile_width);
        y -= (y % tile_height);
        y += tile_height;
        return get(x, y);
    }

    public Tile getTileAbove(Boundable2D bound, boolean jump) {
        int y = (jump ? bound.getY() : bound.getY2()).intValue() / GlobalCamera.getInstance().getScaling();
        int x = bound.getX().intValue() / GlobalCamera.getInstance().getScaling();

        x += (x % tile_width);
        y += (y % tile_height);
        y -= tile_height;
        return get(x, y);
    }


    public void fixBounds(Gravitational2D bounds) {
        fixBounds(bounds, false);
    }


    public void fixBounds(Gravitational2D bounds, boolean jump) {
        Tile tile = getTileBelow(bounds, jump);
        if (tile != null && tile.isCollision()) {
            int y = (tile.getY().intValue() - tile_height) * GlobalCamera.getInstance().getScaling();
            if (Math.abs(bounds.getVelocityY().intValue()) == 0) {
                bounds.setY(y);
            }
        }
    }


    public static class LongMap extends HashMap<Long, Long> {
    }


}


