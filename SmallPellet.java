package PACMAN;
import java.awt.*;

public class SmallPellet extends Pellet {
    
    public static final int RADIUS = 5;
    public static final boolean ENERGIZER_STATUS = false;

    public SmallPellet(int pos_x, int pos_y) {
        super(pos_x, pos_y, RADIUS, ENERGIZER_STATUS);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(pos_x + RADIUS, pos_y + RADIUS, RADIUS, RADIUS);
    }   
}