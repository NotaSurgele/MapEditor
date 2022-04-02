package com.editor.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class MapEditor extends ApplicationAdapter {
	SpriteBatch batch;
	public static OrthographicCamera camera;
	Grid grid;
	Brush brush;

	boolean isLock = true;
	final float speed = 500f;
	final float offset = 100f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		grid = new Grid(50, 50, 32);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		brush = new Brush(32, 50, 50);
		brush.loadPackage("farm.package");
		Gdx.input.setInputProcessor(new OwnInput());
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		if (!isLock) {
			float x = Gdx.input.getX();
			float y = Gdx.input.getY();

			if (x >= (Gdx.graphics.getWidth() - offset))
				camera.position.set(new Vector2(camera.position.x + (speed * camera.zoom) * Gdx.graphics.getDeltaTime(), camera.position.y), 0);
			if (x <= offset)
				camera.position.set(new Vector2(camera.position.x - (speed * camera.zoom) * Gdx.graphics.getDeltaTime(), camera.position.y), 0);
			if (y <= offset)
				camera.position.set(new Vector2(camera.position.x, camera.position.y + (speed * camera.zoom) * Gdx.graphics.getDeltaTime()), 0);
			if (y >= (Gdx.graphics.getHeight() - offset))
				camera.position.set(new Vector2(camera.position.x, camera.position.y - (speed * camera.zoom) * Gdx.graphics.getDeltaTime()), 0);
		}

		isLock = Gdx.input.isKeyJustPressed(Input.Keys.SPACE) != isLock;
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		camera.update();
		brush.update(camera, batch);
		grid.update(batch, camera);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
