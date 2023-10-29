/** 
 * @author Tuur Bosma
 * @id     1978020
 * @author Thom Coolen
 * @id     1971190
 */

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

    public StartScreen(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        panel.setFocusable(true);
    
        try {
            backgroundImage = ImageIO.read(new File("Startscreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //constants
        final int BUTTON_WIDTH = 360;
        final int BUTTON_HEIGHT = 200;
        final int BUTTON_Y = Math.toIntExact(Math.round(((SCREEN_HEIGHT - BUTTON_HEIGHT) / 2) * 1.9));
        
        //Create multiplayer button
        multiPlayButton = new JButton("");
        int buttonX = (SCREEN_WIDTH - BUTTON_WIDTH) / 2 + 200;
        //Set the icon of the multiplayer button
        ImageIcon multiPlayerIcon = new ImageIcon("MultiPlayerButton.png");
        Image multiPlayerImage = multiPlayerIcon.getImage();
        Image resizedMultiPlayerImage = multiPlayerImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
        multiPlayerIcon = new ImageIcon(resizedMultiPlayerImage);
        //Make the multiplayer button transparent
        multiPlayButton.setContentAreaFilled(false);
        multiPlayButton.setBorderPainted(false);
        multiPlayButton.setIcon(multiPlayerIcon);
        //Add the multiplayer to the panel
        this.setLayout(null);
        this.add(multiPlayButton);
        multiPlayButton.setBounds(buttonX, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);

        //Create singleplayer button
        singlePlayButton = new JButton("");
        buttonX = (SCREEN_WIDTH - BUTTON_WIDTH) / 2 - 200;
        //Set the icon of the singleplayer button
        ImageIcon singlePlayerIcon = new ImageIcon("SinglePlayerButton.png");
        Image singlePlayerImage = singlePlayerIcon.getImage();
        Image resizedSinglePlayerImage = singlePlayerImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
        singlePlayerIcon = new ImageIcon(resizedSinglePlayerImage);
        //Make the singleplayer button transparent
        singlePlayButton.setContentAreaFilled(false);
        singlePlayButton.setBorderPainted(false);
        singlePlayButton.setIcon(singlePlayerIcon);
        //Add the singleplayer to the panel
        this.add(singlePlayButton);
        singlePlayButton.setBounds(buttonX, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    /**
     * Add actionlistener to the multiplayer button
     *
     * @param listener events that will be executed when to the multiplayer button is clicked
     */
    public void addMultiPlayerActionListener(ActionListener listener) {
        multiPlayButton.addActionListener(listener);
    }

    /**
     * Add actionlistener to the singleplayer button
     *
     * @param listener events that will be executed when to the singleplayer button is clicked
     */  
    public void addSinglePlayerActionListener(ActionListener listener) {
        singlePlayButton.addActionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Not used
    }
}
