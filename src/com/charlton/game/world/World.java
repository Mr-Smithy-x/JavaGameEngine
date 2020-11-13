package com.charlton.game.world;

import com.charlton.Game;
import com.charlton.game.contracts.Renderable;
import com.charlton.game.states.State;
import com.charlton.game.models.tilemap.Point;
import com.charlton.game.models.tilemap.Tile;
import com.charlton.game.models.tilemap.TileMap;

import java.awt.*;
import java.io.IOException;

public class World implements Renderable {

    private Game game;
    private TileMap tiles;


    public TileMap getTiles() {
        return tiles;
    }

    public World(Game game, String path) throws IOException {
        this.game = game;
        loadWorld(path);
    }

    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        for (Point point : tiles) {
            Tile tile = tiles.get(point);
            int x = (int) (point.getX() - game.getGameCamera().getXOrigin());
            int y = (int) (point.getY() - game.getGameCamera().getYOrigin());
            tile.render(g, x, y);
            if(State.DEBUG){
                if(tile.isCollision()){
                    g.setColor(new Color(0.2f, 0, 0, 0.2f));
                    g.fillRect(x, y, tiles.getTileWidth(), tiles.getTileHeight());
                }else{
                    g.setColor(new Color(0, 0.2f, 0, 0.2f));
                    g.drawRect(x, y, tiles.getTileWidth(), tiles.getTileHeight());
                }
            }
        }
    }

    private void loadWorld(String filename) throws IOException {
        tiles = TileMap.from(filename);
    }

}







