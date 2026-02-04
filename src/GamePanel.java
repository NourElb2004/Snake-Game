import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static int DELAY = 110;
    static int applesEatenPlayer1 = 0;
    static int applesEatenPlayer2 = 0;
    public static volatile int appleEatenBy = 0;

    static final int[] player1X = new int[GAME_UNITS];
    static final int[] player1Y = new int[GAME_UNITS];
    static final int[] player2X = new int[GAME_UNITS];
    static final int[] player2Y = new int[GAME_UNITS];

    static int player1BodyParts = 6;
    static int player2BodyParts = 6;
    static int appleX;
    static int appleY;

    static char player1Direction = 'R';
    static char player2Direction = 'L';

    static boolean running = false;
    static Timer timer;
    static int gameTicks = 0;
    static Random random;
    Image appleImage;
    Image player1HeadImage;
    Image player2HeadImage;

    ExecutorService threadPool = Executors.newFixedThreadPool(6);

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        appleImage = new ImageIcon("src/Images/apple_00.png").getImage();
        player1HeadImage = new ImageIcon("src/Images/snake1.png").getImage();
        player2HeadImage = new ImageIcon("src/Images/snake2.png").getImage();

        for (int i = 0; i < player1BodyParts; i++) {
            player1X[i] = i * UNIT_SIZE;
            player1Y[i] = 0;
        }
        for (int i = 0; i < player2BodyParts; i++) {
            player2X[i] = SCREEN_WIDTH - (i + 1) * UNIT_SIZE;
            player2Y[i] = SCREEN_HEIGHT - UNIT_SIZE;
        }
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            draw(g);
        } else {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException ex) {
                threadPool.shutdownNow();
            }
            gameOver(g);
            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                   if (key == KeyEvent.VK_2) {
                        System.out.println("Window closed!");
                        System.exit(0);
                    }
                }
            });
        }
    }

    public void draw(Graphics g) {
            if (gameTicks < 10) {
                    g.setColor(Color.black);
                    g.setFont(new Font("Ink Free", Font.BOLD, 50));
                    FontMetrics metrics = getFontMetrics(g.getFont());

                    if (gameTicks < 5) {
                        String welcomeMessage = "Welcome to our Snake Game!";
                        g.drawString(welcomeMessage, (SCREEN_WIDTH - metrics.stringWidth(welcomeMessage)) / 2, SCREEN_HEIGHT / 2);
                    } else {
                        String countdown = String.valueOf(10 - gameTicks);
                        g.drawString(countdown, (SCREEN_WIDTH - metrics.stringWidth(countdown)) / 2, SCREEN_HEIGHT / 2);
                    }
                    return;
            }

        for (int i = 0; i < player1BodyParts; i++) {
            if (i == 0) {
                Graphics2D g2d = (Graphics2D) g;
                int angle = 0;

                switch (player1Direction) {
                    case 'U':
                        angle = -90;
                        break;
                    case 'D':
                        angle = 90;
                        break;
                    case 'L':
                        angle = 180;
                        break;
                    case 'R':
                        angle = 0;
                        break;
                }

                int xPos = player1X[i];
                int yPos = player1Y[i];
                int xCenter = xPos + (UNIT_SIZE / 2);
                int yCenter = yPos + (UNIT_SIZE / 2);

                g2d.rotate(Math.toRadians(angle), xCenter, yCenter);
                g2d.drawImage(player1HeadImage, xPos, yPos, UNIT_SIZE, UNIT_SIZE, this);
                g2d.rotate(-Math.toRadians(angle), xCenter, yCenter);
            } else {
                g.setColor(Color.decode("#fbbc05"));
                g.fillOval(player1X[i], player1Y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
            for (int i = 0; i < player2BodyParts; i++) {
                if (i == 0) {

                    Graphics2D g2d = (Graphics2D) g;
                    int angle = 0;

                    switch (player2Direction) {
                        case 'U':
                            angle = -90;
                            break;
                        case 'D':
                            angle = 90;
                            break;
                        case 'L':
                            angle = 180;
                            break;
                        case 'R':
                            angle = 0;
                            break;
                    }

                    int xPos = player2X[i];
                    int yPos = player2Y[i];
                    int xCenter = xPos + (UNIT_SIZE / 2);
                    int yCenter = yPos + (UNIT_SIZE / 2);

                    g2d.rotate(Math.toRadians(angle), xCenter, yCenter);
                    g2d.drawImage(player2HeadImage, xPos, yPos, UNIT_SIZE, UNIT_SIZE, this);
                    g2d.rotate(-Math.toRadians(angle), xCenter, yCenter); // Reset rotation
                } else {
                    g.setColor(Color.decode("#ea4335"));
                    g.fillOval(player2X[i], player2Y[i], UNIT_SIZE, UNIT_SIZE);
                }}

                g.drawImage(appleImage, appleX, appleY, UNIT_SIZE, UNIT_SIZE, this);

                g.setColor(Color.red);
                g.setFont(new Font("Monotype Corsiva", Font.BOLD, 40));
                FontMetrics metrics1 = getFontMetrics(g.getFont());
                String player1Score = "Player 1: " + applesEatenPlayer1;
                g.drawString(player1Score, (SCREEN_WIDTH / 4) - (metrics1.stringWidth(player1Score) / 2), g.getFont().getSize());
                String player2Score = "Player 2: " + applesEatenPlayer2;
                g.drawString(player2Score, (3 * SCREEN_WIDTH / 4) - (metrics1.stringWidth(player2Score) / 2), g.getFont().getSize());


        }


    public void updateDelay() {
        int totalApplesEaten = applesEatenPlayer1 + applesEatenPlayer2;
        int newDelay = 110 - ((totalApplesEaten / 2) * 20);
        newDelay = Math.max(newDelay, 50);
        if (newDelay != DELAY) {
            DELAY = newDelay;
            System.out.println("New Delay: " + DELAY);
            timer.setDelay(DELAY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MovementThread move = new MovementThread();
        CollisionThread collision = new CollisionThread();
        ScoreUpdateThread score = new ScoreUpdateThread();
        if (running) {
            gameTicks++;
            threadPool.execute(move);
            threadPool.execute(collision);
            threadPool.execute(score);
            updateDelay();
        }
        repaint();
    }

    public static void newApple() {
        boolean validPosition;
        do {
            validPosition = true;
            appleX = (int) (Math.random() * (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            appleY = (int) (Math.random() * (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

            for (int i = 0; i < player1BodyParts; i++) {
                if (appleX == player1X[i] && appleY == player1Y[i]) {
                    validPosition = false;
                    break;
                }
            }
            for (int i = 0; i < player2BodyParts; i++) {
                if (appleX == player2X[i] && appleY == player2Y[i]) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Monotype Corsiva", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());

        String player1Score = "Player 1: " + applesEatenPlayer1;
        g.drawString(player1Score, (SCREEN_WIDTH / 4) - (metrics1.stringWidth(player1Score) / 2), g.getFont().getSize());

        String player2Score = "Player 2: " + applesEatenPlayer2;
        g.drawString(player2Score, (3 * SCREEN_WIDTH / 4) - (metrics1.stringWidth(player2Score) / 2), g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont(new Font("Monotype Corsiva", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.black);
        g.setFont(new Font("Monotype Corsiva", Font.BOLD, 30));
        String restartMessage = "             Press 2 to Exit";
        g.drawString(restartMessage, (SCREEN_WIDTH - metrics1.stringWidth(restartMessage)) / 2, SCREEN_HEIGHT / 2 + 60);
    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            KeyInputThread input1 = new KeyInputThread(e);
            KeyInput2Thread input2 = new KeyInput2Thread(e);
            try {
                if (threadPool != null && !threadPool.isShutdown()) {
                    threadPool.execute(input1);
                    threadPool.execute(input2);
                } else {
                    System.err.println("Thread pool is shut down. Task rejected.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
