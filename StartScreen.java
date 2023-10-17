import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel implements ActionListener {

    private Image backgroundImage; // Image for the start screen
    public JButton playButton;

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
        playButton = new JButton("Play");
        int buttonWidth = 360;
        int buttonHeight = 140;
        int buttonX = (screenWidth - buttonWidth) / 2;
        int buttonY = Math.toIntExact(Math.round(((screenHeight - buttonHeight) / 2)*1.9));

        //playButton.setSize(buttonWidth, buttonHeight);
        //playButton.setLocation(buttonX, buttonY);
        
        // Add the button to the panel
        this.setLayout(null);
        this.add(playButton);
        playButton.setBounds(buttonX,buttonY,buttonWidth,buttonHeight);  

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
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

