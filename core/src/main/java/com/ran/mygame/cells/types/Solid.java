package com.ran.mygame.cells.types;

import com.badlogic.gdx.graphics.Color;
import com.ran.mygame.cells.Element;

import static com.ran.mygame.Main.grid;
import static com.ran.mygame.Main.rnd;

public abstract class Solid extends Element {
    public Solid(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public void applyGravity() {
        if (y <= 0) return;

        if (grid[x][y - 1] instanceof Empty)
            this.moveInto(grid[x][y - 1]);
        else if (rnd.nextBoolean()) {
            if (x > 0 && grid[x - 1][y - 1] instanceof Empty)
                this.moveInto(grid[x - 1][y - 1]);
        } else if (x < grid.length - 1 && grid[x + 1][y - 1] instanceof Empty)
            this.moveInto(grid[x + 1][y - 1]);
    }
}
