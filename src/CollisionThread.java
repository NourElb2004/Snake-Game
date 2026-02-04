

public class CollisionThread implements Runnable {

    @Override
    public synchronized void run() {
        if (GamePanel.gameTicks < 10) {
            return;
        }
        for (int i = 0; i < GamePanel.player2BodyParts; i++) {
            if (GamePanel.player1X[0] == GamePanel.player2X[i] && GamePanel.player1Y[0] == GamePanel.player2Y[i]) {
                GamePanel.running = false;
                System.out.println("Collision: Player 1 hit Player 2");
            }
        }

        for (int i = 0; i < GamePanel.player1BodyParts; i++) {
            if (GamePanel.player2X[0] == GamePanel.player1X[i] && GamePanel.player2Y[0] == GamePanel.player1Y[i]) {
                GamePanel.running = false;
                System.out.println("Collision: Player 2 hit Player 1");
            }
        }

        for (int i = GamePanel.player1BodyParts; i > 0; i--) {
            if (GamePanel.player1X[0] == GamePanel.player1X[i] && GamePanel.player1Y[0] == GamePanel.player1Y[i]) {
                GamePanel.running = false;
                System.out.println("Collision: Player 1 hit itself");
            }
        }

        for (int i = GamePanel.player2BodyParts; i > 0; i--) {
            if (GamePanel.player2X[0] == GamePanel.player2X[i] && GamePanel.player2Y[0] == GamePanel.player2Y[i]) {
                GamePanel.running = false;
                System.out.println("Collision: Player 2 hit itself");
            }
        }

        if (GamePanel.player1X[0] < 0 || GamePanel.player1X[0] >= GamePanel.SCREEN_WIDTH || GamePanel.player1Y[0] < 0 || GamePanel.player1Y[0] >= GamePanel.SCREEN_HEIGHT ||
    GamePanel.player2X[0] < 0 || GamePanel.player2X[0] >= GamePanel.SCREEN_WIDTH || GamePanel.player2Y[0] < 0 || GamePanel.player2Y[0] >= GamePanel.SCREEN_HEIGHT) {
        GamePanel.running = false;
        System.out.println("Collision with wall detected");

    }

        if (GamePanel.player2X[0] == GamePanel.appleX && GamePanel.player2Y[0] == GamePanel.appleY) {
            GamePanel.appleEatenBy = 2;
            System.out.println("Player 2 ate the apple!");
        }

        if (GamePanel.player1X[0] == GamePanel.appleX && GamePanel.player1Y[0] == GamePanel.appleY) {
        GamePanel.appleEatenBy = 1;
        System.out.println("Player 1 ate the apple!");
    }

        if (!GamePanel.running) {
        GamePanel.timer.stop();
    }

    }
}



