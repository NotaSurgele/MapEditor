package com.editor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class BrushButton {

    Sprite sprite;
    int value;
    Rectangle hitbox;
    float x;
    float y;

    public BrushButton(String path, int value, int size, float x, float y) {
        this.value = value;
        this.sprite = new Sprite(new Texture(path));
        this.sprite.setBounds(x, y, size, size);
        this.hitbox = new Rectangle(x, y, size, size);
        this.x = x;
        this.y = y;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public int getValue() {
        return this.value;
    }

    public boolean contain(OrthographicCamera camera) {
        float y = (-Gdx.input.getY() + (Gdx.graphics.getHeight() / 2f));
        float x = (Gdx.input.getX() - (Gdx.graphics.getWidth() / 2f));
        return this.hitbox.contains(x, y);
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.projection);
        this.sprite.setPosition(x - 250f, Panel.actualPosition.y + 250f);
        this.hitbox.setPosition(sprite.getX(), sprite.getY());
        this.sprite.draw(batch);
    }
}
