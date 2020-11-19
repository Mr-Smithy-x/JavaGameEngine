package com.charlton.game.display;

public class GlobalCamera extends BaseCamera {

    public static boolean DEBUG = true;
    protected static GlobalCamera camera;

    private float vx, vy;
    private float ay, av;

    protected int GRAVITY = 1;


    public static GlobalCamera getInstance(){
        if(camera == null){
            camera = new GlobalCamera();
        }
        return camera;
    }

    public GlobalCamera(){
        this(0,0);
    }

    public GlobalCamera(float x_origin, float y_origin) {
        super(x_origin, y_origin);
    }


    public void update() {

    }

}