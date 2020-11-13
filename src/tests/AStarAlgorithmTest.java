package tests;

import com.charlton.algorithms.pathfinding.AStar;
import com.charlton.tilemap.models.Point;
import com.charlton.tilemap.models.Tile;
import com.charlton.tilemap.models.TileSet;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class AStarAlgorithmTest {


    @Test
    public void testPath() throws IOException {
        TileSet tile = TileSet.from(new File("/Users/admin/code/IdeaProjects/Game2020/src/assets/maps/collision_with_layer.json"));
        Point source = new Point(0, 0);
        Point dest = new Point(6 * tile.getTileWidth(), 9 * tile.getTileHeight());
        AStar<TileSet, Tile> aStarAlgorithm = new AStar<>(tile);
        Tile s = tile.get(source);
        Tile e = tile.get(dest);
        aStarAlgorithm.setStartNode(s);
        aStarAlgorithm.setEndNode(e);
        aStarAlgorithm.solve();
        System.out.println(aStarAlgorithm.getPath());
    }
}

