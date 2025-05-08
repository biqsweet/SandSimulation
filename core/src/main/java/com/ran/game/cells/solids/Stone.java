package com.ran.game.cells.solids;

import com.badlogic.gdx.graphics.Color;
import com.ran.game.cells.Element;


public class Stone extends Solid {
    public Stone(int x, int y) {
        super(Color.DARK_GRAY, x, y, 0, false, 3);
    }

    @Override
    public void applyGravity() {
    }

    @Override
    public Element cloneInto(int x, int y) {
        return new Stone(x, y);
    }
}
