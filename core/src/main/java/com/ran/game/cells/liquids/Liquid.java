package com.ran.game.cells.liquids;

import com.badlogic.gdx.graphics.Color;
import com.ran.game.cells.Element;

public abstract class Liquid extends Element {
    public Liquid(Color color, int x, int y, int spreadRate, boolean isMovable, int id) {
        super(color, x, y, spreadRate, isMovable, id);
    }

    @Override
    public void applyGravity() {
        if (!move(0, 1)) {
            if (!movedThisFrame) {
                for (int i = 0; i < spreadRate; i++) {
                    if (Math.random() < 0.5) move(-1, 0);
                    else move(1, 0);
                }
            }
        }
    }
}

