package com.ran.mygame.cells.liquids;

import com.badlogic.gdx.graphics.Color;
import com.ran.mygame.cells.Element;
import com.ran.mygame.cells.types.Liquid;

public class Water extends Liquid {
    public Water(int x, int y) {
        super(Color.BLUE, x, y);
        this.material = Material.WATER;
    }

    @Override
    public Element cloneInto(int x, int y) {
        return new Water(x, y);
    }
}
