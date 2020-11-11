package com.charlton.algorithms;

import com.charlton.helpers.Camera;
import com.charlton.models.SpriteSheet;
import com.charlton.tilemap.models.Point;
import com.charlton.tilemap.models.TileSet;

public class BFS {


    public static boolean toCamera(TileSet tileSet, SpriteSheet sheet) {

        //{Unscaled Dimension And Positions)
        int tile_sheet_width = tileSet.getTileWidth();
        int tile_sheet_height = tileSet.getTileHeight();
        int sprite_sheet_position_x = sheet.getX().intValue();
        int sprite_sheet_position_y = sheet.getY().intValue();

        //(Scaled Dimension and Positions)
        int scaled_tile_sheet_width = tile_sheet_width * Camera.scaling_factor;
        int scaled_tile_sheet_height = tile_sheet_height * Camera.scaling_factor;
        int scaled_sprite_sheet_position_x = sprite_sheet_position_x * Camera.scaling_factor;
        int scaled_sprite_sheet_position_y = sprite_sheet_position_y * Camera.scaling_factor;

        //Real Columns (ie. starting position)
        int fixed_position_x = (scaled_sprite_sheet_position_x - (scaled_sprite_sheet_position_x % scaled_tile_sheet_width));
        int fixed_position_y = (scaled_sprite_sheet_position_y - (scaled_sprite_sheet_position_y % scaled_tile_sheet_height));

        //Position to find = -Camera.x_origin

        double position_x = Camera.x; // Destination
        double position_y = Camera.y; //Desition

        int[] dr = {-1, +1, 0, 0};
        int[] dc = {0, 0, +1, -1};
        for (int i = 0; i < 4; i++) {
            int rr = fixed_position_y + (dr[i] * scaled_tile_sheet_height);
            int cc = fixed_position_x + (dc[i] * scaled_tile_sheet_width);
            if(!tileSet.has(Point.toLong(cc, rr))) continue;
        }

        //double position_x = Camera.x + Camera.x_origin;
        //double position_y = Camera.y + Camera.y_origin;

        System.out.printf("POS: (%s, %s)\n", position_x, position_y);
        return false;
    }

}

