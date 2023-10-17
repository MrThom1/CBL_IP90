import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel implements ActionListener {

    private Image backgroundImage; // Image for the start screen
    public JButton multiPlayButton;
    public JButton singlePlayButton;


    public StartScreen(int screenWidth, int screenHeight) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight));  // Set the preferred size to full screen dimensions
        panel.setFocusable(true);

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Startscreen.png"));  // Provide the correct path to your image file
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a button
        multiPlayButton = new JButton("Play");
        int buttonWidth = 360;
        int buttonHeight = 140;
        int buttonX = (screenWidth - buttonWidth) / 2 + 200 ;
        int buttonY = Math.toIntExact(Math.round(((screenHeight - buttonHeight) / 2)*1.9));
        
        // Add the button to the panel
        this.setLayout(null);
        this.add(multiPlayButton);
        multiPlayButton.setBounds(buttonX,buttonY,buttonWidth,buttonHeight);  

        // Set a dummy action listener for the button
        multiPlayButton.addActionListener(e -> {
            //setVisible(false);
        });
        
        singlePlayButton = new JButton("Play");
        buttonX = (screenWidth - buttonWidth) / 2 - 200;

        // Add the button to the panel
        this.setLayout(null);
        this.add(singlePlayButton);
        singlePlayButton.setBounds(buttonX,buttonY,buttonWidth,buttonHeight);  

        // Set a dummy action listener for the button
        singlePlayButton.addActionListener(e -> {
            //setVisible(false);
        });
    }

    public void addMultiPlayerActionListener(ActionListener listener) {
        multiPlayButton.addActionListener(listener);
    }
    public void addSinglePlayerActionListener(ActionListener listener) {
        singlePlayButton.addActionListener(listener);
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
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

