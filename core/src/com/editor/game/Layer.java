package com.editor.game;

import java.util.ArrayList;

public class Layer {

    int[][] layer;
    int width;
    int height;

    public Layer(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.layer = new int[width][height];
        initLayer(width, height);
    }

    public void displayLyer()
    {
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++)
                System.out.print(layer[i][j]);
            System.out.println();
        }
        System.out.println("________________________________________________");
    }

    private void initLayer(int width, int height)
    {
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++)
                this.layer[i][j] = 0;
        }
    }

    public void setLayerValue(int i, int j, int value)
    {
        this.layer[i][j] = value;
    }

}
