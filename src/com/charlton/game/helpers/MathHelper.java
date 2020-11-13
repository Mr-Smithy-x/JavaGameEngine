package com.charlton.game.helpers;

public class MathHelper {

    public float newWidth(float newHeight, float aspectRatio) {
        return newHeight * aspectRatio;
    }

    public float newHeight(float newWidth, float aspectRatio) {
        return newWidth / aspectRatio;
    }

    public float getAspectRatio(float oldWidth, float oldHeight) {
        return oldWidth / oldHeight;
    }
}
