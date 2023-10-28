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
        //constants
        int BUTTONWIDTH = 360;
        int BUTTONHEIGHT = 200;
        int BUTTONY = Math.toIntExact(Math.round(((screenHeight - BUTTONHEIGHT) / 2) * 1.9));

        //Create multiplayer button
        multiPlayButton = new JButton("");
        int buttonX = (screenWidth - BUTTONWIDTH) / 2 + 200;
        //Set the icon of the multiplayer button
        ImageIcon multiPlayerIcon = new ImageIcon("MultiPlayerButton.png");
        Image multiPlayerImage = multiPlayerIcon.getImage();
        Image resizedMultiPlayerImage = multiPlayerImage.getScaledInstance(BUTTONWIDTH, BUTTONHEIGHT, Image.SCALE_SMOOTH);
        multiPlayerIcon = new ImageIcon(resizedMultiPlayerImage);
        //Make the multiplayer button transparent
        multiPlayButton.setContentAreaFilled(false);
        multiPlayButton.setBorderPainted(false);
        multiPlayButton.setIcon(multiPlayerIcon);
        //Add the multiplayer to the panel
        this.setLayout(null);
        this.add(multiPlayButton);
        multiPlayButton.setBounds(buttonX, BUTTONY, BUTTONWIDTH, BUTTONHEIGHT);

        //Create singleplayer button
        singlePlayButton = new JButton("");
        buttonX = (screenWidth - BUTTONWIDTH) / 2 - 200;
        //Set the icon of the singleplayer button
        ImageIcon singlePlayerIcon = new ImageIcon("SinglePlayerButton.png");
        Image singlePlayerImage = singlePlayerIcon.getImage();
        Image resizedSinglePlayerImage = singlePlayerImage.getScaledInstance(BUTTONWIDTH, BUTTONHEIGHT, Image.SCALE_SMOOTH);
        singlePlayerIcon = new ImageIcon(resizedSinglePlayerImage);
        // Make the singleplayer button transparent
        singlePlayButton.setContentAreaFilled(false);
        singlePlayButton.setBorderPainted(false);
        singlePlayButton.setIcon(singlePlayerIcon);
        //Add the singleplayer to the panel
        this.add(singlePlayButton);
        singlePlayButton.setBounds(buttonX, BUTTONY, BUTTONWIDTH, BUTTONHEIGHT);
    }

    //Add actionlistener to the multiplayer button
    public void addMultiPlayerActionListener(ActionListener listener) {
        multiPlayButton.addActionListener(listener);
    }
    //Add actionlistener to the singleplayer button    
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
