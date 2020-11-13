package com.charlton.game.models.tilemap;

public class Point extends Number implements Comparable<Long> {

    int x;
    int y;
    int dist;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        if (x != point.x) return false;
        return y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }

    public int getDist() {
        return dist;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point setX(int x) {
        this.x = x;
        return this;
    }

    public Point setY(int y) {
        this.y = y;
        return this;
    }

    public long toLong(){
        return toLong(x,y);
    }

    public static long toLong(int position_x, int position_y) {
        long address = position_x;
        address = (address << 12) + position_y;
        return address;
    }

    public static Point fromLong(long position) {
        long position_x = (position >> 12) & 0xfff;
        long position_y = (position) & 0xfff;
        return new Point((int) position_x, (int) position_y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int intValue() {
        return (int) longValue();
    }

    @Override
    public long longValue() {
        return toLong(this.x, this.y);
    }

    @Override
    public float floatValue() {
        return longValue();
    }

    @Override
    public double doubleValue() {
        return longValue();
    }

    @Override
    public int compareTo(Long o) {
        return Long.compare(toLong(), o);
    }

}
