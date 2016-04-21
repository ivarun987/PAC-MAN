package PACMAN;
import java.awt.Graphics;

public class Pellet implements Comparable<Pellet>{
    
    public int pos_x;
    public int pos_y;
    public int radius;
    public boolean isEnergizer;
    
    public Pellet(int pos_x, int pos_y, int radius, boolean isEnergizer) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.radius = radius;
        this.isEnergizer = isEnergizer;
    }
    
    public boolean intersects(PacMan pacman) {
        boolean ans = (pacman.pos_x == this.pos_x) &&
                (pacman.pos_y == this.pos_y);
        return ans;
    }
    
    
    /**
     * Default draw method that provides how the object should be drawn 
     * in the GUI. This method does not draw anything. Subclass should 
     * override this method based on how their object should appear.
     * 
     * @param g 
     *  The <code>Graphics</code> context used for drawing the object.
     *  Remember graphics contexts that we used in OCaml, it gives the 
     *  context in which the object should be drawn (a canvas, a frame, 
     *  etc.)
     */
    public void draw(Graphics g) {
    }

    @Override
    public int compareTo(Pellet pellet) {
        // TODO Auto-generated method stub
        if (pellet == null) {
            throw new NullPointerException();
        }
        if (this.pos_x == pellet.pos_x && this.pos_y == pellet.pos_y) {
            return 0;
        } else if ((this.pos_x^2 + this.pos_y^2) < (pellet.pos_x^2 + pellet.pos_y^2)) {
            return -1;
        } else {
            return 1;
        }
    }    
}
