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

import java.util.ArrayList;

public class SaveButton {

    private String name;
    private Sprite sprite;
    private Vector2 position;
    private Rectangle hitbox;
    private BitmapFont text;
    private boolean clicked = false;

    private ArrayList<Layer> layers;

    public SaveButton()
    {
        this.position = new Vector2(0, 0);
        this.name = "Save";
        this.sprite = new Sprite(new Texture("LayerButton.png"));
        this.sprite.setBounds(this.position.x, this.position.y, this.sprite.getRegionWidth(), this.sprite.getRegionHeight());
        this.hitbox = new Rectangle(this.position.x, this.position.y, this.sprite.getRegionWidth(), this.sprite.getRegionHeight());
        this.text = new BitmapFont();
    }

    public boolean isClicked()
    {
        float y = (-Gdx.input.getY() + (Gdx.graphics.getHeight() / 2f));
        float x = (Gdx.input.getX() - (Gdx.graphics.getWidth() / 2f));
        return this.clicked = this.hitbox.contains(x, y) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }

    public void setLayers(ArrayList<Layer> layers)
    {
        this.layers = layers;
    }

    private void saveData()
    {
        System.out.println("Save");
        this.clicked = false;
    }

    public void update(SpriteBatch batch)
    {
        OrthographicCamera camera = MapEditor.camera;
        batch.setProjectionMatrix(camera.projection);

        this.sprite.setPosition(LayerPanel.actualPosition.x + this.position.x + 45f, this.position.y - 250f);
        this.sprite.draw(batch);
        this.hitbox.setPosition(this.sprite.getX(), this.sprite.getY());
        this.text.draw(batch, this.name, LayerPanel.actualPosition.x + this.position.x + 175, this.position.y - 175f);
        if (clicked == true)
            saveData();
    }



}
