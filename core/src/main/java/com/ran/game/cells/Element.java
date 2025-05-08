package com.ran.game.cells;

import com.badlogic.gdx.graphics.Color;

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

    public static boolean insideCircle(int centerX, int centerY, int x, int y, double radius) {
        final double dx = centerX - x;
        final double dy = centerY - y;
        return dx * dx + dy * dy <= radius * radius;
    }

    public boolean move(int x, int y) {
        if (INPUT_HANDLER.inBounds(this.x + x, this.y + y)) {
            if (GRID[this.x + x][this.y + y] instanceof Empty) {
                GRID[this.x + x][this.y + y] = cloneInto(this.x + x, this.y + y);
                GRID[this.x][this.y] = new Empty(x, y);
//                GRID[this.x][this.y].movedThisFrame = true;
                GRID[this.x + x][this.y + y].movedThisFrame = true;
                return true;
            }
        }
        return false;
    }

    public Color getColor() {
        return color;
    }

    public abstract void applyGravity();

    public abstract Element cloneInto(int x, int y);
}
