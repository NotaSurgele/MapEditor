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
        CLOSE
    }

    Vector2 openPosition;
    Vector2 closedPosition;
    public static Vector2 actualPosition;
    Texture t;

    float speed = 500f;
    public static State state = State.CLOSE;

    private boolean show = false;

    public Panel()
    {
        openPosition = new Vector2(-640f, -500f);
        closedPosition = new Vector2(-640f, -800f);
        actualPosition = closedPosition.cpy();
        t = new Texture("Panel.png");
    }

    public void update(SpriteBatch batch, ArrayList<BrushButton> buttons)
    {
        batch.setProjectionMatrix(camera.projection);
        if (state == State.CLOSE) {
            if (actualPosition.y >= closedPosition.y)
                actualPosition.y -= speed * Gdx.graphics.getDeltaTime();
            else
                this.show = false;
        }
        if (state == State.OPEN) {
            this.show = true;
            if (actualPosition.y <= openPosition.y)
                actualPosition.y += speed * Gdx.graphics.getDeltaTime();
        }
        if (this.show)
            batch.draw(t, actualPosition.x, actualPosition.y, 1280, 400);
        batch.setProjectionMatrix(camera.combined);
        if (this.show)
            for (int i = 0; i != buttons.size(); i++)
                buttons.get(i).render(batch, camera);
    }
}
