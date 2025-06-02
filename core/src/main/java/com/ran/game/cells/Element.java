package com.ran.game.cells;

import com.badlogic.gdx.graphics.Color;
import com.ran.game.cells.liquids.Liquid;
import com.ran.game.cells.solids.Solid;

import static com.ran.game.Main.GRID;
import static com.ran.game.Main.INPUT_HANDLER;

public abstract class Element {
    public final Color color;
    public final int spreadRate, id;
    public final boolean isMovable;
    public boolean movedThisFrame = false;
    protected int y, x;


    public Element(Color Color, int x, int y, int spreadRate, boolean isMovable, int id) {
        this.color = Color;
        this.x = x;
        this.y = y;
        this.spreadRate = spreadRate;
        this.isMovable = isMovable;
        this.id = id;
    }


    public boolean move(int dx, int dy) {
        if (movedThisFrame) return false;

        final int targetX = x + dx;
        final int targetY = y + dy;

        if (!INPUT_HANDLER.inBounds(targetX, targetY)) return false;

        final Element targetElement = GRID[targetX][targetY];

        if (targetElement instanceof Empty) {
            final Element movedElement = this.cloneInto(targetX, targetY);
            movedElement.movedThisFrame = true;

            GRID[targetX][targetY] = movedElement;
            GRID[x][y] = new Empty(x, y);

            return true;
        }

        if (targetElement instanceof Liquid && this instanceof Solid) {
            final Element solidMoved = this.cloneInto(targetX, targetY);
            final Element liquidMoved = targetElement.cloneInto(x, y);

            solidMoved.movedThisFrame = true;
            liquidMoved.movedThisFrame = true;

            GRID[targetX][targetY] = solidMoved;
            GRID[x][y] = liquidMoved;

            return true;
        }

        return false;
    }


    public abstract void applyGravity();

    public abstract Element cloneInto(int x, int y);
}
