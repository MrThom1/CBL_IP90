import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel implements KeyListener, ActionListener {

    private SoccerGame soccerGame;
    private Image backgroundImage; // Image for the start screen
    private JButton playButton;

    public StartScreen(int screenWidth, int screenHeight) {
        this.soccerGame = soccerGame;
        setPreferredSize(new Dimension(screenWidth, screenHeight));  // Set the preferred size to full screen dimensions
        setFocusable(true);
        addKeyListener(this);

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Startscreen.png"));  // Provide the correct path to your image file
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a button
        playButton = new JButton("Play");
        int buttonWidth = 100;
        int buttonHeight = 50;
        int buttonX = (screenWidth - buttonWidth) / 2;
        int buttonY = (screenHeight - buttonHeight) / 2;

        playButton.setSize(buttonWidth, buttonHeight);
        playButton.setLocation(buttonX, buttonY);

        // Add the button to the panel
        add(playButton);

        // Set a dummy action listener for the button
        playButton.addActionListener(e -> {
            //setVisible(false);
        });
    }

    public void addPlayButtonActionListener(ActionListener listener) {
        playButton.addActionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }
}

