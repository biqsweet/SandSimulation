package com.ran.mygame.cells;

import com.badlogic.gdx.graphics.Color;
import com.ran.mygame.cells.types.Empty;

import static com.ran.mygame.Main.grid;

public abstract class Element {
    protected final Color color;
    public boolean movedThisFrame = false;
    protected int y, x;
    protected Material material;

    public Element(Color Color, int x, int y) {
        this.color = Color;
        this.x = x;
        this.y = y;
    }

    public static boolean insideCircle(int cx, int cy, int x, int y, double radius) {
        final double dx = cx - x;
        final double dy = cy - y;
        return dx * dx + dy * dy <= radius * radius;
    }

    public void moveInto(Element target) {
        if (grid[target.x][target.y] instanceof Empty) {
            grid[target.x][target.y] = this.cloneInto(target.x, target.y);
            grid[this.x][this.y] = new Empty(this.x, this.y);
            grid[target.x][target.y].movedThisFrame = true;
            this.movedThisFrame = true;
        }
    }

    public Color getColor() {
        return color;
    }

    public Material getElement() {
        return material;
    }

    public abstract void applyGravity();

    public abstract Element cloneInto(int x, int y);

    public enum Material {
        SAND, STONE, WATER, EMPTY
    }
}
