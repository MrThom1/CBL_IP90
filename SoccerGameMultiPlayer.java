import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SoccerGameMultiPlayer extends JPanel implements ActionListener, KeyListener {

    final private int PLAYER1_RADIUS = 60; 
    final private int PLAYER2_RADIUS = 60;  
    final private int BALL_RADIUS = 40;
    final private int PLAYER_SPEED = 5;
    
    private BufferedImage backgroundImage;  // Image for the background
    private BufferedImage player1Image; // Image for player 1
    private BufferedImage player2Image; // Image for player 2
    private BufferedImage ballImage;  // Image for the ball
    private int player1X;
    private int player1Y;
    private int player2X;
    private int player2Y;
    private int ballX;
    private int ballY;
    private double ballSpeedX = 0;
    private double ballSpeedY = 0; 
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;

    private Set<Integer> keysPressed = new HashSet<>();
    private Timer timer;

    private int player1Score = 0;
    private int player2Score = 0;

    private int timerSeconds = 2*60; 
    Timer gameTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timerSeconds--;
            if (timerSeconds == 0) {
                gameTimer.stop();
                Container frame = SoccerGameMultiPlayer.this.getParent(); //Get the frame
                SoccerGameMultiPlayer.this.setVisible(false);  // Hide the game panel
                frame.remove(SoccerGameMultiPlayer.this); //remove the game panel
                String winner;
                if (player1Score > player2Score) {
                    winner = "purple";
                } else if (player1Score < player2Score) {
                    winner = "red";
                } else {
                    winner = null;
                }
                EndScreen endScreen = new EndScreen(SCREEN_WIDTH, SCREEN_HEIGHT, winner, this); //create an Endscreen
                frame.add(endScreen);  // Add the Endscreen panel
                endScreen.requestFocus();
                endScreen.addStartScreenButtonActionListener(j -> {
                    endScreen.setVisible(false);  // Hide the start screen
                    frame.remove(endScreen);
                    StartScreen startScreen = new StartScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
                    frame.add(startScreen);  // Add the SoccerGame panel
                    startScreen.requestFocus();
                    startScreen.addMultiPlayerActionListener(g -> {
                        startScreen.setVisible(false);  // Hide the start screen
                        frame.remove(startScreen);
                        SoccerGameMultiPlayer soccerGame = new SoccerGameMultiPlayer(SCREEN_WIDTH, SCREEN_HEIGHT);
                        frame.add(soccerGame);  // Add the SoccerGame panel
                        soccerGame.requestFocus();
                        // Start the game
                        soccerGame.startSoccerGame(soccerGame);
                    });
                    startScreen.addSinglePlayerActionListener(h -> {
                        startScreen.setVisible(false);  // Hide the start screen
                        frame.remove(startScreen);
                        SoccerGameSinglePlayer soccerGame = new SoccerGameSinglePlayer(SCREEN_WIDTH, SCREEN_HEIGHT);
                        frame.add(soccerGame);  // Add the SoccerGame panel
                        soccerGame.requestFocus();
                        // Start the game
                        soccerGame.startSoccerGame(soccerGame);
                    });
                });
            }
        }
    });

    public SoccerGameMultiPlayer(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);

        // Set the ball to start in the middle
        ballX = SCREEN_WIDTH / 2;
        ballY = SCREEN_HEIGHT / 2;

        // Set players to the right positions
        player1X = SCREEN_WIDTH / 2 - 150;
        player1Y = SCREEN_HEIGHT / 2 - 1;
        player2X = SCREEN_WIDTH / 2 + 150;
        player2Y = SCREEN_HEIGHT / 2 + 1;

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
            g.drawImage(backgroundImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        }

        // Draw player 1 image
        if (player1Image != null) {
            g.drawImage(player1Image, player1X - PLAYER1_RADIUS, player1Y - PLAYER1_RADIUS, 2 * PLAYER1_RADIUS, 2 * PLAYER1_RADIUS, null);
        }

        // Draw player 2 image
        if (player2Image != null) {
            g.drawImage(player2Image, player2X - PLAYER2_RADIUS, player2Y - PLAYER2_RADIUS, 2 * PLAYER2_RADIUS, 2 * PLAYER2_RADIUS, null);
        }

        // Draw the ball image
        if (ballImage != null) {
            g.drawImage(ballImage, (int) (ballX - BALL_RADIUS), (int) (ballY - BALL_RADIUS), 2 * BALL_RADIUS, 2 * BALL_RADIUS, null);
        }

        // Display the timer on the screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        int minutes = timerSeconds / 60;
        int seconds = timerSeconds % 60;
        String timerString = String.format("%02d:%02d", minutes, seconds);
        g.drawString("" + timerString, SCREEN_WIDTH / 2 - 30, 42);

        // Draw the score for player 1
        g.drawString("" + player1Score, SCREEN_WIDTH / 2 - 125, 45);

        // Draw the score for player 2
        g.drawString("" + player2Score, SCREEN_WIDTH / 2 + 120, 45);
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

    /**
     * Handle the collision between the ball and the player
     *
     * @param playerX x-coordinate of player
     * @param playerY y-coordinate of player
     */  
    public void handleCollision(int playerX, int playerY) {
        int dx = ballX - playerX;
        int dy = ballY - playerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double unitX = dx / distance;
        double unitY = dy / distance;

        ballX = playerX + (int) ((PLAYER1_RADIUS + BALL_RADIUS) * unitX);
        ballY = playerY + (int) ((PLAYER1_RADIUS + BALL_RADIUS) * unitY);

        ballSpeedX = 10 * unitX;
        ballSpeedY = 10 * unitY;

    }
    
    //Handle the collision between the players
    public void handlePlayerCollision() {
        double dx = player2X - player1X;
        double dy = player2Y - player1Y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double overlap = PLAYER1_RADIUS + PLAYER2_RADIUS - distance;

        if (overlap > 0) {
            double angle = Math.atan2(dy, dx);

            int moveX = (int) (0.5 * overlap * Math.cos(angle));
            int moveY = (int) (0.5 * overlap * Math.sin(angle));

            int newPlayer1X = player1X - moveX;
            int newPlayer1Y = player1Y - moveY;
            int newPlayer2X = player2X + moveX;
            int newPlayer2Y = player2Y + moveY;

            if (newPlayer1X - PLAYER1_RADIUS > 0 && newPlayer1X + PLAYER1_RADIUS < SCREEN_WIDTH) {
                player1X = newPlayer1X;
            }
            if (newPlayer1Y - PLAYER1_RADIUS > 0 && newPlayer1Y + PLAYER1_RADIUS < SCREEN_HEIGHT) {
                player1Y = newPlayer1Y;
            }
            if (newPlayer2X - PLAYER2_RADIUS > 0 && newPlayer2X + PLAYER2_RADIUS < SCREEN_WIDTH) {
                player2X = newPlayer2X;
            }
            if (newPlayer2Y - PLAYER2_RADIUS > 0 && newPlayer2Y + PLAYER2_RADIUS < SCREEN_HEIGHT) {
                player2Y = newPlayer2Y;
            }
        }
    }
    
    //Move players depending on the which keys are pressed
    public void movePlayers() {
        int newPlayer1X = player1X;
        int newPlayer1Y = player1Y;

        if (keysPressed.contains(KeyEvent.VK_W) && player1Y - PLAYER_SPEED > PLAYER1_RADIUS + (0.10 * SCREEN_HEIGHT)) {
            newPlayer1Y -= PLAYER_SPEED;
        }
        if (keysPressed.contains(KeyEvent.VK_S) && player1Y + PLAYER_SPEED < SCREEN_HEIGHT - PLAYER1_RADIUS - (0.10 * SCREEN_HEIGHT)) {
            newPlayer1Y += PLAYER_SPEED;
        }
        if (keysPressed.contains(KeyEvent.VK_A) && player1X - PLAYER_SPEED > PLAYER1_RADIUS + (0.18 * SCREEN_HEIGHT)) {
            newPlayer1X -= PLAYER_SPEED;
        }
        if (keysPressed.contains(KeyEvent.VK_D) && player1X + PLAYER_SPEED < SCREEN_WIDTH - PLAYER1_RADIUS - (0.18 * SCREEN_HEIGHT)) {
            newPlayer1X += PLAYER_SPEED;
        }

        int newPlayer2X = player2X;
        int newPlayer2Y = player2Y;

        if (keysPressed.contains(KeyEvent.VK_UP) && player2Y - PLAYER_SPEED > PLAYER2_RADIUS + (0.10 * SCREEN_HEIGHT)) {
            newPlayer2Y -= PLAYER_SPEED;
        }
        if (keysPressed.contains(KeyEvent.VK_DOWN) && player2Y + PLAYER_SPEED < SCREEN_HEIGHT - PLAYER2_RADIUS - (0.10 * SCREEN_HEIGHT)) {
            newPlayer2Y += PLAYER_SPEED;
        }
        if (keysPressed.contains(KeyEvent.VK_LEFT) && player2X - PLAYER_SPEED > PLAYER2_RADIUS + (0.18 * SCREEN_HEIGHT)) {
            newPlayer2X -= PLAYER_SPEED;
        }
        if (keysPressed.contains(KeyEvent.VK_RIGHT) && player2X + PLAYER_SPEED < SCREEN_WIDTH - PLAYER2_RADIUS - (0.18 * SCREEN_HEIGHT)) {
            newPlayer2X += PLAYER_SPEED;
        }

        player1X = newPlayer1X;
        player1Y = newPlayer1Y;
        player2X = newPlayer2X;
        player2Y = newPlayer2Y;
    }
    
    //Check whether the ball is colliding with the border and if there collision between players
    public void checkCollisions() {
        int borderSizeX;
        if (ballY > (0.35 * SCREEN_HEIGHT) && ballY < (0.65 * SCREEN_HEIGHT)) {
            borderSizeX = Math.toIntExact(Math.round(SCREEN_WIDTH*0.0055));;
        } else {
            borderSizeX = Math.toIntExact(Math.round(SCREEN_WIDTH*0.078125*1.3));
        }
        int borderSizeY = Math.toIntExact(Math.round(SCREEN_HEIGHT*0.07407407407*1.2));

        if (ballX - BALL_RADIUS < borderSizeX) {
            ballX = borderSizeX + BALL_RADIUS;
            ballSpeedX = Math.abs(ballSpeedX);
        } else if (ballX + BALL_RADIUS > SCREEN_WIDTH - borderSizeX) {
            ballX = SCREEN_WIDTH - borderSizeX - BALL_RADIUS;
            ballSpeedX = -Math.abs(ballSpeedX);
        }

        if (ballY - BALL_RADIUS < borderSizeY) {
            ballY = borderSizeY + BALL_RADIUS;
            ballSpeedY = Math.abs(ballSpeedY);
        } else if (ballY + BALL_RADIUS > SCREEN_HEIGHT - borderSizeY) {
            ballY = SCREEN_HEIGHT - borderSizeY - BALL_RADIUS;
            ballSpeedY = -Math.abs(ballSpeedY);
        }

        handlePlayerCollision();

        double distancePlayer1 = Math.sqrt(Math.pow(ballX - player1X, 2) + Math.pow(ballY - player1Y, 2));
        if (distancePlayer1 < BALL_RADIUS + PLAYER1_RADIUS) {
            handleCollision(player1X, player1Y);
        }

        double distancePlayer2 = Math.sqrt(Math.pow(ballX - player2X, 2) + Math.pow(ballY - player2Y, 2));
        if (distancePlayer2 < BALL_RADIUS + PLAYER2_RADIUS) {
            handleCollision(player2X, player2Y);
        }
    }

    //Reset the ball to the center
    public void resetBallToCenter() {
        ballX = SCREEN_WIDTH / 2;
        ballY = SCREEN_HEIGHT / 2;
        ballSpeedX = 0;
        ballSpeedY = 0;
    }

    //Reset the players to the center
    public void resetPlayersToCenter() {
        player1X = SCREEN_WIDTH / 2 - 150;
        player1Y = SCREEN_HEIGHT / 2 - 1;
        player2X = SCREEN_WIDTH / 2 + 150;
        player2Y = SCREEN_HEIGHT / 2 + 1;
    }

    //Check whether a goal is scored
    public void checkGoal() {
        long goalBorder = Math.round(SCREEN_WIDTH*0.078125*1.3);
        if (ballX < Math.toIntExact(goalBorder-BALL_RADIUS) && (ballY > (0.35 * SCREEN_HEIGHT) && ballY < (0.65 * SCREEN_HEIGHT))) {
            resetBallToCenter();
            resetPlayersToCenter();
            player2Score++;
        } else if (ballX > SCREEN_WIDTH - Math.toIntExact(goalBorder - BALL_RADIUS) && (ballY > (0.35 * SCREEN_HEIGHT) && ballY < (0.65 * SCREEN_HEIGHT))) {
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

    public void startSoccerGame(SoccerGameMultiPlayer soccerGame) {
        soccerGame.gameTimer.start();
        
    }
}
