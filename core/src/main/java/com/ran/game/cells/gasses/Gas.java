package com.ran.game.cells.gasses;

import com.badlogic.gdx.graphics.Color;
import com.ran.game.cells.Element;

public abstract class Gas extends Element {
    public Gas(Color color, int x, int y, int spreadRate, boolean isMovable, int id) {
        super(color, x, y, spreadRate, isMovable, id);
    }

    @Override
    public void applyGravity() {
        if (!move(0, -1)) {
            if (!move(-1, -1))
                move(1, -1);
        }
    }
}
