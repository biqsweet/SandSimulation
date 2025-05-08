package com.ran.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ran.game.cells.Element;
import com.ran.game.cells.Empty;
import com.ran.game.cells.solids.Stone;

public class Main extends ApplicationAdapter {
    public static final Element[][] GRID = new Element[100][100];
    public static final InputHandler INPUT_HANDLER = new InputHandler();
    public static final OrthographicCamera CAMERA = new OrthographicCamera();
    private ShapeRenderer SHAPE_RENDERER;
    private double FALL_TIMER = 0;

    private static void fillGrid() {
        for (int x = GRID.length - 1; x >= 0; x--) {
            for (int y = GRID[x].length - 1; y >= 0; y--) {
                GRID[x][y] = new Empty(x, y);
            }
        }
    }

    @Override
    public void create() {
        CAMERA.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        SHAPE_RENDERER = new ShapeRenderer();
        SHAPE_RENDERER.setProjectionMatrix(CAMERA.combined);

        Gdx.input.setInputProcessor(INPUT_HANDLER);

        fillGrid();
        for (int i = 30; i < 60; i++) {
            GRID[i][40] = new Stone(40, i);
        }
    }

    @Override
    public void render() {
        draw();
        logic();
    }

    @Override
    public void dispose() {
        SHAPE_RENDERER.dispose();
    }

    private void draw() {
        ScreenUtils.clear(0f, 0f, 0f, 0f);
        SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = GRID.length - 1; x > 0; x--) {
            for (int y = GRID[x].length - 1; y > 0; y--) {
                SHAPE_RENDERER.setColor(GRID[x][y].getColor());
                SHAPE_RENDERER.rect(x * 8 + 1, y * 8 + 1, 7, 7);
            }
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) SHAPE_RENDERER.end();
    }

    private void logic() {
        FALL_TIMER += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (FALL_TIMER >= 0.03) {
            FALL_TIMER = 0;

            for (int x = GRID.length - 1; x >= 0; x--) {
                for (int y = GRID[x].length - 1; y >= 0; y--) {
                    if (GRID[x][y].isMovable)
                        GRID[x][y].applyGravity();
                }
            }
            for (int x = GRID.length - 1; x >= 0; x--) {
                for (int y = GRID[x].length - 1; y >= 0; y--) {
                    GRID[x][y].movedThisFrame = false;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) fillGrid();
        }
    }
}
