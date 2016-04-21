package PACMAN;
import java.awt.Graphics;

public abstract class Character {

    public int pos_x;
    public int pos_y;
    public int speed;
    public int tar_x;
    public int tar_y;
    public int max_x = (Background.width-2)*10;
    public int max_y = (Background.height-2)*10;
    public int min_x = 10;
    public int min_y = 10;

    /**
     * The constructor
     * 
     * @param tilePos_x
     * @param tilePos_y
     */
    public Character(int pos_x, int pos_y, int speed){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.speed = speed;
    }

    public int[] getPos() {
        int[] pos = new int[2];
        pos[0] = pos_x;
        pos[1] = pos_y;
        return pos;
    }

    public int[] setPos(int[] pos_old) {
        int[] pos_new = new int[2];
        pos_new[0] = pos_old[0];
        pos_new[1] = pos_old[1];
        return pos_new;
    }

    public int getSpeed() {
        int s = speed;
        return s;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int[] getTarTile() {
        int[] tarTile = new int[2];
        tarTile[0] = tar_x;
        tarTile[1] = tar_y;
        return tarTile;
    }

    public int[] setTarTile(int[] tarTile_old) {
        int[] tarTile_new = new int[2];
        tarTile_new[0] = tarTile_old[0];
        tarTile_new[1] = tarTile_old[1];
        return tarTile_new;
    }

    public void clip(){
        if (pos_x < min_x) pos_x = min_x;
        else if (pos_x > max_x) pos_x = max_x;

        if (pos_y < min_y) pos_y = min_y;
        else if (pos_y > max_y) pos_y = max_y;
    }
    
    public void grid() {
        if (pos_x % 10 != 0 && pos_y % 10 != 0) {
            pos_x = (int) 10*Math.round(pos_x/10);
            pos_y = (int) 10*Math.round(pos_y/10);
        }
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

}
