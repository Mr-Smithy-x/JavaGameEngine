package com.charlton;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        GameF20 f = null;
        try {
            f = new GameF20();
            f.init();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
//(w * y + x) // linear & (x, y)