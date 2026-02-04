public class ScoreUpdateThread implements Runnable {
    @Override
    public synchronized void run() {
        if (GamePanel.appleEatenBy == 1) {
            GamePanel.player1BodyParts++;
            GamePanel.applesEatenPlayer1++;
            System.out.println("Player 1 score updated: " + GamePanel.applesEatenPlayer1);
            GamePanel.newApple();
            GamePanel.appleEatenBy = 0;
        }
        if (GamePanel.appleEatenBy == 2) {
            GamePanel.player2BodyParts++;
            GamePanel.applesEatenPlayer2++;
            System.out.println("Player 2 score updated: " + GamePanel.applesEatenPlayer2);
            GamePanel.newApple();
            GamePanel.appleEatenBy = 0;
        }

        }


}


