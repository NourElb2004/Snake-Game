import java.awt.event.KeyEvent;

public class KeyInputThread implements Runnable {
    private final KeyEvent event;

    public KeyInputThread(KeyEvent event) {
        this.event = event;
    }

    @Override
    public synchronized void run() {
            if (GamePanel.running) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (GamePanel.player1Direction != 'R') {
                            GamePanel.player1Direction = 'L';
                            System.out.println("Left key pressed by Player 1");
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (GamePanel.player1Direction != 'L') {
                            GamePanel.player1Direction = 'R';
                            System.out.println("Right key pressed by Player 1");
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (GamePanel.player1Direction != 'D') {
                            GamePanel.player1Direction = 'U';
                            System.out.println("Up key pressed by Player 1");
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (GamePanel.player1Direction != 'U') {
                            GamePanel.player1Direction = 'D';
                            System.out.println("Down key pressed by Player 1");
                        }
                        break;
                }
            }

    }
}

