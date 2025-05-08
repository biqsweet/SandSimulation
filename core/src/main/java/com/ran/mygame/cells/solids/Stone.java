package com.ran.mygame.cells.solids;

import com.badlogic.gdx.graphics.Color;
import com.ran.mygame.cells.Element;
import com.ran.mygame.cells.types.Solid;


public class Stone extends Solid {
    public Stone(int x, int y) {
        super(Color.DARK_GRAY, x, y);
        this.material = Material.STONE;
    }

    @Override
    public void applyGravity() {
    }

    @Override
    public Element cloneInto(int x, int y) {
        return new Stone(x, y);
    }
}
