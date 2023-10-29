import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class EndScreen extends JPanel implements ActionListener {
    private Image backgroundImage;
    public JButton StartScreenButton;
    private int screenHeightText;
    private String winner;

    public EndScreen(int screenWidth, int screenHeight, String winnerofgame, Object soccergame) {
        soccergame = null;
        System.gc();
        screenHeightText = screenHeight;
        winner = winnerofgame;

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        panel.setFocusable(true);
    
        try {
            backgroundImage = ImageIO.read(new File("Startscreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //constants
        int BUTTONWIDTH = 540;
        int BUTTONHEIGHT = 300;
        int BUTTONY = Math.toIntExact(Math.round(((screenHeight - BUTTONHEIGHT) / 2) * 1.8));

        //Create StartScreenButton
        StartScreenButton = new JButton();
        int buttonX = (screenWidth - BUTTONWIDTH) / 2;
        //Set the icon of the StartScreenButton
        ImageIcon StartScreenIcon = new ImageIcon("StartScreenButton.png");
        Image StartScreenImage = StartScreenIcon.getImage();
        Image resizedStartScreenImage = StartScreenImage.getScaledInstance(BUTTONWIDTH, BUTTONHEIGHT, Image.SCALE_SMOOTH);
        StartScreenIcon = new ImageIcon(resizedStartScreenImage);
        //Make the StartScreenButton transparent
        StartScreenButton.setContentAreaFilled(false);
        StartScreenButton.setBorderPainted(false);
        StartScreenButton.setIcon(StartScreenIcon);
        //Add the StartScreenButton to the panel
        this.setLayout(null);
        this.add(StartScreenButton);
        StartScreenButton.setBounds(buttonX, BUTTONY, BUTTONWIDTH, BUTTONHEIGHT);
    }

    //Add actionlistener to the StartScreenButton button
    public void addStartScreenButtonActionListener(ActionListener listener) {
        StartScreenButton.addActionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            Font font = new Font("Serif", Font.PLAIN, 64);
            g.setFont(font);
            if (winner == "red") {
                g.drawString("Congrats player 2 ("+winner+"), you won!", 320, Math.toIntExact(Math.round(screenHeightText/1.84)));
            } else if (winner == "purple") {
                g.drawString("Congrats player 1 ("+winner+"), you won!", 300, Math.toIntExact(Math.round(screenHeightText/1.84)));
            } else if (winner == "bot") {
                g.drawString("Oh no you lost to the "+winner+", better luck next time!", 140, Math.toIntExact(Math.round(screenHeightText/1.84)));
            } else {
                g.drawString("That was a draw, try again to see who is better!", 140, Math.toIntExact(Math.round(screenHeightText/1.84)));
            }
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
