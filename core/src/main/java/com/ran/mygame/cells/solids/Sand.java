package com.ran.mygame.cells.solids;


import com.badlogic.gdx.graphics.Color;
import com.ran.mygame.cells.Element;
import com.ran.mygame.cells.types.Solid;

public class Sand extends Solid {
    public Sand(int x, int y) {
        super(Color.YELLOW, x, y);
        this.material = Material.SAND;
    }

    @Override
    public Element cloneInto(int x, int y) {
        return new Sand(x, y);
    }

}
