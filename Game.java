package PACMAN;

/**
 * CIS 120 Game HW 09
 * PAC-MAN
 * (c) Varun Jain
 * @version 1.0, April 2016
 */

// imports necessary libraries for Java swing
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("PAC-MAN");
        frame.setLocation(400, 200);
        
//        frame.setContentPane(new JLabel(new ImageIcon("PacmanBackground.jpeg")));
        
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel lives = new JLabel("Lives Remaining: 3");
        final JLabel score = new JLabel("Score: 0");
        status_panel.add(lives);
        status_panel.add(score);
        
     // Main playing area
        final Background bg = new Background(lives, score);
        frame.add(bg, BorderLayout.CENTER);
        bg.setBackground(Color.BLACK);
        frame.setPreferredSize(new Dimension(Background.width*15, Background.height*15));

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset
        // button, we define it as an anonymous inner class that is
        // an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed,
        // actionPerformed() will be called.
        
//        final JButton reset = new JButton("Reset");
//        reset.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                bg.reset();
//            }
//        });
//        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        bg.reset();
    }

    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}

