package test;

import java.awt.Rectangle;

/**
 * klasa odpowiedzialna za przeszkody pojawiajace sie na planszy rozgrywki
 */
public class Obstacle {
    int obstacle = 8;
    int[] obstacleX = new int[obstacle];
    int[] obstacleY = new int[obstacle];

    public Obstacle(Snake player) {
        this.random_spawn(player);
    }

    /**
     * funkcja odpowiadajaca za losowe pojawianie sie przeszkód
     * na planszy gry.
     * @param player
     */
    public void random_spawn(Snake player) {
        boolean onSnake = true;
        while(onSnake) {
            onSnake = false;
            for(int i = 0;i<obstacle;i++) {
                obstacleX[i] = (int) (Math.random() * Game.width - 1);
                obstacleY[i] = (int) (Math.random() * Game.height - 1);

                for (Rectangle r : player.getBody()) {
                    if (r.x == obstacleX[i] && r.y == obstacleY[i]) {
                        onSnake = true;
                    }
                }
            }
        }
    }
/*
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }*/
}
