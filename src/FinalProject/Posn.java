package FinalProject;

// Final project
// Landegger Theo
// turtle
// Lough Ryan
// rlough

/**
 * Represents a Posn
 * @author Ryan
 * @author Theo
 */
public class Posn {
    int x;
    int y;
    /**
     * Constructs a Posn
     * @param x
     * @param y
     */
    public Posn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Is this Posn equal to the given?
     */
    public boolean equals(Object o) {
        if (o instanceof Posn) {
            return this.x == ((Posn)o).x &&
                    this.y == ((Posn)o).y;
        }
        else {
            return false;
        }
    }
}