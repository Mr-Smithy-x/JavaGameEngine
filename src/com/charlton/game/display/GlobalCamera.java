package com.charlton.game.display;

public class GlobalCamera extends BaseCamera {

    public static boolean DEBUG = false;
    protected static GlobalCamera camera;

    private float vx, vy;
    private float ay, av;

    protected int GRAVITY = 1;
    protected int scaling = 4;


    public int getScaling() {
        return scaling;
    }


    public void setScaling(int scaling) {
        this.scaling = scaling;
    }

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