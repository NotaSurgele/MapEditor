package com.editor.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Brush {

    ArrayList<Sprite> sprites;
    ArrayList<BrushButton> buttons;
    Layer layer;

    int size;
    int width;
    int height;

    float startX = 0f;
    float startY = 0f;

    static float eraserX = 0f;
    static float eraserY = 0f;

    public static boolean isRectangle = false;
    public static boolean eraser = false;

    private boolean onButton = false;

    int bounds = 0;

    Sprite tile;
    int value;

    public Brush(int size, int width, int height) {
        this.size = size;
        this.sprites = new ArrayList<>();
        this.buttons = new ArrayList<>();
        layer = new Layer(width, height);
        this.width = width;
        this.height = height;
        this.tile = new Sprite();
    }

    private void drawSprite(float i, float j) {
        Sprite newTile = new Sprite(tile);
        newTile.setBounds(i, j, size, size);

        for (int index = 0; index != bounds; index++) {
            Sprite s = sprites.get(index);

            if (s.getX() == i && s.getY() == j)
                return;
        }
        layer.setLayerValue((int) i / size, (int) j / size, value);
        sprites.add(newTile);
        bounds++;
    }

    public void eraser(OrthographicCamera camera) {
        if (eraser) {
            float endX = camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x;
            float endY = camera.unproject(new Vector3(0, Gdx.input.getY(), 0)).y;

            float fixedStartX = eraserX - (eraserX % size);
            float fixedStartY = eraserY - (eraserY % size);
            float fixedEndX = endX + (endX % size);
            float fixedEndY = endY + (endY % size);

            for (int i = 0; i < bounds; i++) {
                Sprite s = sprites.get(i);

                if ((s.getX() >= fixedStartX && s.getX() <= fixedEndX) &&
                        (s.getY() >= fixedStartY && s.getY() <= fixedEndY)) {
                    layer.setLayerValue((int)s.getX() / size, (int)s.getY() / size, 0);
                    sprites.remove(i);
                    bounds--;
                }
            }
        }
    }

    private void rectangle(OrthographicCamera camera) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && !isRectangle && !onButton) {
            startX = camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x;
            startY = camera.unproject(new Vector3(0, Gdx.input.getY(), 0)).y;
            isRectangle = true;
        }

        if (isRectangle) {
            float endX = camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x;
            float endY = camera.unproject(new Vector3(0, Gdx.input.getY(), 0)).y;

            float fixedStartX = startX - (startX % size);
            float fixedStartY = startY - (startY % size);
            float fixedEndX = endX - (endX % size);
            float fixedEndY = endY - (endY % size);

            for (float i = fixedStartX; i <= fixedEndX; i += size) {
                for (float j = fixedStartY; j >= fixedEndY; j -= size) {
                    if ((i >= 0f && i < width * size) && (j >= 0f && j < height * size)) {
                        drawSprite(i, j);
                    }
                }
            }
        }
    }

    public void update(OrthographicCamera camera, SpriteBatch batch) {

        rectangle(camera);
        for (int i = 0; i != bounds; i++)
            sprites.get(i).draw(batch);
        for (int i = buttons.size() - 1; i >= 0; i--) {
            BrushButton b = buttons.get(i);

            if (b.contain(camera) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                tile = b.getSprite();
                value = b.getValue();
                onButton = true;
            } else
                onButton = false;
        }
        eraser(camera);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !onButton) {
            float x = camera.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x;
            float y = camera.unproject(new Vector3(0, Gdx.input.getY(), 0)).y;

            if ((x >= 0f && x <= width * size) && (y >= 0f && y <= height * size)) {
                float fixedX = x - (x % size);
                float fixedY = y - (y % size);

                Sprite newTile = new Sprite(tile);
                newTile.setBounds(fixedX, fixedY, size, size);

                for (int i = 0; i != bounds; i++) {
                    Sprite s = sprites.get(i);

                    if (fixedX == s.getX() && fixedY == s.getY())
                        return;
                }
                sprites.add(newTile);
                bounds++;
            }
        }
    }

    private void processData(String data) {
        String[] lines = data.split("\n");
        float x = 0;
        float y = 0;

        int end = lines.length;
        for (int i = 0; i != end; i++) {
            String[] datas = lines[i].split(":");

            if (i % 10 == 0) {
                y -= (size + 10f);
                x = 0f;
            }
            buttons.add(new BrushButton(datas[0], Integer.parseInt(datas[1]), size, x, y));
            x += size + 10f;
        }
        this.tile = buttons.get(0).getSprite();
        this.value = buttons.get(0).getValue();
    }

    public void loadPackage(String packageName) {
        File file = new File(packageName);
        String data = "";

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine())
                data += sc.nextLine() + "\n";

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (data == "")
            return;
        processData(data);
    }
}
