package com.editor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static com.editor.game.MapEditor.camera;

public class Panel {

    enum State {
        OPEN,
        CLOSE,
        UNDEFINED
    }


    Vector2 openPosition;
    Vector2 closedPosition;
    public static Vector2 actualPosition;
    Texture t;

    float speed = 500f;
    public static State state = State.CLOSE;

    public Panel()
    {
        openPosition = new Vector2(-400f, -500f);
        closedPosition = new Vector2(-400f, -800f);
        actualPosition = closedPosition.cpy();
        t = new Texture("Panel.png");
    }

    public void update(SpriteBatch batch, ArrayList<BrushButton> buttons)
    {
        batch.setProjectionMatrix(camera.projection);
        if (state == State.CLOSE) {
            if (actualPosition.y >= closedPosition.y)
                actualPosition.y -= speed * Gdx.graphics.getDeltaTime();
        }
        if (state == State.OPEN) {
            if (actualPosition.y <= openPosition.y)
                actualPosition.y += speed * Gdx.graphics.getDeltaTime();
        }
        batch.draw(t, actualPosition.x, actualPosition.y);
        batch.setProjectionMatrix(camera.combined);
        for (int i = 0; i != buttons.size(); i++)
            buttons.get(i).render(batch, camera);
    }
}
