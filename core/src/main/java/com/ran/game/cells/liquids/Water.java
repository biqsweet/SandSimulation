package com.ran.game.cells.liquids;

import com.badlogic.gdx.graphics.Color;
import com.ran.game.cells.Element;

public class Water extends Liquid {
    public Water(int x, int y) {
        super(Color.BLUE, x, y, 5, true, 2);
    }

    @Override
    public Element cloneInto(int x, int y) {
        return new Water(x, y);
    }
}
