package com.charlton.helpers;

import com.charlton.models.SpriteSheet;
import com.charlton.tilemap.models.Point;
import com.charlton.tilemap.models.Tile;
import com.charlton.tilemap.models.TileSet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteHelper {




    public static Image test(BufferedImage spriteSheet, int x, int y) {
        Image desired = spriteSheet.getSubimage(x, y, 30 * 3, 30 * 3);
        return desired;

    }

    /**
     * This function gets the position of the tile, scaled off bounds of the screen or in screen
     * but in general somewhere on the map.
     * @param tileSet
     * @param sprite
     * @return
     */
    public static Tile getCurrentTile(TileSet tileSet, SpriteSheet sprite) {
        Point currentPoint = getCurrentPoint(tileSet, sprite);
        return tileSet.get(currentPoint);
    }

    public static Point getCurrentPoint(TileSet tileSet, SpriteSheet sprite){

        int sprite_position_x = (int) (Camera.x + Camera.x_origin); //X & Y in the world, ie (0,0) - (600,600) window
        int sprite_position_y = (int) (Camera.y + Camera.y_origin);

        int camera_offset_x = sprite.getX().intValue(); //Origin of where the camera
        int camera_offset_y = sprite.getY().intValue(); //is on the screen + distance


        int scaled_sprite_position_x = (sprite_position_x);// * Camera.scaling_factor);
        int scaled_sprite_position_y = (sprite_position_y);// * Camera.scaling_factor);

        int scaled_tile_width = tileSet.getTileWidth() * Camera.scaling_factor; //Size of the tile, now scaled
        int scaled_tile_height = tileSet.getTileHeight() * Camera.scaling_factor;

        int scaled_sprite_position_offset_x = scaled_sprite_position_x + camera_offset_x;
        int scaled_sprite_position_offset_y = scaled_sprite_position_y + camera_offset_y;

        int scaled_tile_position_x = scaled_sprite_position_offset_x - (scaled_sprite_position_offset_x % scaled_tile_width);
        int scaled_tile_position_y = scaled_sprite_position_offset_y - (scaled_sprite_position_offset_y % scaled_tile_height);

        System.out.printf("Sprite WORLD (%s, %s). Scaled: %s x (%s, %s)\n",
                sprite_position_x, sprite_position_y,
                Camera.scaling_factor,
                scaled_sprite_position_x,
                scaled_sprite_position_y);
        System.out.printf("ScaledXY: (%s, %s) - Camera Offset: (%s, %s)\n",
                scaled_tile_position_x, scaled_tile_position_y,
                camera_offset_x, camera_offset_y);

        int real_tile_pos_x = scaled_tile_position_x / Camera.scaling_factor;
        int real_tile_pos_y = scaled_tile_position_y / Camera.scaling_factor;
        System.out.printf("REAL: (%s, %s)\n", real_tile_pos_x, real_tile_pos_y);
        long point = Point.toLong(real_tile_pos_x, real_tile_pos_y);
        return Point.fromLong(point);
    }

    /**
     * This function gets the position of the tile, scaled off bounds of the screen or in screen
     * but in general somewhere on the map.
     * @param tileSet
     * @param sprite
     * @return
     */
    public static Point getCurrentPoint2(TileSet tileSet, SpriteSheet sprite) {
        int sprite_x = (int) (sprite.getX().intValue() + Camera.x_origin);
        int sprite_y = (int) (sprite.getY().intValue() + Camera.y_origin);
        int tile_width = tileSet.getTileWidth();
        int tile_height = tileSet.getTileHeight();

        int scaled_sprite_x = sprite_x * Camera.scaling_factor;
        int scaled_sprite_y = sprite_y * Camera.scaling_factor;

        int scaled_tile_width = tile_width * Camera.scaling_factor;
        int scaled_tile_height = tile_height * Camera.scaling_factor;

        int fixed_tile_position_x = scaled_sprite_x - (scaled_sprite_x % scaled_tile_width);
        int fixed_tile_position_y = scaled_sprite_y - (scaled_sprite_y % scaled_tile_height);
        return new Point(fixed_tile_position_x/Camera.scaling_factor, fixed_tile_position_y/Camera.scaling_factor);
    }
}
