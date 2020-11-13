package com.charlton;

import com.charlton.game.display.Display;
import com.charlton.game.gfx.SpriteAssets;
import com.charlton.game.display.Camera;
import com.charlton.game.input.GameKeyManager;
import com.charlton.game.states.GameState;
import com.charlton.game.states.MenuState;
import com.charlton.game.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class Game implements Runnable {

    private Display display;
    private int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    //States
    private State gameState;
    private State menuState;

    //Input
    private GameKeyManager keyManager;

    //Camera
    private Camera gameCamera;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new GameKeyManager();
    }

    private void init() throws IOException {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        SpriteAssets.init();
        gameCamera = new Camera(this, 0, 0);
        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);
    }

    private void tick() {
        keyManager.tick();
        if (State.getState() != null)
            State.getState().tick();
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        if (State.getState() != null)
            State.getState().render(g);
        bs.show();
        g.dispose();
    }

    public void run() {

        try {
            init();
            int fps = 60;
            double timePerTick = 1000000000 / fps;
            double delta = 0;
            long now;
            long lastTime = System.nanoTime();
            long timer = 0;
            int ticks = 0;

            while (running) {
                now = System.nanoTime();
                delta += (now - lastTime) / timePerTick;
                timer += now - lastTime;
                lastTime = now;

                if (delta >= 1) {
                    tick();
                    render();
                    ticks++;
                    delta--;
                }

                if (timer >= 1000000000) {
                    System.out.println("Ticks and Frames: " + ticks);
                    ticks = 0;
                    timer = 0;
                }
            }
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameKeyManager getKeyManager() {
        return keyManager;
    }

    public Camera getGameCamera() {
        return gameCamera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
