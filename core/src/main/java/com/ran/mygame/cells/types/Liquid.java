package com.ran.mygame.cells.types;

import com.badlogic.gdx.graphics.Color;
import com.ran.mygame.cells.Element;

import static com.ran.mygame.Main.grid;
import static com.ran.mygame.Main.rnd;

public abstract class Liquid extends Element {
    public Liquid(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public void applyGravity() {
        if (y < grid.length - 1 && grid[x][y + 1] instanceof Empty)
            this.moveInto(grid[x][y + 1]);
        else {
            for (int step = 0; step < 5; step++) {
                if (rnd.nextBoolean()) {
                    if (x - step >= 0 && grid[x - step][y] instanceof Empty)
                        this.moveInto(grid[x - step][y]);
                } else if (x + step < grid.length && grid[x + step][y] instanceof Empty)
                    this.moveInto(grid[x + step][y]);
            }
        }
    }
}
