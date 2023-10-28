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
    public JButton backToStart;

        public EndScreen(int screenWidth, int screenHeight) {
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
        backToStart = new JButton();
        int buttonX = (screenWidth - BUTTONWIDTH) / 2 + 200;
        //Set the icon of the multiplayer button
        ImageIcon multiPlayerIcon = new ImageIcon("MultiPlayerButton.png");
        Image multiPlayerImage = multiPlayerIcon.getImage();
        Image resizedMultiPlayerImage = multiPlayerImage.getScaledInstance(BUTTONWIDTH, BUTTONHEIGHT, Image.SCALE_SMOOTH);
        multiPlayerIcon = new ImageIcon(resizedMultiPlayerImage);
        //Make the multiplayer button transparent
        backToStart.setContentAreaFilled(false);
        backToStart.setBorderPainted(false);
        backToStart.setIcon(multiPlayerIcon);
        //Add the multiplayer to the panel
        this.setLayout(null);
        this.add(backToStart);
        backToStart.setBounds(buttonX, BUTTONY, BUTTONWIDTH, BUTTONHEIGHT);

    }

    //Add actionlistener to the multiplayer button
    public void addMultiPlayerActionListener(ActionListener listener) {
        backToStart.addActionListener(listener);
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

    }
}
