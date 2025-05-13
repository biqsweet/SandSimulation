package com.ran.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.ran.game.cells.Empty;
import com.ran.game.cells.liquids.Water;
import com.ran.game.cells.solids.Sand;
import com.ran.game.cells.solids.Stone;

import static com.ran.game.Main.*;

public class InputHandler implements InputProcessor {
    protected static int BRUSH_SIZE = 5;
    protected static int ELEMENT_TYPE = 2;
    protected static char SHAPE = 's';

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.NUM_1:
                ELEMENT_TYPE = 1;
                return true;
            case Input.Keys.NUM_0:
                ELEMENT_TYPE = 0;
                return true;
            case Input.Keys.NUM_2:
                ELEMENT_TYPE = 2;
                return true;
            case Input.Keys.NUM_3:
                ELEMENT_TYPE = 3;
                return true;
            case Input.Keys.S:
                SHAPE = Input.Keys.S;
                return true;
            case Input.Keys.C:
                SHAPE = Input.Keys.C;
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
        return handleTouch(screenX, screenY);
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
        return handleTouch(screenX, screenY);
    }

    private boolean handleTouch(int screenX, int screenY) {
        final Vector3 coords = CAMERA.unproject(new Vector3(screenX, screenY, 0));

        final int x = (int) coords.x / CELL_SIZE;
        final int y = (int) coords.y / CELL_SIZE;
        if (!inBounds(x, y) || !GRID[x][y].isMovable) return false;

        if (SHAPE == Input.Keys.C) spawnCircle(x, y);
        else spawnSquare(x, y);

        return true;
    }

    private void spawnElement(int x, int y) {
        switch (ELEMENT_TYPE) {
            case 1:
                GRID[x][y] = new Sand(x, y);
                break;
            case 2:
                GRID[x][y] = new Water(x, y);
                break;
            case 3:
                GRID[x][y] = new Stone(x, y);
                break;
            default:
                GRID[x][y] = new Empty(x, y);
                break;
        }
    }

    private void spawnCircle(int x, int y) {
        for (int i = x - BRUSH_SIZE; i <= x + BRUSH_SIZE; i++) {
            for (int j = y - BRUSH_SIZE; j <= y + BRUSH_SIZE; j++) {
                if (inBounds(i, j) && insideCircle(x, y, i, j, BRUSH_SIZE) && (ELEMENT_TYPE == 0 || GRID[i][j].isMovable)) {
                    spawnElement(i, j);
                }
            }
        }
    }

    private void spawnSquare(int x, int y) {
        for (int i = x - BRUSH_SIZE; i <= x + BRUSH_SIZE; i++) {
            for (int j = y - BRUSH_SIZE; j <= y + BRUSH_SIZE; j++) {
                if (inBounds(i, j) && (ELEMENT_TYPE == 0 || GRID[i][j].isMovable)) {
                    spawnElement(i, j);
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
        BRUSH_SIZE -= (int) amountY;
        if (BRUSH_SIZE < 0) BRUSH_SIZE = 0;
        return true;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < GRID.length && y >= 0 && y < GRID[x].length;
    }
    private boolean insideCircle(int centerX, int centerY, int x, int y, double radius) {
        final double dx = centerX - x;
        final double dy = centerY - y;
        return dx * dx + dy * dy <= radius * radius;
    }
}
