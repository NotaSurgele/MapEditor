package com.editor.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class MapEditor extends ApplicationAdapter {
	SpriteBatch batch;
	Grid grid;
	Brush brush;
	Panel panel;
	LayerPanel layerPanel;

	public static OrthographicCamera camera;
	boolean isLock = true;
	final float speed = 500f;
	final float offset = 100f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		grid = new Grid(50, 50, 32);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		brush = new Brush(32, Grid.width, Grid.height);
		brush.loadPackage("farm.package");
		panel = new Panel();
		layerPanel = new LayerPanel();
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
		grid.update(batch, camera);
		brush.update(camera, batch);
		panel.update(batch, brush.buttons);
		layerPanel.update(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
