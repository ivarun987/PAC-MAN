package PACMAN;
import java.awt.Color;
import java.awt.Graphics;

public class PacMan extends Character {

    private static final int RADIUS = 15;
    public int dx;
    public int dy;
    private static final int INIT_PACMAN_POS_X = 140;
    private static final int INIT_PACMAN_POS_Y = 110;
    public int dir = 2;


    public PacMan(int speed, int dx, int dy) {
        super(INIT_PACMAN_POS_X, INIT_PACMAN_POS_Y, speed);
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        pos_x += (int) speed*dx;
        pos_y += (int) speed*dy;
        grid();
        clip();
    }

    public boolean intersects(Character ghost) {
        return (this.pos_x == ghost.pos_x) && (this.pos_y == ghost.pos_y);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        switch (dir) {
        // UP
        case 0:
            g.fillArc(pos_x, pos_y, RADIUS, RADIUS, 110, 320);
            break;
        // DOWN
        case 1:
            g.fillArc(pos_x, pos_y, RADIUS, RADIUS, 290, 320);
            break;
        // LEFT
        case 2:
            g.fillArc(pos_x, pos_y, RADIUS, RADIUS, 200, 320);
            break;
        // RIGHT
        case 3:
            g.fillArc(pos_x, pos_y, RADIUS, RADIUS, 20, 320);
            break;
        // ELSE
        default:
            g.fillArc(pos_x, pos_y, RADIUS, RADIUS, 20, 320);
            break;
        }

    }


}
