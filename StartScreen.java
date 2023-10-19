import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel implements ActionListener {

    private Image backgroundImage;
    public JButton multiPlayButton;
    public JButton singlePlayButton;

    public StartScreen(int screenWidth, int screenHeight) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        panel.setFocusable(true);
    
        try {
            backgroundImage = ImageIO.read(new File("Startscreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        multiPlayButton = new JButton("MultiPlayer!");
        int buttonWidth = 360;
        int buttonHeight = 200;
        int buttonX = (screenWidth - buttonWidth) / 2 + 200;
        int buttonY = Math.toIntExact(Math.round(((screenHeight - buttonHeight) / 2) * 1.9));
    
        ImageIcon multiPlayerIcon = new ImageIcon("MultiPlayerButton.png");
        Image multiPlayerImage = multiPlayerIcon.getImage();
        Image resizedMultiPlayerImage = multiPlayerImage.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        multiPlayerIcon = new ImageIcon(resizedMultiPlayerImage);
    
        // Make the button transparent
        multiPlayButton.setContentAreaFilled(false);
        multiPlayButton.setBorderPainted(false);
        multiPlayButton.setIcon(multiPlayerIcon);
    
        this.setLayout(null);
        this.add(multiPlayButton);
        multiPlayButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
    
        multiPlayButton.addActionListener(e -> {
        });
    
        singlePlayButton = new JButton("SinglePlayer!");
        buttonX = (screenWidth - buttonWidth) / 2 - 200;
    
        ImageIcon singlePlayerIcon = new ImageIcon("SinglePlayerButton.png");
        Image singlePlayerImage = singlePlayerIcon.getImage();
        Image resizedSinglePlayerImage = singlePlayerImage.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        singlePlayerIcon = new ImageIcon(resizedSinglePlayerImage);
    
        // Make the button transparent
        singlePlayButton.setContentAreaFilled(false);
        singlePlayButton.setBorderPainted(false);
        singlePlayButton.setIcon(singlePlayerIcon);
    
        this.add(singlePlayButton);
        singlePlayButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
    
        singlePlayButton.addActionListener(e -> {
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
