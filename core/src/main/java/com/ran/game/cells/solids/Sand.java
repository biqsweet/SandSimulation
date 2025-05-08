package com.ran.game.cells.solids;


import com.badlogic.gdx.graphics.Color;
import com.ran.game.cells.Element;

public class Sand extends Solid {
    public Sand(int x, int y) {
        super(Color.YELLOW, x, y, 3, true, 1);
    }

    @Override
    public Element cloneInto(int x, int y) {
        return new Sand(x, y);
    }

}
