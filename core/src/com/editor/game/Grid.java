package com.editor.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Grid {

    int width = 0;
    int height = 0;
    int size = 0;

    ShapeRenderer[][] renderer;

    public Grid(int width, int height, int size) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.renderer = new ShapeRenderer[width][height];
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++) {
                this.renderer[i][j] = new ShapeRenderer();
            }
        }
    }

    public void update(SpriteBatch batch, OrthographicCamera camera) {
        float x = 0;
        float y = 0;

        batch.end();
        for (int i = 0; i != width; i++) {
            for (int j = 0; j != height; j++) {
                renderer[i][j].setProjectionMatrix(camera.combined);
                renderer[i][j].begin(ShapeRenderer.ShapeType.Line);
                renderer[i][j].setColor(Color.WHITE);
                renderer[i][j].rect(x, y, this.size, this.size);
                renderer[i][j].end();
                x += this.size;
            }
            x = 0f;
            y += this.size;
        }
        batch.begin();
    }
}
