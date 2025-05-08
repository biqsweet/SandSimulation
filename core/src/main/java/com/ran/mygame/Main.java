package com.ran.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ran.mygame.cells.Element;
import com.ran.mygame.cells.Element.Material;
import com.ran.mygame.cells.liquids.Water;
import com.ran.mygame.cells.solids.Stone;
import com.ran.mygame.cells.types.Empty;

import java.util.Random;

import static com.ran.mygame.cells.Element.insideCircle;

public class Main extends ApplicationAdapter {
    public static final Element[][] grid = new Element[100][100];
    public static final Random rnd = new Random();
    private ShapeRenderer shapeRenderer;
    private float fallTimer = 0f;

    @Override
    public void create() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        for (int x = grid.length - 1; x >= 0; x--) {
            for (int y = grid[x].length - 1; y >= 0; y--) {
                grid[x][y] = new Empty(x, y);
            }
        }
        for (int i = 30; i < 60; i++) {
            grid[i][40] = new Stone(40, i);
        }
    }

    @Override
    public void render() {
        draw();
        input();
        logic();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void input() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            final int x = Gdx.input.getX() / 8;
            final int y = Gdx.input.getY() / 8;

            final boolean inBounds = x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
            if (inBounds && !(grid[x][y] instanceof Stone)) {
                int radius = 5;
                for (int i = x - radius; i <= x + radius; i++) {
                    for (int j = y - radius; j <= y + radius; j++) {
                        if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
                            if (insideCircle(x, y, i, j, radius)) {
                                if (grid[i][j] instanceof Empty) {
                                    grid[i][j] = new Water(i, j);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(0f, 0f, 0f, 0f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = grid.length - 1; x > 0; x--) {
            for (int y = grid[x].length - 1; y > 0; y--) {
                shapeRenderer.setColor(grid[x][y].getColor());
                shapeRenderer.rect(x * 8 + 1, y * 8 + 1, 7, 7);
            }
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) shapeRenderer.end();
    }

    private void logic() {
        fallTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (fallTimer >= 0.03f) {
            fallTimer = 0f;
            for (int x = grid.length - 1; x >= 0; x--) {
                for (int y = grid[x].length - 1; y >= 0; y--) {
                    grid[x][y].movedThisFrame = false;
                }
            }
            for (int x = grid.length - 1; x >= 0; x--) {
                for (int y = grid[x].length - 1; y >= 0; y--) {
                    if (!grid[x][y].movedThisFrame && grid[x][y].getElement() != Material.EMPTY)
                        grid[x][y].applyGravity();
                }
            }
        }
    }
}
