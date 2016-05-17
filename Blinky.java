import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// Blinky otherwise known as Shadow
public class Blinky extends Character {

    private static final int INIT_BLINKY_POS_X = 135;
    private static final int INIT_BLINKY_POS_Y = 110;
    public Direction dir = Direction.LEFT;
    private int dx = -1;
    private int dy = 0;
    public boolean fright = false;
    private double x = 0;
    
    /**
     * Animation timer that switches between true and false periodically
     * @return
     */
    public boolean animationTimer() {
        boolean ans = (Math.sin(x) > 0);
        x += .2;
        return ans;
    }

    public Blinky(int speed) {
        super(INIT_BLINKY_POS_X, INIT_BLINKY_POS_Y, speed);
    }

    public void move() {
        pos_x += (int) speed*dx;
        pos_y += (int) speed*dy;

        portal();
        corner();
        clip();
    }

    private void corner() {
        for (int[] p : Background.LDCorner) {
            if (pos_x == p[0] && pos_y == p[1]) {
                if (dir == Direction.LEFT) {
                    turnUp();
                }
                if (dir == Direction.DOWN) {
                    turnRight();
                }
            }
        }

        for (int[] p : Background.RDCorner) {
            if (pos_x == p[0] && pos_y == p[1]) {
                if (dir == Direction.RIGHT) {
                    turnUp();
                }
                if (dir == Direction.DOWN) {
                    turnLeft();
                }
            }
        }

        for (int[] p : Background.LUCorner) {
            if (pos_x == p[0] && pos_y == p[1]) {
                if (dir == Direction.LEFT) {
                    turnDown();
                }
                if (dir == Direction.UP) {
                    turnRight();
                }
            }
        }

        for (int[] p : Background.RUCorner) {
            if (pos_x == p[0] && pos_y == p[1]) {
                if (dir == Direction.RIGHT) {
                    turnDown();
                }
                if (dir == Direction.UP) {
                    turnLeft();
                }
            }
        }

        for (int[] p : Background.FourWay) {
            if (pos_x == p[0] && pos_y == p[1]) {
                ArrayList<Direction> dirs = minDir();
                dirs = runAwayDir(fright, dirs);
                for (Direction d : dirs) {
                    if (dir != oppDir(d)) {
                        turn(d);
                        break;
                    }
                }
            }
        }
        for (int[] p : Background.ThreeWayRight) {
            if (pos_x == p[0] && pos_y == p[1]) {
                ArrayList<Direction> dirs = minDir();
                dirs = runAwayDir(fright, dirs);
                for (Direction d : dirs) {
                    if (dir != oppDir(d) && d != Direction.LEFT) {
                        turn(d);
                        break;
                    }
                }
            }
        }
        for (int[] p : Background.ThreeWayLeft) {
            if (pos_x == p[0] && pos_y == p[1]) {
                ArrayList<Direction> dirs = minDir();
                dirs = runAwayDir(fright, dirs);
                for (Direction d : dirs) {
                    if (dir != oppDir(d) && d != Direction.RIGHT) {
                        turn(d);
                        break;
                    }
                }
            }
        }
        for (int[] p : Background.ThreeWayDown) {
            if (pos_x == p[0] && pos_y == p[1]) {
                ArrayList<Direction> dirs = minDir();
                dirs = runAwayDir(fright, dirs);
                for (Direction d : dirs) {
                    if (dir != oppDir(d) && d != Direction.UP) {
                        turn(d);
                        break;
                    }
                }
            }
        }
        for (int[] p : Background.ThreeWayUp) {
            if (pos_x == p[0] && pos_y == p[1]) {
                ArrayList<Direction> dirs = minDir();
                dirs = runAwayDir(fright, dirs);
                for (Direction d : dirs) {
                    if (dir != oppDir(d) && d != Direction.DOWN) {
                        turn(d);
                        break;
                    }
                }
            }
        }
    }



    public void frightened() {
        fright = true;
    }
    
    public void frightenedOff() {
        fright = false;
    }


    @Override
    public void draw(Graphics g) {
        BufferedImage imgU = null;
        BufferedImage imgU2 = null;
        BufferedImage imgL = null;
        BufferedImage imgL2 = null;
        BufferedImage imgD = null;
        BufferedImage imgD2 = null;
        BufferedImage imgR = null;
        BufferedImage imgR2 = null;
        BufferedImage imgFR = null;
        BufferedImage imgFR2 = null;
        try {
            imgU = ImageIO.read(new File("blinkyU.png"));
            imgU2 = ImageIO.read(new File("blinkyU2.png"));
            imgL = ImageIO.read(new File("blinkyL.png"));
            imgL2 = ImageIO.read(new File("blinkyL2.png"));
            imgD = ImageIO.read(new File("blinkyD.png"));
            imgD2 = ImageIO.read(new File("blinkyD2.png"));
            imgR = ImageIO.read(new File("blinkyR.png"));
            imgR2 = ImageIO.read(new File("blinkyR2.png"));
            imgFR = ImageIO.read(new File("frightened.png"));
            imgFR2 = ImageIO.read(new File("frightened2.png"));
        } catch (IOException e) {
            System.out.println("error in drawing Blinky");
        }
        if (fright) {
            if (animationTimer()) {
                g.drawImage(imgFR, pos_x, pos_y, null);
            } else {
                g.drawImage(imgFR2, pos_x, pos_y, null);
            }
        } else {

            switch (dir) {
            case UP:
                if (animationTimer()) {
                    g.drawImage(imgU, pos_x, pos_y, null);
                } else {
                    g.drawImage(imgU2, pos_x, pos_y, null);
                }
                break;
            case DOWN:
                if (animationTimer()) {
                    g.drawImage(imgD, pos_x, pos_y, null);
                } else {
                    g.drawImage(imgD2, pos_x, pos_y, null);
                }
                break;
            case LEFT:
                if (animationTimer()) {
                    g.drawImage(imgL, pos_x, pos_y, null);
                } else {
                    g.drawImage(imgL2, pos_x, pos_y, null);
                }
                break;
            case RIGHT:
                if (animationTimer()) {
                    g.drawImage(imgR, pos_x, pos_y, null);
                } else {
                    g.drawImage(imgR2, pos_x, pos_y, null);
                }
                break;
            }
        }

    }

    public void turn(Direction d) {
        switch (d) {
        case UP:
            turnUp();
            break;
        case DOWN:
            turnDown();
            break;
        case LEFT:
            turnLeft();
            break;
        case RIGHT:
            turnRight();
            break;
        }
    }

    public void turnUp() {
        dir = Direction.UP;
        dx = 0;
        dy = -1;
    }

    public void turnRight() {
        dir = Direction.RIGHT;
        dx = 1;
        dy = 0;
    }

    public void turnDown() {
        dir = Direction.DOWN;
        dx = 0;
        dy = 1;
    }

    public void turnLeft() {
        dir = Direction.LEFT;
        dx = -1;
        dy = 0;
    }

}
