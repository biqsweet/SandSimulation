package com.ran.game.cells;

import com.badlogic.gdx.graphics.Color;

public class Empty extends Element {
    public Empty(int x, int y) {
        super(Color.BLACK, x, y, 0, true, 0);
    }

    @Override
    public void applyGravity() {

    }

    @Override
    public Element cloneInto(int x, int y) {
        return new Empty(x, y);
    }


}
