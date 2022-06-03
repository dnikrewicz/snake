package test;

import java.awt.Rectangle;

/**
 * klasa odpowiedzialna za pojawianie sie zaby na plaszy gry
 */
public class Frog {
    private int x;
    private int y;

    public Frog(Snake player) {
        this.random_spawn(player);
    }

    /**
     * Funkcja odpowiadajaca za losowe pojawianie sie zaby na planszy gry
     * @param player
     */
    public void random_spawn(Snake player) {
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
        }
    }
//funkcje pomocnicze
    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

}