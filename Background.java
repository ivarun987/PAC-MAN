package PACMAN;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")

public class Background extends JPanel{
    
    private PacMan pacman;
    private Blinky blinky;
    private Pinky pinky;
    private Inky inky;  
    private Clyde clyde;    
    public boolean playing = false;
    public int numLives = 3;
    public int score = 0;
    private JLabel livesLabel; // Current status text (i.e. Running...)
    private JLabel scoreLabel; // Current status text (i.e. Running...)
    public static final int INTERVAL = 20;
    
    
    // 0 open tile
    // 1 wall tile
    // 2 ghost wall tile
    // 3 Pacman Start
    // 4 Ghost Start
//    public static final int[][] map =
//        {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//         {1, 0, 1, 1, 1, 2, 1, 1, 1, 0, 1},
//         {1, 0, 1, 1, 4, 4, 4, 1, 1, 0, 1},
//         {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
//         {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
//         {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
//         {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1},
//         {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
//         {1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1},
//         {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
//         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
//        };
    
    public static final int[][] map =
        {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };

    
    public static final int width = map[0].length;
    public static final int height = map.length;
    
    // Initialize Pellets
    public static Set<SmallPellet> smallPellet() {
        Set<SmallPellet> pellets = new TreeSet<>();
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                if (map[j][i] == 0) {
                    pellets.add(new SmallPellet(10*i, 10*j));
                }
            }
        }
        System.out.println(pellets.size());
        return pellets;
    }
    
    Set<SmallPellet> smallPellets = new TreeSet<>(smallPellet());
    
    
    
    
    public Background(JLabel livesLabel, JLabel scoreLabel) {
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the background area.
        // When this component has the keyboard focus, key
        // events will be handled by its key listener.
        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
//                    if (pacman.hasUp)
                    pacman.dir = 0;
                    pacman.dx = 0;
                    pacman.dy = -1;
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    pacman.dir = 1;
                    pacman.dx = 0;
                    pacman.dy = 1;
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    pacman.dir = 2;
                    pacman.dx = -1;
                    pacman.dy = 0;
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    pacman.dir = 3;
                    pacman.dx = 1;
                    pacman.dy = 0;
                }
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    playing = !playing;
                }
            }
        });

        this.livesLabel = livesLabel;
        this.scoreLabel = scoreLabel;
    }
    
    public void reset() {
        
        pacman = new PacMan(2, -1, 0);
        blinky = new Blinky(1);
        pinky = new Pinky(0);
        inky = new Inky(0);
        clyde = new Clyde(0);

        playing = false;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    void tick() {
        if (playing) {
            // move pacman
            pacman.move();

            // check for the game end conditions
            if (pacman.intersects(blinky) || pacman.intersects(pinky) ||
                    pacman.intersects(inky) || pacman.intersects(clyde)) {
                playing = false;
                if (numLives > 1) {
                    numLives -= 1;
                } else {
                    numLives = 0;
                }
                livesLabel.setText("Lives Remaining" + numLives);
            }
            
            Set<SmallPellet> pellets = new TreeSet<>(smallPellets);
            for (SmallPellet pellet : smallPellets) {
                if (pellet.intersects(pacman)) {
                    pellets.remove(pellet);
                    score += 10;
                    scoreLabel.setText("Score: " + score);
                }
            }
            smallPellets = pellets;
            
            if (smallPellets.isEmpty()) {
                playing = false;
                
            }
  
            // update the display
            repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        pacman.draw(g);
        for (SmallPellet pellet : smallPellets) {
            pellet.draw(g);
        }
//        BufferedImage img;
//        try {
//            img = ImageIO.read(new File("PacmanBackground.jpeg"));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        g.drawImage(img, 0, 0, this);
        
    }
    
}
