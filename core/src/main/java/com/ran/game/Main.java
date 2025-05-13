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
    public static final Element[][] GRID = new Element[300][300];
    public static final InputHandler INPUT_HANDLER = new InputHandler();
    public static final OrthographicCamera CAMERA = new OrthographicCamera();
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int CELL_SIZE;
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
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        CELL_SIZE = Math.min(SCREEN_WIDTH / GRID.length, SCREEN_HEIGHT / GRID[0].length);

        CAMERA.setToOrtho(true, GRID.length * CELL_SIZE, GRID[0].length * CELL_SIZE);
        SHAPE_RENDERER = new ShapeRenderer();
        SHAPE_RENDERER.setProjectionMatrix(CAMERA.combined);

        Gdx.input.setInputProcessor(INPUT_HANDLER);

        fillGrid();
        for (int i = 30; i < GRID.length / 2 + 30; i++) {
            GRID[i][GRID.length / 2] = new Stone(GRID.length / 2, i);
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

        for (int x = 0; x < GRID.length - 1; x++) {
            for (int y = 0; y < GRID[x].length - 1; y++) {
//                    for (int x = GRID.length - 1; x >= 0; x--) {
//                for (int y = GRID[x].length - 1; y >= 0; y--) {
                if (!(GRID[x][y] instanceof Empty)) {
                    SHAPE_RENDERER.setColor(GRID[x][y].color);
                    SHAPE_RENDERER.rect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE , CELL_SIZE );
                }
            }
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) SHAPE_RENDERER.end();
    }

    private void logic() {
        FALL_TIMER += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (FALL_TIMER >= 0.03) {
            FALL_TIMER = 0;
            boolean leftToRight = Math.random() < 0.5;
            for (int y = GRID[0].length - 1; y >= 0; y--) {
                if (leftToRight) {
                    for (int x = 0; x < GRID.length; x++) {
                        if (GRID[x][y].isMovable) GRID[x][y].applyGravity();
                    }
                } else {
                    for (int x = GRID.length - 1; x >= 0; x--) {
                        if (GRID[x][y].isMovable) GRID[x][y].applyGravity();
                    }
                }
            }
            for (int y = GRID[0].length - 1; y >= 0; y--) {
                for (int x = 0; x < GRID.length; x++) {
                    GRID[x][y].movedThisFrame = false;
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) fillGrid();
        }
    }
}
