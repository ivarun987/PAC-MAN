import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * Main Game Logic and Space where everything is processed and simulated
 *
 */
@SuppressWarnings("serial")
public class Background extends JPanel{
    
    private PacMan pacman;
    private Blinky blinky;
    private Pinky pinky;
    private Inky inky;  
    private Clyde clyde;    
    public boolean playing = false;
    public boolean alreadyExecuted = false;
    public int numLives = 3;
    public int score = 0;
    public static final int SCALE = 10;
    private JLabel livesLabel;
    private JLabel scoreLabel;
    public static final int INTERVAL = 25;
    public String username = "Name";
    public String scoreFile = "scores.txt";
    public static int[] pacPos;
    long currTime = 0;
    
    // Initialize Intersections
    public static final int[][] LDCorner = {
            {10, 80},{90, 80},{10, 290},{10, 230},{90, 260},{210, 260}
    };
    
    public static final int[][] RDCorner = {
            {260, 80},{180, 80},{260, 290},{260, 230},{180, 260},{60, 260}
    };

    public static final int[][] LUCorner = {
            {10, 10},{10, 200},{10, 260},{240, 230},
            {150, 10},{150, 80},{150, 200},{150,260},{90,110}
    };
    
    public static final int[][] RUCorner = {
            {260, 10},{260, 200},{260, 260},{30, 230},
            {120, 10},{120, 80},{120, 200},{120, 260},{180, 110}
    };
    
    public static final int[][] FourWay = {
            {60, 50}, {60, 140}, {60, 200},
            {210, 50},{210, 140},{210, 200}
    };
    
    public static final int[][] ThreeWayUp = {
            {120, 290},{120, 50},{120, 110},{120, 230},{30, 260},{90, 200},
            {150, 290},{150, 50},{150, 110},{150, 230},{240, 260},{180, 200}
    };
    
    public static final int[][] ThreeWayDown = {
            {60, 10},{210, 10},{90, 50},{180, 50},{90, 230},{180, 230}
    };
    
    public static final int[][] ThreeWayLeft = {
            {260, 50},{210, 230},{90, 140},{60, 80},{180, 170}
    };
    
    public static final int[][] ThreeWayRight = {
            {10, 50},{60, 230},{180, 140},{210, 80},{90, 170}
    };    

    public HighScoreScanner mkScores() throws IOException, HighScoreScanner.FormatException {
        final HighScoreScanner hsc = HighScoreScanner.make(scoreFile);
        return hsc;
    }
    
    // 0 small Pellet
    // 1 wall
    // 2 energizer Pellet
    // 3 open space
    
    public static final int[][] MAP =
        {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 2, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 2, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 3, 3, 3, 3, 3, 0, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 0, 3, 3, 3, 3, 3, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 1},
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
         {1, 2, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 1},
         {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1},
         {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1},
         {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
         {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
         {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
         {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
         {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };
    
    
    public static final int width = MAP[0].length;
    public static final int height = MAP.length;
    
    // Initialize Small Pellets
    public static Set<SmallPellet> smallPellet() {
        Set<SmallPellet> pellets = new HashSet<>();
        
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                if (MAP[j][i] == 0) {
                    pellets.add(new SmallPellet(SCALE*i, SCALE*j));
                }
            }
        }
        return pellets;
    }
    Set<SmallPellet> smallPellets =
            new HashSet<>(smallPellet());
    
    
    //Intialize Energizer Pellets
    public static Set<EnergizerPellet> energizerPellet() {
        Set<EnergizerPellet> pellets = new HashSet<>();
        
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                if (MAP[j][i] == 2) {
                    pellets.add(new EnergizerPellet(SCALE*i, SCALE*j));
                }
            }
        }
        return pellets;
    }
    Set<EnergizerPellet> energizerPellets =
            new HashSet<>(energizerPellet());
    
    
    
    
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
                    if (pacman.hasUp()) {
                        pacman.dir = Direction.UP;
                        pacman.dx = 0;
                        pacman.dy = -1;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (pacman.hasDown()) {
                        pacman.dir = Direction.DOWN;
                        pacman.dx = 0;
                        pacman.dy = 1;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (pacman.hasLeft()) {
                        pacman.dir = Direction.LEFT;
                        pacman.dx = -1;
                        pacman.dy = 0;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (pacman.hasRight()) {
                        pacman.dir = Direction.RIGHT;
                        pacman.dx = 1;
                        pacman.dy = 0;
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (numLives > 0) {
                        playing = !playing;
                    }
                }
            }
        });

        this.livesLabel = livesLabel;
        this.scoreLabel = scoreLabel;
        
    }
    
    // Reset screen characters when losing a life
    public void reset() {
        pacman = new PacMan(1, -1, 0);
        blinky = new Blinky(1);
        pinky = new Pinky(1);
        inky = new Inky(1);
        clyde = new Clyde(1);

        playing = false;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    // Lost the Game. Reset everything and print high score
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void resetLose() {
        
        HighScoreScanner hsc = null;
        try {
            hsc = this.mkScores();
        } catch (IOException | HighScoreScanner.FormatException e) {
            System.out.println("resetLose: Error");
        }
        
        //Add Score to High Score File
        String name = username;
        int ctr = 0;
        
        final ArrayList<Player<String, Integer>> scoreList_temp =
                new ArrayList<Player<String,Integer>>(hsc.scoreList);
        for (Player player : hsc.scoreList) {
            if (this.score > player.getScore()) {
                scoreList_temp.add(ctr,new Player(name,score));
                break;
            }
            // If Lowest Score
            if (ctr == scoreList_temp.size() - 1) {
                scoreList_temp.add(new Player(name,score));
            }
            ctr += 1;
        }
        hsc.scoreList = scoreList_temp;
        
        try {
            hsc.write(scoreFile);
        } catch (FileNotFoundException e) {
            System.out.println("resetLose: File Not Found");
        }

        
        numLives = 3;
        livesLabel.setText("Lives Remaining: " + numLives);
        score = 0;
        scoreLabel.setText("Score: " + score);
        
        smallPellets = new HashSet<>(smallPellet());
        energizerPellets = new HashSet<>(energizerPellet());
        
        pacman = new PacMan(1, -1, 0);
        blinky = new Blinky(1);
        pinky = new Pinky(1);
        inky = new Inky(1);
        clyde = new Clyde(1);

        playing = false;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    // Won the Game. Reset everything except score and lives
    public void resetWin() {

        smallPellets = new HashSet<>(smallPellet());
        energizerPellets = new HashSet<>(energizerPellet());

        
        pacman = new PacMan(1, -1, 0);
        blinky = new Blinky(1);
        pinky = new Pinky(1);
        inky = new Inky(1);
        clyde = new Clyde(1);

        playing = false;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    public void loseALife() {
        playing = false;
        if (numLives > 1) {
            numLives -= 1;
        } else {
            numLives = 0;
        }
        livesLabel.setText("Lives Remaining: " + numLives);
        this.reset();
    }

    
    void tick() {
        if (playing) {
            // move pacman
            pacman.move();
            pacPos = pacman.getPos();
            pinky.move();
            blinky.move();
            if (smallPellets.size() < 170) {
                inky.move();
            }
            if (smallPellets.size() < 100) {
                clyde.move();
            }

            // check for the game end conditions
            if (!blinky.fright && pacman.intersects(blinky)) {
                loseALife();
            }
            if (!pinky.fright && pacman.intersects(pinky)) {
                loseALife();
            }
            if (!inky.fright && pacman.intersects(inky)) {
                loseALife();
            }
            if (!clyde.fright && pacman.intersects(clyde)) {
                loseALife();
            }
            
            if (blinky.fright && pacman.intersects(blinky)) {
                score += 200;
                scoreLabel.setText("Score: " + score);
                blinky = new Blinky(1);
            }
            
            if (pinky.fright && pacman.intersects(pinky)) {
                score += 200;
                scoreLabel.setText("Score: " + score);
                pinky = new Pinky(1);
            }
            
            if (inky.fright && pacman.intersects(inky)) {
                score += 200;
                scoreLabel.setText("Score: " + score);
                inky = new Inky(1);
            }
            
            if (clyde.fright && pacman.intersects(clyde)) {
                score += 200;
                scoreLabel.setText("Score: " + score);
                clyde = new Clyde(1);
            }
            
            // Lost the game
            if (numLives == 0) {
                livesLabel.setText("Lives Remaining: " + 0);
                this.resetLose();
            }
            
            // Make frightened mode temporary
            if ((System.currentTimeMillis() - currTime) > 10000) {
                blinky.frightenedOff();
                pinky.frightenedOff();
                inky.frightenedOff();
                clyde.frightenedOff();
                pacman.setSpeed(1);
            }
            
            // Every 5000 points, additional life
            if (score % 5000 == 0 && score > 0 && !alreadyExecuted) {
                numLives += 1;
                livesLabel.setText("Lives Remaining: " + numLives);
                alreadyExecuted = true;
            }
            
            if (score % 5000 != 0) {
                alreadyExecuted = false;
            }

            // check for small pellet intersection
            Set<SmallPellet> smPellets = new HashSet<>(smallPellets);
            for (SmallPellet pellet : smallPellets) {
                if (pellet.intersects(pacman)) {
                    smPellets.remove(pellet);
                    score += 10;
                    scoreLabel.setText("Score: " + score);
                }
            }
            smallPellets = smPellets;

            // check for energizer pellet intersection
            Set<EnergizerPellet> enPellets = new HashSet<>(energizerPellets);
            for (EnergizerPellet pellet : energizerPellets) {
                if (pellet.intersects(pacman)) {
                    enPellets.remove(pellet);
                    if (smallPellets.size() < 170) {
                        inky.frightened();
                    }
                    if (smallPellets.size() < 100) {
                        clyde.frightened();
                    }
                    blinky.frightened();
                    pinky.frightened();
                    score += 50;
                    pacman.setSpeed(2);
                    currTime = System.currentTimeMillis();
                    scoreLabel.setText("Score: " + score);
                }
            }
            energizerPellets = enPellets;

            // Win the Game. Reset everything except score and lives
            if (smallPellets.isEmpty() && energizerPellets.isEmpty()) {
                this.resetWin();
            }
 
            // update the display
            repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw Backgrounds
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("PacmanBackground.jpeg"));
        } catch (IOException e) {
            System.out.println("paint: IO error");
        }
        g.drawImage(img, 1, 1, 282, 313, this);

        // Draw Small Pellets
        for (SmallPellet pellet : smallPellets) {
            pellet.draw(g);
        }
        
        // Draw Energizer Pellets
        for (EnergizerPellet pellet : energizerPellets) {
            pellet.draw(g);
        }
        
        // Draw characters
        blinky.draw(g);
        pinky.draw(g);
        inky.draw(g);
        clyde.draw(g);
        pacman.draw(g);        
    }

}
