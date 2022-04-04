package com.editor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

import static com.editor.game.MapEditor.camera;

public class OwnInput implements InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == 45) {

            switch (Panel.state) {
                case CLOSE:
                    Panel.state = Panel.State.OPEN;
                    break;
                case OPEN:
                    Panel.state = Panel.State.CLOSE;
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != 2)
            return false;
        Brush.eraserX = camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x;
        Brush.eraserY = camera.unproject(new Vector3(0, Gdx.input.getY(), 0)).y;
        Brush.eraser = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Brush.isRectangle = false;
        Brush.eraser = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        camera.zoom += amountY * .2f;
        return false;
    }
}
