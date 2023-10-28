import java.awt.*;

import javax.swing.JFrame;

public class Handler {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenWidth = (int) screenSize.getWidth();
    static int screenHeight = (int) screenSize.getHeight();
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Soccer Game");
        StartScreen startScreen = new StartScreen(screenWidth, screenHeight);
        frame.add(startScreen);
        frame.setUndecorated(true);
        frame.setSize(screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Add an ActionListener to the "Play" button
        startScreen.addMultiPlayerActionListener(e -> {
            startScreen.setVisible(false);  // Hide the start screen
            frame.remove(startScreen);
            SoccerGameMultiPlayer soccerGame = new SoccerGameMultiPlayer(screenWidth, screenHeight);
            frame.add(soccerGame);  // Add the SoccerGame panel
            soccerGame.requestFocus();
            // Start the game
            soccerGame.startSoccerGame(soccerGame);
        });
        startScreen.addSinglePlayerActionListener(e -> {
            startScreen.setVisible(false);  // Hide the start screen
            frame.remove(startScreen);
            SoccerGameSinglePlayer soccerGame = new SoccerGameSinglePlayer(screenWidth, screenHeight);
            frame.add(soccerGame);  // Add the SoccerGame panel
            soccerGame.requestFocus();
            // Start the game
            soccerGame.startSoccerGame(soccerGame);
        });
    }
}