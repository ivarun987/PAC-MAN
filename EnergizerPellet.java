package PACMAN;
import java.awt.Color;
import java.awt.Graphics;

public class EnergizerPellet extends Pellet {
    
    public static final int RADIUS = 4;
    public static final boolean ENERGIZER_STATUS = true;

    public EnergizerPellet(int pos_x, int pos_y) {
        super(pos_x, pos_y, RADIUS, ENERGIZER_STATUS);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(pos_x, pos_y, RADIUS, RADIUS);
    }

}
