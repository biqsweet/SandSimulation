package com.ran.mygame.cells.types;

import com.badlogic.gdx.graphics.Color;
import com.ran.mygame.cells.Element;

public class Empty extends Element {
    public Empty(int x, int y) {
        super(Color.BLACK, x, y);
        this.material = Material.EMPTY;
    }

    @Override
    public void applyGravity() {

    }

    @Override
    public Element cloneInto(int x, int y) {
        return new Empty(x, y);
    }


}
