package PACMAN;
import java.awt.Graphics;

// Blinky otherwise known as Shadow
// Follows Pacman's current tile
public class Blinky extends Character {

    private static final int INIT_BLINKY_POS_X = 5;
    private static final int INIT_BLINKY_POS_Y = 1;

    
    public static final int[] TARGET_TILE_SCATTER = {0,0};
    
    public Blinky(int speed) {
        super(INIT_BLINKY_POS_X, INIT_BLINKY_POS_Y, speed);
    }
    
    @Override
    public void draw(Graphics g) {
    }

}
