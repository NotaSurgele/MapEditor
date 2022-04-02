package com.editor.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Grid {

    int width = 0;
    int height = 0;
    int size = 0;

    int W_LINE_SIZE = 0;
    int H_LINE_SIZE = 0;

    ShapeRenderer[] vertical;
    ShapeRenderer[] horizontal;

    public Grid(int width, int height, int size) {
        this.width = width;
        this.height = height;
        this.size = size;
        vertical = new ShapeRenderer[width + 1];
        horizontal = new ShapeRenderer[height + 1];
        for (int i = 0; i <= width; i++)
            vertical[i] = new ShapeRenderer();
        for (int i = 0; i <= height; i++)
            horizontal[i] = new ShapeRenderer();
        W_LINE_SIZE = (size * width);
        H_LINE_SIZE = (size * height);
    }

    public void update(SpriteBatch batch, OrthographicCamera camera) {
        float x = 0;
        float y = 0;

        batch.end();
        for (int i = 0; i <= width; i++) {
            vertical[i].setProjectionMatrix(camera.combined);
            vertical[i].begin(ShapeRenderer.ShapeType.Line);
            vertical[i].setColor(Color.WHITE);
            vertical[i].line(W_LINE_SIZE, y, 0, y);
            vertical[i].end();
            y += 32;
        }
        for (int i = 0; i <= height; i++) {
            horizontal[i].setProjectionMatrix(camera.combined);
            horizontal[i].begin(ShapeRenderer.ShapeType.Line);
            horizontal[i].setColor(Color.WHITE);
            horizontal[i].line(x, H_LINE_SIZE, x, 0);
            horizontal[i].end();
            x += 32;
        }
        batch.begin();
    }
}
