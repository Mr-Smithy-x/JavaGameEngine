package tests;

import com.charlton.game.algorithms.pathfinding.AStar;
import com.charlton.game.models.tilemap.Point;
import com.charlton.game.models.tilemap.Tile;
import com.charlton.game.models.tilemap.TileMap;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class AStarAlgorithmTest {


    @Test
    public void testPath() throws IOException {
        TileMap tile = TileMap.from(new File("/Users/admin/code/IdeaProjects/Game2020/src/assets/maps/collision_with_layer.json"));
        Point source = new Point(0, 0);
        Point dest = new Point(6 * tile.getTileWidth(), 9 * tile.getTileHeight());
        AStar<TileMap, Tile> aStarAlgorithm = new AStar<>(tile);
        Tile s = tile.get(source);
        Tile e = tile.get(dest);
        aStarAlgorithm.setStartNode(s);
        aStarAlgorithm.setEndNode(e);
        aStarAlgorithm.solve();
        System.out.println(aStarAlgorithm.getPath());
    }
}

