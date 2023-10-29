/** 
 * @author Tuur Bosma
 * @id     1978020
 * @author Thom Coolen
 * @id     1971190
 */

import java.awt.*;

import javax.swing.JFrame;

public class Handler {
    static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int SCREEN_WIDTH = (int) SCREEN_SIZE.getWidth();
    static final int SCREEN_HEIGHT = (int) SCREEN_SIZE.getHeight();
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Soccer Game");
        StartScreen startScreen = new StartScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.add(startScreen);
        frame.setUndecorated(true);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Add an ActionListener to the "Play" button
        startScreen.addMultiPlayerActionListener(e -> {
            startScreen.setVisible(false);  // Hide the start screen
            frame.remove(startScreen);
            SoccerGameMultiPlayer soccerGame = new SoccerGameMultiPlayer(SCREEN_WIDTH, SCREEN_HEIGHT);
            frame.add(soccerGame);  // Add the SoccerGame panel
            soccerGame.requestFocus();
            // Start the game
            soccerGame.startSoccerGame(soccerGame);
        });
        startScreen.addSinglePlayerActionListener(e -> {
            startScreen.setVisible(false);  // Hide the start screen
            frame.remove(startScreen);
            SoccerGameSinglePlayer soccerGame = new SoccerGameSinglePlayer(SCREEN_WIDTH, SCREEN_HEIGHT);
            frame.add(soccerGame);  // Add the SoccerGame panel
            soccerGame.requestFocus();
            // Start the game
            soccerGame.startSoccerGame(soccerGame);
        });
    }
}