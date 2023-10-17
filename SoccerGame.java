import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SoccerGame extends JPanel implements ActionListener, KeyListener {

    private BufferedImage backgroundImage;  // Image for the background
    private BufferedImage player1Image; // Image for player 1
    private BufferedImage player2Image; // Image for player 2
    private BufferedImage ballImage;  // Image for the ball
    private int player1X;
    private int player1Y;
    private int player1Radius = 60;
    private int player2X;
    private int player2Y;
    private int player2Radius = 60;
    private int ballX;
    private int ballY;
    private int ballRadius = 40;
    private double ballSpeedX = 0;
    private double ballSpeedY = 0;
    private int playerSpeed = 5;
    private int screenWidth;
    private int screenHeight;

    private Set<Integer> keysPressed = new HashSet<>();
    private Timer timer;

    private int player1Score = 0;
    private int player2Score = 0;

    private int timerSeconds = 5 * 60;  // 5 minutes timer
    private Timer gameTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timerSeconds--;
            if (timerSeconds == 0) {
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "Game Over! Time's up.");
            }
        }
    });

    public SoccerGame(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);

        // Set the ball to start in the middle
        ballX = screenWidth / 2;
        ballY = screenHeight / 2;

        // Set players to the right positions
        player1X = screenWidth / 2 - 150;
        player1Y = screenHeight / 2 - 1;
        player2X = screenWidth / 2 + 150;
        player2Y = screenHeight / 2 + 1;

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("background.png"));  // Provide the correct path to your image file
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load player 1 image
        try {
            player1Image = ImageIO.read(new File("Player1.png"));  // Provide the correct path to your image file
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load player 2 image
        try {
            player2Image = ImageIO.read(new File("Player2.png"));  // Provide the correct path to your image file
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load the ball image
        try {
            ballImage = ImageIO.read(new File("Football.png"));  // Provide the correct path to your image file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
        }

        // Draw player 1 image
        if (player1Image != null) {
            g.drawImage(player1Image, player1X - player1Radius, player1Y - player1Radius, 2 * player1Radius, 2 * player1Radius, null);
        }

        // Draw player 2 image
        if (player2Image != null) {
            g.drawImage(player2Image, player2X - player2Radius, player2Y - player2Radius, 2 * player2Radius, 2 * player2Radius, null);
        }

        // Draw the ball image
        if (ballImage != null) {
            g.drawImage(ballImage, (int) (ballX - ballRadius), (int) (ballY - ballRadius), 2 * ballRadius, 2 * ballRadius, null);
        }

        // Display the timer on the screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        int minutes = timerSeconds / 60;
        int seconds = timerSeconds % 60;
        String timerString = String.format("%02d:%02d", minutes, seconds);
        g.drawString("" + timerString, screenWidth / 2 - 30, 42);

        // Draw the score for player 1
        g.drawString("" + player1Score, screenWidth / 2 - 125, 45);

        // Draw the score for player 2
        g.drawString("" + player2Score, screenWidth / 2 + 120, 45);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double speedReduction = 0.025;
        ballSpeedX -= ballSpeedX * speedReduction;
        ballSpeedY -= ballSpeedY * speedReduction;

        double speedThreshold = 0.1;
        if (Math.abs(ballSpeedX) < speedThreshold) {
            ballSpeedX = 0;
        }
        if (Math.abs(ballSpeedY) < speedThreshold) {
            ballSpeedY = 0;
        }

        ballX += ballSpeedX;
        ballY += ballSpeedY;
        checkCollisions();

        movePlayers();

        checkGoal();

        repaint();
    }

    public void handleCollision(int playerX, int playerY) {
        int dx = ballX - playerX;
        int dy = ballY - playerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double unitX = dx / distance;
        double unitY = dy / distance;

        ballX = playerX + (int) ((player1Radius + ballRadius) * unitX);
        ballY = playerY + (int) ((player1Radius + ballRadius) * unitY);

        ballSpeedX = 10 * unitX;
        ballSpeedY = 10 * unitY;

    }

    public void handlePlayerCollision() {
        double dx = player2X - player1X;
        double dy = player2Y - player1Y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double overlap = player1Radius + player2Radius - distance;

        if (overlap > 0) {
            double angle = Math.atan2(dy, dx);

            int moveX = (int) (0.5 * overlap * Math.cos(angle));
            int moveY = (int) (0.5 * overlap * Math.sin(angle));

            int newPlayer1X = player1X - moveX;
            int newPlayer1Y = player1Y - moveY;
            int newPlayer2X = player2X + moveX;
            int newPlayer2Y = player2Y + moveY;

            if (newPlayer1X - player1Radius > 0 && newPlayer1X + player1Radius < screenWidth) {
                player1X = newPlayer1X;
            }
            if (newPlayer1Y - player1Radius > 0 && newPlayer1Y + player1Radius < screenHeight) {
                player1Y = newPlayer1Y;
            }
            if (newPlayer2X - player2Radius > 0 && newPlayer2X + player2Radius < screenWidth) {
                player2X = newPlayer2X;
            }
            if (newPlayer2Y - player2Radius > 0 && newPlayer2Y + player2Radius < screenHeight) {
                player2Y = newPlayer2Y;
            }
        }
    }

    public void movePlayers() {
        int newPlayer1X = player1X;
        int newPlayer1Y = player1Y;

        if (keysPressed.contains(KeyEvent.VK_W) && player1Y - playerSpeed > player1Radius + (0.10 * screenHeight)) {
            newPlayer1Y -= playerSpeed;
        }
        if (keysPressed.contains(KeyEvent.VK_S) && player1Y + playerSpeed < screenHeight - player1Radius - (0.10 * screenHeight)) {
            newPlayer1Y += playerSpeed;
        }
        if (keysPressed.contains(KeyEvent.VK_A) && player1X - playerSpeed > player1Radius + (0.18 * screenHeight)) {
            newPlayer1X -= playerSpeed;
        }
        if (keysPressed.contains(KeyEvent.VK_D) && player1X + playerSpeed < screenWidth - player1Radius - (0.18 * screenHeight)) {
            newPlayer1X += playerSpeed;
        }

        int newPlayer2X = player2X;
        int newPlayer2Y = player2Y;

        if (keysPressed.contains(KeyEvent.VK_UP) && player2Y - playerSpeed > player2Radius + (0.10 * screenHeight)) {
            newPlayer2Y -= playerSpeed;
        }
        if (keysPressed.contains(KeyEvent.VK_DOWN) && player2Y + playerSpeed < screenHeight - player2Radius - (0.10 * screenHeight)) {
            newPlayer2Y += playerSpeed;
        }
        if (keysPressed.contains(KeyEvent.VK_LEFT) && player2X - playerSpeed > player2Radius + (0.18 * screenHeight)) {
            newPlayer2X -= playerSpeed;
        }
        if (keysPressed.contains(KeyEvent.VK_RIGHT) && player2X + playerSpeed < screenWidth - player2Radius - (0.18 * screenHeight)) {
            newPlayer2X += playerSpeed;
        }

        player1X = newPlayer1X;
        player1Y = newPlayer1Y;
        player2X = newPlayer2X;
        player2Y = newPlayer2Y;
    }

    public void checkCollisions() {
        int borderSizeX;
        if (ballY > (0.35 * screenHeight) && ballY < (0.65 * screenHeight)) {
            borderSizeX = 10;
        } else {
            borderSizeX = 150;
        }
        int borderSizeY = 80;

        if (ballX - ballRadius < borderSizeX) {
            ballX = borderSizeX + ballRadius;
            ballSpeedX = Math.abs(ballSpeedX);
        } else if (ballX + ballRadius > screenWidth - borderSizeX) {
            ballX = screenWidth - borderSizeX - ballRadius;
            ballSpeedX = -Math.abs(ballSpeedX);
        }

        if (ballY - ballRadius < borderSizeY) {
            ballY = borderSizeY + ballRadius;
            ballSpeedY = Math.abs(ballSpeedY);
        } else if (ballY + ballRadius > screenHeight - borderSizeY) {
            ballY = screenHeight - borderSizeY - ballRadius;
            ballSpeedY = -Math.abs(ballSpeedY);
        }

        handlePlayerCollision();

        double distancePlayer1 = Math.sqrt(Math.pow(ballX - player1X, 2) + Math.pow(ballY - player1Y, 2));
        if (distancePlayer1 < ballRadius + player1Radius) {
            handleCollision(player1X, player1Y);
        }

        double distancePlayer2 = Math.sqrt(Math.pow(ballX - player2X, 2) + Math.pow(ballY - player2Y, 2));
        if (distancePlayer2 < ballRadius + player2Radius) {
            handleCollision(player2X, player2Y);
        }
    }

    public void resetBallToCenter() {
        ballX = screenWidth / 2;
        ballY = screenHeight / 2;
        ballSpeedX = 0;
        ballSpeedY = 0;
    }

    public void resetPlayersToCenter() {
        player1X = screenWidth / 2 - 150;
        player1Y = screenHeight / 2 - 1;
        player2X = screenWidth / 2 + 150;
        player2Y = screenHeight / 2 + 1;
    }

    public void checkGoal() {
        if (ballX < (155 - ballRadius)) {
            resetBallToCenter();
            resetPlayersToCenter();
            player2Score++;
        } else if (ballX > (screenWidth - 155 + ballRadius)) {
            resetBallToCenter();
            resetPlayersToCenter();
            player1Score++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        keysPressed.add(key);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        keysPressed.remove(key);
    }

    public void startSoccerGame(SoccerGame soccerGame) {
        soccerGame.gameTimer.start();
        
    }
}