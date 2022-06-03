package test;

import java.awt.Rectangle;

/**
 * klasa odpowiedzialna za powstajace jedzenie na planszny gry
 */
public class Food {
    private int x;
    private int y;

    public Food(Snake player, SnakeAI playerAI) {
        this.random_spawn(player, playerAI);
    }

    /**
     * funkcja odpowiadajaca za losowe pojawianie sie jabłek na planszy gry
     * @param player
     * @param playerAI
     */
    public void random_spawn(Snake player, SnakeAI playerAI) {
        boolean onSnake = true;
        while(onSnake) {
            onSnake = false;

            x = (int)(Math.random() * Game.width - 1);
            y = (int)(Math.random() * Game.height - 1);

            for(Rectangle r : player.getBody()){
                if(r.x == x && r.y == y) {
                    onSnake = true;
                }
            }
            for(Rectangle r : playerAI.getBody()){
                if(r.x == x && r.y == y) {
                    onSnake = true;
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
