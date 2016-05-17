import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public abstract class Character {

    public int pos_x;
    public int pos_y;
    public int speed;
    public int max_x = (Background.width-2)*10;
    public int max_y = (Background.height-2)*10;
    public int min_x = 10;
    public int min_y = 10;
    public Direction dir = Direction.LEFT;

    /**
     * Constructor
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

    /**
     * Clips at max and min
     */
    public void clip(){
        if (pos_x < min_x) pos_x = min_x;
        else if (pos_x > max_x) pos_x = max_x;

        if (pos_y < min_y) pos_y = min_y;
        else if (pos_y > max_y) pos_y = max_y;
    }

    /**
     * Makes it so pacman moves only in a grid
     */
    public void grid() {
        if (pos_x % 10 != 0 && pos_y % 10 != 0) {
            pos_x = 10*Math.round(pos_x/10);
            pos_y = 10*Math.round(pos_y/10);
        }
    }
    
    /**
     * Behavior for side portal
     */
    public void portal() {
        if (pos_x == 10 && pos_y == 140) {
            pos_x = 258;
        }
        if (pos_x == 260 && pos_y == 140) {
            pos_x = 12;
        }
    }

    public boolean hasUpDraw() {
        int x = (int) Math.round(this.pos_x/10);
        int y = (int) Math.round(this.pos_y/10 + .45);
        return Background.MAP[y][x] != 1; 
    }
    
    public boolean hasUp() {
        int x = (int) Math.round(this.pos_x/10);
        int y = (int) Math.round(this.pos_y/10);
        return Background.MAP[y-1][x] != 1; 
    }

    public boolean hasLeftDraw() {
        int x = (int) Math.round(this.pos_x/10 + .45);
        int y = (int) Math.round(this.pos_y/10);
        return Background.MAP[y][x] != 1; 
    }

    public boolean hasLeft() {
        int x = (int) Math.round(this.pos_x/10);
        int y = (int) Math.round(this.pos_y/10);
        return Background.MAP[y][x-1] != 1; 
    }

    public boolean hasDown() {
        int x = (int) Math.round(this.pos_x/10);
        int y = (int) Math.round(this.pos_y/10);
        return Background.MAP[y+1][x] != 1; 
    }

    public boolean hasRight() {
        int x = (int) Math.round(this.pos_x/10);
        int y = (int) Math.round(this.pos_y/10);
        return Background.MAP[y][x+1] != 1; 
    }

    public boolean hasNext() {
        int x = (int) Math.round(this.pos_x/10);
        int y = (int) Math.round(this.pos_y/10);
        switch (dir) {
        case UP:
            return Background.MAP[y-1][x] != 1; 
        case DOWN:
            return Background.MAP[y+1][x] != 1; 
        case LEFT:
            return Background.MAP[y][x-1] != 1; 
        case RIGHT:
            return Background.MAP[y][x+1] != 1; 
        default:
            return true;
        }
    }
    
    /**
     * 
     * @return List of directions sorted by distance to PacMan
     */
    public ArrayList<Direction> minDir() {
        Map<Direction,Integer> dist = new TreeMap<>();
        int distU = (pos_x - Background.pacPos[0])
                + (pos_y - 10 - Background.pacPos[1]);
        int distD = (pos_x - Background.pacPos[0])
                + (pos_y + 10 - Background.pacPos[1]);
        int distL = (pos_x - 10 - Background.pacPos[0])
                + (pos_y - Background.pacPos[1]);
        int distR = (pos_x + 10 - Background.pacPos[0])
                + (pos_y - Background.pacPos[1]);
        dist.put(Direction.UP, Math.abs(distU));
        dist.put(Direction.DOWN, Math.abs(distD));
        dist.put(Direction.LEFT, Math.abs(distL));
        dist.put(Direction.RIGHT, Math.abs(distR));
        ArrayList<Direction> dirs = new ArrayList<>();

        while (dirs.size() < 4) {
            Entry<Direction, Integer> min = null;
            for (Entry<Direction, Integer> entry : dist.entrySet()) {
                if (min == null || min.getValue() > entry.getValue()) {
                    min = entry;
                }
            }
            dirs.add(min.getKey());
            dist.remove(min.getKey());
        }
        return dirs;
    }
    
    /**
     * Opposite Direction
     * @param d
     * @return opposite direction
     */
    public Direction oppDir(Direction d) {
        switch (d) {
        case UP:
            return Direction.DOWN;
        case DOWN:
            return Direction.UP;
        case LEFT:
            return Direction.RIGHT;
        case RIGHT:
            return Direction.LEFT;
        }
        return Direction.LEFT;
    }
    
    /**
     * Frightened Behavior
     * @param fright
     * @param dirs
     * @return direction list of descending order of distance to PacMan
     */
    public ArrayList<Direction> runAwayDir(boolean fright, ArrayList<Direction> dirs) {
        if (fright) {
            Collections.reverse(dirs);
        }
        return dirs;
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
