public class MovementThread implements Runnable {
    @Override
    public synchronized void run() {
       try {
            for (int i = GamePanel.player1BodyParts; i > 0; i--) {
                GamePanel.player1X[i] = GamePanel.player1X[i - 1];
                GamePanel.player1Y[i] = GamePanel.player1Y[i - 1];
            }

            switch (GamePanel.player1Direction) {
                case 'U':
                    GamePanel.player1Y[0] -= GamePanel.UNIT_SIZE;
                    break;
                case 'D':
                    GamePanel.player1Y[0] += GamePanel.UNIT_SIZE;
                    break;
                case 'L':
                    GamePanel.player1X[0] -= GamePanel.UNIT_SIZE;
                    break;
                case 'R':
                    GamePanel.player1X[0] += GamePanel.UNIT_SIZE;
                    break;
            }

            for (int i = GamePanel.player2BodyParts; i > 0; i--) {
                GamePanel.player2X[i] = GamePanel.player2X[i - 1];
                GamePanel.player2Y[i] = GamePanel.player2Y[i - 1];
            }
            switch (GamePanel.player2Direction) {
                case 'U':
                    GamePanel.player2Y[0] -= GamePanel.UNIT_SIZE;
                    break;
                case 'D':
                    GamePanel.player2Y[0] += GamePanel.UNIT_SIZE;
                    break;
                case 'L':
                    GamePanel.player2X[0] -=GamePanel. UNIT_SIZE;
                    break;
                case 'R':
                    GamePanel.player2X[0] += GamePanel.UNIT_SIZE;
                    break;
            }
            if (GamePanel.running) {
                System.out.println("MovementThread is running");
            }
            Thread.sleep(GamePanel.DELAY);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
