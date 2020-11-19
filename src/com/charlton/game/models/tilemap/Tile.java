package com.charlton.game.models.tilemap;

import com.charlton.game.algorithms.pathfinding.models.Network;
import com.charlton.game.algorithms.pathfinding.models.Node;
import com.charlton.game.contracts.Boundable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile extends Node implements Comparable<Node>, Boundable{


    public final static int LEVEL_GROUND = 0, LEVEL_MID = 1, LEVEL_SKY = 2;
    public final static int TRANSITION_NONE = 0, TRANSITION_TELEPORT = 1, TRANSITION_ROOM = 2;
    public BufferedImage image;
    Point point;

    int position_x; // range 0-4095; 0x7FF
    int position_y;// range 0-4095; 0x7FF
    int pixel_w;// range 0-255; 0xFF
    int pixel_h;// range 0-255; //0xFF
    int transition_reference = 0; // Range; 0-127 // 0x7F
    int transition_type = 0; // Range 0-3; 0x3;
    int level = 0; //Range 0-3; //0x3
    boolean collision;// range 0-1; 0x1
    boolean object;// range 0-1; 0x1


    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (position_x != tile.position_x) return false;
        if (position_y != tile.position_y) return false;
        if (pixel_w != tile.pixel_w) return false;
        if (pixel_h != tile.pixel_h) return false;
        if (transition_reference != tile.transition_reference) return false;
        if (transition_type != tile.transition_type) return false;
        if (level != tile.level) return false;
        if (collision != tile.collision) return false;
        if (object != tile.object) return false;
        return point != null ? point.equals(tile.point) : tile.point == null;
    }

    @Override
    public int hashCode() {
        int result = point != null ? point.hashCode() : 0;
        result = 31 * result + position_x;
        result = 31 * result + position_y;
        result = 31 * result + pixel_w;
        result = 31 * result + pixel_h;
        result = 31 * result + transition_reference;
        result = 31 * result + transition_type;
        result = 31 * result + level;
        result = 31 * result + (collision ? 1 : 0);
        result = 31 * result + (object ? 1 : 0);
        return result;
    }


    /**
     * This is just a point reference, this is necessary for A*
     *
     * @param point
     */
    public void setPoint(Point point) {
        this.point = point;
    }


    public int getPixelH() {
        return pixel_h;
    }

    public int getPixelW() {
        return pixel_w;
    }

    public int getPositionX() {
        return position_x;
    }

    public int getPositionY() {
        return position_y;
    }

    public Tile(int position_x, int position_y, int pixel_w, int pixel_h, boolean collides, boolean object) {
        this(position_x, position_y, pixel_w, pixel_h, collides, object, LEVEL_GROUND, TRANSITION_NONE, 0);
    }

    public Tile(int position_x, int position_y, int pixel_w, int pixel_h, boolean collides, boolean object, int level, int transition_type, int transition_reference) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.pixel_w = pixel_w;
        this.pixel_h = pixel_h;
        this.collision = collides;
        this.object = object;
        this.level = level;
        this.transition_type = transition_type;
        this.transition_reference = transition_reference;
    }


    //region Static Functions
    //region Conversation Functions
    public static long toLong(int position_x, int position_y, int pixel_w, int pixel_h, boolean collision, boolean is_object) {
        return toLong(position_x, position_y, pixel_w, pixel_h, collision ? 1 : 0, is_object ? 1 : 0, LEVEL_GROUND, TRANSITION_NONE, 0);
    }

    public static long toLong(int position_x, int position_y, int pixel_w, int pixel_h, int collision, int is_object, int level, int transition_type, int transition_reference) {
        long address = position_x;
        address = (address << 12) + position_y;
        address = (address << 8) + pixel_w;
        address = (address << 8) + pixel_h;
        address = (address << 7) + transition_reference;
        address = (address << 2) + transition_type;
        address = (address << 2) + level;
        address = (address << 1) + collision;
        address = (address << 1) + is_object;
        return address;
    }

    public static Tile create(long address) {
        long spos_x = (address >> 41) & 0xfff;
        long spos_y = (address >> 29) & 0xfff;
        long stile_w = (address >> 21) & 0xff;
        long stile_h = (address >> 13) & 0xff;
        long transition_reference = (address >> 7) & 0x7F; // Range; 0-127 // 0x7F
        long transition_type = (address >> 4) & 0x3; // Range 0-2; 0x2;
        long level = (address >> 2) & 0x3; //Range 0-2; //0x2
        long scollision = (address >> 1) & 0x1;
        long sobject = (address & 0x1);
        return new Tile((int) spos_x, (int) spos_y, (int) stile_w, (int) stile_h, scollision == 1, sobject == 1, (int) level, (int) transition_type, (int) transition_reference);
    }

    public static Tile createOld(long address) {
        long spos_x = (address >> 32) & 0xfff;
        long spos_y = (address >> 20) & 0xfff;
        long stile_w = (address >> 12) & 0xff;
        long stile_h = (address >> 4) & 0xff;
        long scollision = (address >> 2) & 0x1;
        long sobject = (address & 0x1);
        return new Tile((int) spos_x, (int) spos_y, (int) stile_w, (int) stile_h, scollision == 1, sobject == 1);
    }
    //endregion

    //region bit retrievals
    public static long getPositionX(long address) {
        return (address >> 41) & 0xfff;
    }

    public static long getPositionY(long address) {
        return (address >> 29) & 0xfff;
    }

    public static long getTileWidth(long address) {
        return (address >> 21) & 0xff;
    }

    public static long getTileHeight(long address) {
        return (address >> 13) & 0xff;
    }

    public static long getTransitionReference(long address) {
        return (address >> 7) & 0x7F;
    }

    public static long getTransitionType(long address) {
        return (address >> 4) & 0x3;
    }

    public static long getLevel(long address) {
        return (address >> 2) & 0x3;
    }

    public static long getCollision(long address) {
        return (address >> 1) & 0x1;
    }

    public static long getObject(long address) {
        return (address) & 0x1;
    }
    //endregion

    //region boolean checks
    public static boolean isCollisionTile(long address) {
        return getCollision(address) == 1;
    }

    public static boolean isObjectTile(long address) {
        return getObject(address) == 1;
    }
    //endregion

    //region bit manipulations


    public static long setTransitionReference(long address, int transition_reference) {
        long r = getTransitionReference(address);
        address -= (r << 7); //reset;
        address += (transition_reference << 7); //addback
        return address;
    }

    public static long setTransitionType(long address, int transition_type) {
        long t = getTransitionType(address);
        address -= (t << 4); //reset;
        address += (transition_type << 4); //addback
        return address;
    }


    public static long setLevel(long address, int level) {
        long l = getLevel(address);
        address -= (l << 2); //reset;
        address += (level << 2); //addback
        return address;
    }


    public static long setCollision(long address, boolean collides) {
        long collision = (address >> 1) & 0x1;
        if (collision == 0) { //currently no collision
            address += (collides ? 1 << 1 : 0); //if true add collision, +2
        } else { //currently collision
            address -= (!collides ? 1 << 1 : 0); //if false minus collision, -2
        }
        return address;
    }

    public static long setIsObject(long address, boolean is_object) {
        long object = (address & 0x1);
        if (object == 0) { //currently no collision
            address += (is_object ? 1 : 0); //if true add collision
        } else { //currently collision
            address -= (!is_object ? 1 : 0); //if false minus collision
        }
        return address;
    }

    //endregion


    @Override
    public boolean isValid() {
        return !isCollision();
    }

    //region Toggles
    public static long toggleCollision(long address) {
        long collision = (address >> 1) & 0x1;
        if (collision == 0) {
            address += 1 << 1; // +2
        } else {
            address -= 1 << 1; // -2
        }
        return address;
    }

    public static long toggleObject(long address) {
        long object = (address & 0x1);
        if (object == 0) {
            address += 1;
        } else {
            address -= 1;
        }
        return address;
    }
    //endregion Toggles

    //endregion

    //region Class Function
    public boolean isObject() {
        return object;
    }

    public int getTransitionReference() {
        return transition_reference;
    }

    public int getTransitionType() {
        return transition_type;
    }

    public int getLevel() {
        return level;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setObject(boolean object) {
        this.object = object;
    }

    public void toggleObject() {
        this.object = !this.object;
    }

    public void toggleCollision() {
        this.collision = !this.collision;
    }

    public long toLong() {
        return toLong(position_x, position_y, pixel_w, pixel_h, collision ? 1 : 0, object ? 1 : 0, level, transition_type, transition_reference);
    }
    //endregion

    //region Number Overrides

    @Override
    public int compareTo(Node o) {
        double b1Priority = this.getFunction();
        double b2Priority = o.getFunction();
        return Double.compare(b1Priority, b2Priority);
    }

    public int getCollision() {
        return collision ? 1 : 0;
    }

    public int getObject() {
        return object ? 1 : 0;
    }

    @Override
    public void calculateNearestNodes(Network network) {

        TileMap grid = (TileMap) network;

        ArrayList<Node> nodes = new ArrayList<>();

        int minX = 0;
        int minY = 0;
        int maxX = grid.getTileWidth() + point.x;
        int maxY = grid.getTileHeight() + point.y;

        int x = point.x;
        int y = point.y;

        if (x > minX) {
            Tile e = grid.find(x - pixel_w, y);
            if(e != null && e.isValid()) {
                nodes.add(e); //west
            }
        }

        if (x < maxX) {
            Tile e = grid.find(x + pixel_w, y);
            if(e != null && e.isValid()) {
                nodes.add(e); //east
            }
        }

        if (y > minY) {
            Tile e = grid.find(x, y - pixel_h);
            if(e != null && e.isValid()) {
                nodes.add(e); //north
            }
        }

        if (y < maxY) {
            Tile e = grid.find(x, y + pixel_h);
            if(e != null && e.isValid()) {
                nodes.add(e); //south
            }
        }

        if(network.hasCrossDirection()) {
            if (x > minX && y > minY) {
                Tile e = grid.find(x - pixel_w, y - pixel_h);
                if(e != null && e.isValid()) {
                    nodes.add(e); //northwest
                }
            }

            if (x < maxX && y < maxY) {
                Tile e = grid.find(x + pixel_w, y + pixel_h);
                if(e != null && e.isValid()) {
                    nodes.add(e); //southeast
                }
            }

            if (x < maxX && y > minY) {
                Tile e = grid.find(x + pixel_w, y - pixel_h);
                if(e != null && e.isValid()) {
                    nodes.add(e); //northeast
                }
            }

            if (x > minY && y < maxY) {
                Tile e = grid.find(x - pixel_w, y + pixel_h);
                if(e != null && e.isValid()) {
                    nodes.add(e); //southwest
                }
            }
        }
        setNeighbours(nodes);
    }



    @Override
    public double discover(Node dest) {
        return distanceTo(dest);
    }

    @Override
    public double distanceTo(Node dest) {
        Tile d = (Tile) dest;
        double px = d.point.x - point.x;
        double py = d.point.y - point.y;
        return Math.sqrt(px * px + py * py);
    }

    @Override
    public String toString() {
        return String.format("Position(%s, %s)", point.x, point.y);
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(image, x, y, pixel_w, pixel_h, null);
    }

    @Override
    public void setX(Number x) {
        //nothing
    }

    @Override
    public void setY(Number y) {
        //nothing
    }

    @Override
    public Number getX() {
        return point.x;
    }

    @Override
    public Number getY() {
        return point.y;
    }

    @Override
    public Number getWidth() {
        return pixel_w;
    }

    @Override
    public Number getHeight() {
        return pixel_h;
    }

    @Override
    public Number getRadius() {
        return pixel_w / 2;
    }

    @Override
    public int getType() {
        return 0;
    }

    //endregion
}
