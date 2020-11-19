package com.charlton.game.display;

public class GlobalCamera extends BaseCamera {

    public static boolean DEBUG = false;
    protected static GlobalCamera camera;

    private int vx, vy;
    private int ay, av;

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

    public GlobalCamera(int x_origin, int y_origin) {
        super(x_origin, y_origin);
    }


    public void update() {

    }

}