package test;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * klasa odpowiedzialna za weza AI
 */
public class SnakeAI {
    private ArrayList<Rectangle> body;
    private int w = Game.width;
    private int h = Game.height;
    private int d = Game.dimension;
    private Food f;
    private SnakeAI s;
    int x = 0;
    int y=0;

    private String move; //NOTHING, UP, DOWN, LEFT, RIGHT

    /**
     * konstruktor bezparametrowy odpowiedzialny za weza AI
     */
    public SnakeAI() {
        body = new ArrayList<>();

        Rectangle temp = new Rectangle(Game.dimension, Game.dimension);
        temp.setLocation(Game.width / 2 * Game.dimension, Game.height / 2 * Game.dimension);
        body.add(temp);

        temp = new Rectangle(d, d);
        temp.setLocation((w / 2 - 1) * d, (h / 2) * d);
        body.add(temp);

        temp = new Rectangle(d, d);
        temp.setLocation((w / 2 - 2) * d, (h / 2) * d);
        body.add(temp);

        move = "NOTHING";
    }


    /**
     * funkcja odpowiadajaca za ruch weza
     * @param x
     * @param y
     */
    public void move(int x,int y) {


        Rectangle first = body.get(0);
        Rectangle temp = new Rectangle(Game.dimension, Game.dimension);

            if (y == first.y) {
                if (x > first.x) { // w prawo
                    temp.setLocation(first.x + Game.dimension, first.y);
                    move = "RIGHT";
                } else {
                    temp.setLocation(first.x - Game.dimension, first.y);
                    move = "LEFT";
                }

            } else if (y > first.y) { // dol
                temp.setLocation(first.x, first.y + Game.dimension);
                move = "DOWN";
            } else { // gora
                temp.setLocation(first.x, first.y - Game.dimension);
                move = "UP";
            }

            body.add(0, temp);
            body.remove(body.size() - 1);

        }

    /**
     * funkcja odpowiadajaca za rosniecie weza AI
     */
    public void grow() {
        Rectangle first = body.get(body.size()-1);

        Rectangle temp = new Rectangle(Game.dimension, Game.dimension);

        if(move == "UP") {
            temp.setLocation(first.x, first.y);
        }
        else if(move == "DOWN") {
            temp.setLocation(first.x, first.y);
        }
        else if(move == "LEFT") {
            temp.setLocation(first.x - Game.dimension, first.y);
        }
        else{
            temp.setLocation(first.x + Game.dimension, first.y);
        }

        body.add(body.size(), temp);
    }
    //funkcje pomocnicze
    public ArrayList<Rectangle> getBody() {
        return body;
    }


    public void setBody(ArrayList<Rectangle> body) {
        this.body = body;
    }

    public int getX() {
        return body.get(0).x;
    }

    public int getY() {
        return body.get(0).y ;
    }

    public String getMove() {
        return move;
    }


}
