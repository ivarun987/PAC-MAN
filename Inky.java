package PACMAN;
import java.awt.Graphics;

public class Inky extends Character {

    public static final int[] TARGET_TILE_SCATTER = {0,9};
    private static final int INIT_INKY_POS_X = 4;
    private static final int INIT_INKY_POS_Y = 3;
    
    
    public Inky(int speed) {
        super(INIT_INKY_POS_X, INIT_INKY_POS_Y, speed);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void draw(Graphics g) {
    }

}
