package com.charlton.game.states;

import com.charlton.Game;
import com.charlton.game.models.Player;
import com.charlton.game.world.World;

import java.awt.*;
import java.io.IOException;

public class GameState extends State {

    private Player player;
    private World world;

    public GameState(Game game) throws IOException {
        super(game);
        player = new Player(game, 0, 0);
        world = new World(game, "collision_with_layer.json");
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void tick() {
        world.tick();
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
    }

}
