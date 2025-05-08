package com.ran.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.ran.game.cells.Empty;
import com.ran.game.cells.liquids.Water;
import com.ran.game.cells.solids.Sand;
import com.ran.game.cells.solids.Stone;

import static com.ran.game.Main.GRID;
import static com.ran.game.cells.Element.insideCircle;

public class InputHandler implements InputProcessor {
    protected static int BRUSH_RADIUS = 5;
    protected static int elementToSpawn;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.NUM_1:
                elementToSpawn = 1;
                return true;
            case Input.Keys.NUM_0:
                elementToSpawn = 0;
                return true;
            case Input.Keys.NUM_2:
                elementToSpawn = 2;
                return true;
            case Input.Keys.NUM_3:
                elementToSpawn = 3;
                return true;
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
        final int x = screenX / 8;
        final int y = screenY / 8;
        if (!inBounds(x, y) || !GRID[x][y].isMovable) return false;
        spawnElement(x, y, elementToSpawn);
        return !(GRID[x][y] instanceof Empty);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        final int x = screenX / 8;
        final int y = screenY / 8;
        if (!inBounds(x, y) || !GRID[x][y].isMovable) return false;
        spawnElement(x, y, elementToSpawn);
        return !(GRID[x][y] instanceof Empty);
    }

    private void spawnElement(int x, int y, int id) {
        for (int i = x - BRUSH_RADIUS; i <= x + BRUSH_RADIUS; i++) {
            for (int j = y - BRUSH_RADIUS; j <= y + BRUSH_RADIUS; j++) {
                if (inBounds(i, j) && insideCircle(x, y, i, j, BRUSH_RADIUS) && GRID[i][j] instanceof Empty) {
                    switch (elementToSpawn) {
                        case 0:
                            GRID[i][j] = new Empty(i, j);
                            break;
                        case 1:
                            GRID[i][j] = new Sand(i, j);
                            break;
                        case 2:
                            GRID[i][j] = new Water(i, j);
                            break;
                        case 3:
                            GRID[i][j] = new Stone(i, j);
                            break;
                    }
                }
            }
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        BRUSH_RADIUS -= (int) amountY;
        if (BRUSH_RADIUS < 0) BRUSH_RADIUS = 0;
        return true;
    }

    public boolean inBounds(int x, int y) {
        return x > 0 && x < GRID.length && y > 0 && y < GRID[x].length;
    }
}
