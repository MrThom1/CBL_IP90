

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.*;
public class SoccerGameSinglePlayer extends JPanel implements ActionListener, KeyListener {
    
    private final int PLAYER1_RADIUS = 60;
    private final int BOT_RADIUS = 60;
    private final int PLAYER_SPEED = 5;
    private final int BALL_RADIUS = 40;

    private BufferedImage backgroundImage;  // Image for the background
    private BufferedImage player1Image; // Image for player 1
    private BufferedImage botImage; // Image for player 2
    private BufferedImage ballImage;  // Image for the ball
    private int player1X;
    private int player1Y;
    private int botX;
    private int botY;
    private int ballX;
    private int ballY;
    private double ballSpeedX = 0;
    private double ballSpeedY = 0;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
    private int stepX;
    private int stepY;
    private Set<Integer> keysPressed = new HashSet<>();
    private Timer timer;

    private boolean botBehind = false;
    private int inverseX = 1;
    private int inverseY = 1;
    private long startInverseX = 0;
    
    private int player1Score = 0;
    private int botScore = 0;

    private int timerSeconds = 2*60;
    private Timer gameTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timerSeconds--;
            if (timerSeconds == 0) {
                gameTimer.stop();
                Container frame = SoccerGameSinglePlayer.this.getParent(); //Get the frame
                SoccerGameSinglePlayer.this.setVisible(false);  // Hide the game panel
                frame.remove(SoccerGameSinglePlayer.this); //remove the game panel
                String winner;
                if (player1Score > botScore) {
                    winner = "purple";
                } else if (player1Score < botScore) {
                    winner = "bot";
                } else {
                    winner = null;
                }
                EndScreen endScreen = new EndScreen(SCREEN_WIDTH, SCREEN_HEIGHT, winner, this); 
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

    public SoccerGameSinglePlayer(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
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
        botX = SCREEN_WIDTH / 2 + 150;
        botY = SCREEN_HEIGHT / 2 + 1;

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load player 1 image
        try {
            player1Image = ImageIO.read(new File("Player1.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load bot image
        try {
            botImage = ImageIO.read(new File("Bot.png"));  
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load the ball image
        try {
            ballImage = ImageIO.read(new File("Football.png"));  
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
    
        // Draw bot image
        if (botImage != null) {
            g.drawImage(botImage, botX - BOT_RADIUS, botY - BOT_RADIUS, 2 * BOT_RADIUS, 2 * BOT_RADIUS, null);
    
        // Draw the bot's name tag
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Bot", botX - 15, botY - BOT_RADIUS - 5);
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
        g.drawString("" + botScore, SCREEN_WIDTH / 2 + 120, 45);
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
        

        movePlayer1();

        moveBot();
        checkCollisions();
        checkGoal();

        repaint();
    }

    private void handleCollision(int playerX, int playerY) {
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

    private void handlePlayerbotCollision() {
        double dx = botX - player1X;
        double dy = botY - player1Y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double overlap = PLAYER1_RADIUS + BOT_RADIUS - distance;

        if (overlap > 0) {
            double angle = Math.atan2(dy, dx);

            int moveX = (int) (0.5 * overlap * Math.cos(angle));
            int moveY = (int) (0.5 * overlap * Math.sin(angle));

            int newPlayer1X = player1X - moveX;
            int newPlayer1Y = player1Y - moveY;
            int newbotX = botX + moveX;
            int newbotY = botY + moveY;

            if (newPlayer1X - PLAYER1_RADIUS > 0 && newPlayer1X + PLAYER1_RADIUS < SCREEN_WIDTH) {
                player1X = newPlayer1X;
            }
            if (newPlayer1Y - PLAYER1_RADIUS > 0 && newPlayer1Y + PLAYER1_RADIUS < SCREEN_HEIGHT) {
                player1Y = newPlayer1Y;
            }
            if (newbotX - BOT_RADIUS > 0 && newbotX + BOT_RADIUS < SCREEN_WIDTH) {
                botX = newbotX;
            }
            if (newbotY - BOT_RADIUS > 0 && newbotY + BOT_RADIUS < SCREEN_HEIGHT) {
                botY = newbotY;
            }
        }
    }

    private void movePlayer1() {
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

        player1X = newPlayer1X;
        player1Y = newPlayer1Y;
    }

    private void moveBotBehindBall() {
        float deltaXToGoal = ballX;
        float deltaYToGoal = ballY-SCREEN_HEIGHT/2;
        int desiredX = ballX + BALL_RADIUS*2;
        int desiredY = Math.round((deltaYToGoal/deltaXToGoal)*desiredX+SCREEN_HEIGHT/2);
        double distance = Math.sqrt((desiredX-botX) * (desiredX-botX) + (desiredY-botY) * (desiredY-botY));
        double proportion = PLAYER_SPEED / distance;
        botBehind = true;
        if (Math.abs(desiredX-botX)>10) {
            stepX = (int) ((desiredX-botX) * proportion);
        } else if (Math.abs(desiredY-botY)>10) {
            stepY = (int) ((desiredY-botY) * proportion);
        } else {
            botBehind = false;
        }
    }

    private void moveBot() {
        int borderSizeX;
        if (ballY > (0.35 * SCREEN_HEIGHT) && ballY < (0.65 * SCREEN_HEIGHT)) {
            borderSizeX = Math.toIntExact(Math.round(SCREEN_WIDTH*0.0055));
        } else {
            borderSizeX = Math.toIntExact(Math.round(SCREEN_WIDTH*0.078125*1.3));
        }
        int borderSizeY = Math.toIntExact(Math.round(SCREEN_HEIGHT*0.07407407407*1.2));
        // Calculate the direction towards the ball
        int deltaX = ballX - botX;
        int deltaY = ballY - botY;
    
        // Calculate the distance to the ball
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    
        // Ensure the distance is greater than 0 to avoid division by zero
        if (distance > 0) {
            // Calculate a proportion of the distance as the movement step
            double proportion = PLAYER_SPEED / distance;
            stepX = (int) (deltaX * proportion);
            stepY = (int) (deltaY * proportion);
            
            // Move towards the ball
            if (distance < 150 && inverseX == 1) {
                if ((ballX - BALL_RADIUS < borderSizeX | ballX + BALL_RADIUS > SCREEN_WIDTH - borderSizeX) && (ballY - BALL_RADIUS < borderSizeY | ballY + BALL_RADIUS > SCREEN_HEIGHT - borderSizeY)) {
                    inverseX = -1;
                    inverseY = -1;
                    startInverseX = System.currentTimeMillis();
                }

            }
            if (System.currentTimeMillis()-startInverseX > 1000){
                inverseX = 1;
                inverseY = 1;
            }

            if (botX<=ballX | botBehind){
                moveBotBehindBall();
                botX += stepX*inverseX;
                botY += stepY*inverseY;
            } else {
                botX += stepX*inverseX;
                botY += stepY*inverseY;
            }
            // Update Bot's position to the nearest valid position within the screen boundaries
            botX = Math.max(Math.toIntExact(Math.round(SCREEN_WIDTH*0.15))-BOT_RADIUS, botX);
            botX = Math.min(Math.toIntExact(Math.round(SCREEN_WIDTH*0.85))+BOT_RADIUS, botX);
            botY = Math.min(Math.toIntExact(Math.round(SCREEN_HEIGHT*0.9))-BOT_RADIUS, botY);
            botY = Math.max(Math.toIntExact(Math.round(SCREEN_HEIGHT*0.1))+BOT_RADIUS, botY);

        } else {
            System.out.println("Distance is 0. Cannot calculate movement step.");
        }
    }

    private void checkCollisions() {
        int borderSizeX;
        if (ballY > (0.35 * SCREEN_HEIGHT) && ballY < (0.65 * SCREEN_HEIGHT)) {
            borderSizeX = Math.toIntExact(Math.round(SCREEN_WIDTH*0.0055));
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

        handlePlayerbotCollision();

        double distancePlayer1 = Math.sqrt(Math.pow(ballX - player1X, 2) + Math.pow(ballY - player1Y, 2));
        if (distancePlayer1 < BALL_RADIUS + PLAYER1_RADIUS) {
            handleCollision(player1X, player1Y);
        }

        double distancebot = Math.sqrt(Math.pow(ballX - botX, 2) + Math.pow(ballY - botY, 2));
        if (distancebot < BALL_RADIUS + BOT_RADIUS) {
            handleCollision(botX, botY);
        }
    }

    private void resetBallToCenter() {
        ballX = SCREEN_WIDTH / 2;
        ballY = SCREEN_HEIGHT / 2;
        ballSpeedX = 0;
        ballSpeedY = 0;
    }

    private void resetPlayersToCenter() {
        player1X = SCREEN_WIDTH / 2 - 150;
        player1Y = SCREEN_HEIGHT / 2 - 1;
        botX = SCREEN_WIDTH / 2 + 150;
        botY = SCREEN_HEIGHT / 2 + 1;
        botBehind = false;
    }
    
    //Check whether a goal is scored
    private void checkGoal() {
        long goalBorder = Math.round(SCREEN_WIDTH*0.078125*1.3);
        if (ballX < Math.toIntExact(goalBorder-BALL_RADIUS) && (ballY > (0.35 * SCREEN_HEIGHT) && ballY < (0.65 * SCREEN_HEIGHT))) {
            resetBallToCenter();
            resetPlayersToCenter();
            botScore++;
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

    public void startSoccerGame(SoccerGameSinglePlayer soccerGame) {
        soccerGame.gameTimer.start();
        
    }
}
