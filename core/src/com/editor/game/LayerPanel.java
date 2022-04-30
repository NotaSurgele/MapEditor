package com.editor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static com.editor.game.MapEditor.camera;

public class LayerPanel {

    Sprite sprite;
    Vector2 openPosition;
    Vector2 closePosition;
    Texture t;
    ArrayList<LayerButton> buttons;
    SaveButton save;
    public static int currentLayer = 0;

    BitmapFont selectedLayer;
    final String[] layerName = {
        "Non Collider",
        "Collider"
    };


    public static Vector2 actualPosition;

    float speed = 600f;

    private boolean show = false;

    enum State {
        OPEN,
        CLOSE
    }

    public static State state = State.CLOSE;

    public LayerPanel()
    {
        this.openPosition = new Vector2(-640f, 360f);
        this.closePosition = new Vector2(-1040f, -360f);
        this.actualPosition = closePosition.cpy();
        this.t = new Texture("LayerPanel.png");
        this.sprite = new Sprite(t);
        this.sprite.setBounds(actualPosition.x, actualPosition.y, t.getWidth(), t.getHeight());
        this.buttons = new ArrayList<>();
        this.buttons.add(new LayerButton(new Texture("LayerButton.png"), new Vector2(-330, 700), "Non Collider", 0));
        this.buttons.add(new LayerButton(new Texture("LayerButton.png"), new Vector2(-330, 500f), "Collider", 1));
        this.selectedLayer = new BitmapFont();
        this.selectedLayer.setColor(Color.RED);
        this.save = new SaveButton();
    }

    public int getCurrentLayer() { return this.currentLayer; }

    public void update(SpriteBatch batch, Brush brush)
    {
        batch.setProjectionMatrix(camera.projection);
        if (state == State.CLOSE) {
            if (actualPosition.x >= closePosition.x)
                actualPosition.x -= speed * Gdx.graphics.getDeltaTime();
            else
                this.show = false;
        }
        if (state == State.OPEN) {
            this.show = true;
            if (actualPosition.x <= openPosition.x) {
                actualPosition.x += speed * Gdx.graphics.getDeltaTime();
            }
        }
        if (this.show == true) {
            batch.draw(t, actualPosition.x, actualPosition.y, 400, 720);
            this.selectedLayer.draw(batch, "Selected layer: " + this.layerName[this.currentLayer], LayerPanel.actualPosition.x + 100f, -300f);
        }
        this.sprite.setPosition(actualPosition.x, actualPosition.y);
        batch.setProjectionMatrix(camera.combined);
        if (this.show == true) {
            for (int i = 0; i != buttons.size(); i++) {
                buttons.get(i).update(batch);
                if (buttons.get(i).isClicked()) {
                    this.currentLayer = buttons.get(i).getLayerValue();
                }
            }
            this.save.update(batch);
            if (this.save.isClicked())
                this.save.setLayers(brush.layers);
        }
    }
}
