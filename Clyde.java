package PACMAN;
import java.awt.Graphics;

public class Clyde extends Character {
    
    public static final int[] TARGET_TILE_SCATTER = {9,9};
    private static final int INIT_CLYDE_POS_X = 6;
    private static final int INIT_CLYDE_POS_Y = 3;

    
    public Clyde(int speed) {
        super(INIT_CLYDE_POS_X, INIT_CLYDE_POS_Y, speed);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void draw(Graphics g) {
    }

}
