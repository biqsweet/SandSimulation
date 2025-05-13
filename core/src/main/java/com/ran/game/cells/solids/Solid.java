package com.ran.game.cells.solids;

import com.badlogic.gdx.graphics.Color;
import com.ran.game.cells.Element;


public abstract class Solid extends Element {
    public Solid(Color color, int x, int y, int spreadRate, boolean isMovable, int id) {
        super(color, x, y, spreadRate, isMovable, id);
    }

    @Override
    public void applyGravity() {
        if (move(0, 1)) return;

        final boolean tryLeftFirst = Math.random() < 0.5;

        for (int i = 0; i < spreadRate; i++) {
            if (tryLeftFirst) {
                if (move(-1, 1) || move(1, 1)) return;
            } else {
                if (move(1, 1) || move(-1, 1)) return;
            }
        }
    }
}
