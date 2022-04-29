package com.editor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class LayerButton {

    private String name;
    private Sprite sprite;
    private Vector2 position;
    private Rectangle hitbox;
    BitmapFont text;

    int layerValue;

    public LayerButton(Texture t, Vector2 position, String n, int value)
    {
        this.position = position;
        this.name = n;
        this.sprite = new Sprite(t);
        this.sprite.setBounds(this.position.x, this.position.y, t.getWidth(), t.getHeight());
        this.layerValue = value;
        this.hitbox = new Rectangle(this.position.x, this.position.y, t.getWidth(), t.getHeight());
        this.text = new BitmapFont();
    }

    public boolean isClicked()
    {
        float y = (-Gdx.input.getY() + (Gdx.graphics.getHeight() / 2f));
        float x = (Gdx.input.getX() - (Gdx.graphics.getWidth() / 2f));
        return this.hitbox.contains(x, y) && Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    public int getLayerValue() { return this.layerValue; }

    public void update(SpriteBatch batch)
    {
        OrthographicCamera camera = MapEditor.camera;
        batch.setProjectionMatrix(camera.projection);

        this.sprite.setPosition(LayerPanel.actualPosition.x + this.position.x + 375f, this.position.y - 550f);
        this.sprite.draw(batch);
        this.hitbox.setPosition(this.sprite.getX(), this.sprite.getY());
        this.text.draw(batch, this.name,LayerPanel.actualPosition.x + this.position.x + 500f, this.position.y - 470f);
    }
}
