package com.charlton.helpers;

import com.charlton.algorithms.pathfinding.models.Node;
import com.charlton.models.SpriteSheet;
import com.charlton.sprites.Dog;
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
        return tileSet.get(getCurrentPoint(tileSet, sprite));
    }

    /**
     * This function gets the position of the tile, scaled off bounds of the screen or in screen
     * but in general somewhere on the map.
     * @param tileSet
     * @param sprite
     * @return
     */
    public static Point getCurrentPoint(TileSet tileSet, SpriteSheet sprite) {
        int sprite_x = sprite.getX().intValue();
        int sprite_y = sprite.getY().intValue();
        int tile_width = tileSet.getTileWidth();
        int tile_height = tileSet.getTileHeight();

        int scaled_sprite_x = sprite_x * Camera.scaling_factor;
        int scaled_sprite_y = sprite_y * Camera.scaling_factor;

        int scaled_tile_width = tile_width * Camera.scaling_factor;
        int scaled_tile_height = tile_height * Camera.scaling_factor;

        int fixed_tile_position_x = scaled_sprite_x - (scaled_sprite_x % scaled_tile_width);
        int fixed_tile_position_y = scaled_sprite_y - (scaled_sprite_y % scaled_tile_height);
        return new Point(fixed_tile_position_x, fixed_tile_position_y);
    }
}
