import java.awt.event.KeyEvent;

public class KeyInput2Thread implements Runnable {
    private final KeyEvent event;

    public KeyInput2Thread(KeyEvent event) {
        this.event = event;
    }

    @Override
    public synchronized void run() {
        if (GamePanel.running) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_A:
                    if (GamePanel.player2Direction != 'R') {
                        GamePanel.player2Direction = 'L';
                        System.out.println("Left key pressed by Player 2");
                    }
                    break;
                case KeyEvent.VK_D:
                    if (GamePanel.player2Direction != 'L') {
                        GamePanel.player2Direction = 'R';
                        System.out.println("Right key pressed by Player 2");
                    }
                    break;

                case KeyEvent.VK_W:
                    if (GamePanel.player2Direction != 'D') {
                        GamePanel.player2Direction = 'U';
                        System.out.println("Up key pressed by Player 2");
                    }
                    break;
                case KeyEvent.VK_S:
                    if (GamePanel.player2Direction != 'U') {
                        GamePanel.player2Direction = 'D';
                        System.out.println("Down key pressed by Player 2");
                    }
                    break;
            }
        }

    }
}

