import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
/**
 * 
 * Clyde otherwise known as Stupid or feigning ignorance
 *
 */
public class Clyde extends Character {

    private static final int INIT_CLYDE_POS_X = 155;
    private static final int INIT_CLYDE_POS_Y = 140;
    public Direction dir = Direction.LEFT;
    public int dx = -1;
    public int dy = 0;
    public boolean fright = false;
    private double x = 0;
    
    public boolean animationTimer() {
        boolean ans = (Math.sin(x) > 0);
        x += .2;
        return ans;
    }

    public Clyde(int speed) {
        super(INIT_CLYDE_POS_X, INIT_CLYDE_POS_Y, speed);
        // TODO Auto-generated constructor stub
    }

    public void move() {
        pos_x += (int) speed*dx;
        pos_y += (int) speed*dy;
        
        
        // Initial Movement
        if (pos_x == 135 && pos_y == 140) {
            turnUp();
        }
        if (pos_x == 135 && pos_y == 110 && dir == Direction.UP) {
            turnLeft();
        }
        
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
            imgU = ImageIO.read(new File("clydeU.png"));
            imgU2 = ImageIO.read(new File("clydeU2.png"));
            imgL = ImageIO.read(new File("clydeL.png"));
            imgL2 = ImageIO.read(new File("clydeL2.png"));
            imgD = ImageIO.read(new File("clydeD.png"));
            imgD2 = ImageIO.read(new File("clydeD2.png"));
            imgR = ImageIO.read(new File("clydeR.png"));
            imgR2 = ImageIO.read(new File("clydeR2.png"));
            imgFR = ImageIO.read(new File("frightened.png"));
            imgFR2 = ImageIO.read(new File("frightened2.png"));
        } catch (IOException e) {
            System.out.println("error in drawing Clyde");
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
