package test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.JFrame;

/**
 * klasa odpowiedzialna za "silnik gry"
 */
public class Game
        implements KeyListener{
    private Snake player;
    private SnakeAI playerAI;
    Food food;
    private Frog frog;
    private Graphics graphics;
    private Obstacle obstacle; ////////////////////////////////////////////////////////
    public FileWriter file;
    private JFrame window;
    public static final int width = 30;
    public static final int height = 30;
    public static final int dimension = 20;
    int number = 1234567;

    /**
     * konstruktor bezparametrowy odpowiedzialny za wywolanie wszystkich niezbednych funkcji
     */
    public Game() {
        window = new JFrame();

        player = new Snake();

        playerAI = new SnakeAI();

        food = new Food(player, playerAI);

        frog = new Frog(player);

        obstacle = new Obstacle(player);

        graphics = new Graphics(this);

        window.add(graphics);

        window.setTitle("Snake");
        window.setSize(width * dimension + 2, height * dimension + dimension + 4);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public Game(JFrame window) {

        player = new Snake();

        food = new Food(player, playerAI);

        frog = new Frog(player);

        obstacle = new Obstacle(player);

        graphics = new Graphics(this);

        //window.add(graphics);

        //window.setTitle("Snake");
        //window.setSize(width * dimension + 2, height * dimension + dimension + 4);
        //window.setVisible(true);
        //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * funkcja odpowiadajaca za wystartowanie rozgrywki
     */
    public void start() {
        graphics.state = "RUNNING";
    }

    /**
     * funkcja odpowiadajaca za restart gry
     */
    public void restart() {
        graphics.state = "START";
    }

    /**
     * Funkcja odpowiadajaca za aktualizowanie wydarzen w stanie gry
     * Sprawdza wszelkiego rodzaju kolizje konczace koleizje
     * jak i zjedzenie jablka, w przypadku gdy nie wyktryje kolizji
     * kolejny ruch jest wykonywany
     */
    public void update() {
        if(graphics.state == "RUNNING") {

            if(check_food_collision()) {
                player.grow();
                food.random_spawn(player,playerAI);
            }
            if(check_foodAI_collision()){
                food.random_spawn(player,playerAI);
                playerAI.grow();
                /*
                if(food.getY()>playerAI.getY()){
                    playerAI.move(food.getX()*dimension,food.getY()*dimension);
                }
                else{
                    playerAI.move(food.getX()*dimension,food.getY()*dimension);
                }
            }else{
                playerAI.move(food.getX()*dimension,food.getY()*dimension);*/
            }

            if(check_frog_collision()) {
                int temp = frog.getX() + 1;

                //player.grow();
                frog.random_spawn(player);
            }
            else if(check_wall_collision() || check_self_collision() || check_selfAI_collision() || check_obstacle_collision()) {
                graphics.state = "END";
                String a = player.getBody().size() - 3 + "";
                try(FileWriter file = new FileWriter("results.txt", true)){
                BufferedWriter out = new BufferedWriter(file);
                out.write(a + " ");
                out.close();} catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                player.move();
                playerAI.move(food.getX()*dimension,food.getY()*dimension);
            }
        }
    }


    /**
     * funkcja sprawdzajaca kolizje gracza ze scianami bocznymi
     * zwraca wartosc true lub false
     * @return
     */
    private boolean check_wall_collision() {
        if(player.getX() < 0 || player.getX() >= width * dimension
                || player.getY() < 0|| player.getY() >= height * dimension) {
            return true;
        }
        return false;
    }
    /**
     * funkcja sprawdzajaca kolizje gracza z jabłkami
     * zwraca wartosc true lub false
     * @return
     */
    private boolean check_food_collision() {
        if(player.getX() == food.getX() * dimension && player.getY() == food.getY() * dimension) {
            return true;
        }
        return false;
    }
    /**
     * funkcja sprawdzajaca kolizje AI z jablkami
     * zwraca wartosc true lub false
     * @return
     */
    private boolean check_foodAI_collision() {
        if(playerAI.getX() == food.getX() * dimension && playerAI.getY() == food.getY() * dimension) {
            return true;
        }
        return false;
    }
    /**
     * funkcja odpowiedzialna za ruch zaby, w przypadku gdy waz znajduje sie w poblizu
     * zaba jest wystraszana i wykonuje randomowy skok
     * zwraca wartosc true lub false
     * @return
     */
    private boolean check_frog_collision() {
        if(player.getX() == (frog.getX() * dimension) - 20 && player.getY() == frog.getY() * dimension ||
                player.getX() == (frog.getX() * dimension) + 20 && player.getY() == frog.getY() * dimension ||
                player.getX() == frog.getX() * dimension && player.getY() == (frog.getY() * dimension) - 20 ||
                player.getX() == frog.getX() * dimension && player.getY() == (frog.getY() * dimension) + 20) {
            return true;
        }
        return false;
    }
    /**
     * funkcja sprawdzajaca kolizje gracza z przeszkodami
     * zwraca wartosc true lub false
     * @return
     */
    private boolean check_obstacle_collision() {
        for (int i = 0; i<8; i++) {
            if (player.getX() == obstacle.obstacleX[i] * dimension && player.getY() == obstacle.obstacleY[i] * dimension) {
                return true;
            }
        }
        return false;

    }
    /**
     * funkcja sprawdzajaca kolizje weza z samym soba
     * zwraca wartosc true lub false
     * @return
     */
    private boolean check_self_collision() {
        for(int i = 1; i < player.getBody().size(); i++) {
            if(player.getX() == player.getBody().get(i).x &&
                    player.getY() == player.getBody().get(i).y) {
                return true;
            }
        }
        return false;
    }
    /**
     * funkcja sprawdzajaca kolizje AI ze samym soba
     * zwraca wartosc true lub false
     * @return
     */
    private boolean check_selfAI_collision() {
        for(int i = 1; i < playerAI.getBody().size(); i++) {
            if(playerAI.getX() == playerAI.getBody().get(i).x &&
                    playerAI.getY() == playerAI.getBody().get(i).y) {
                return true;
            }
        }
        return false;
    }



    @Override
    public void keyTyped(KeyEvent e) {	}

    /**
     * nadpisana funkcja obslugi gracza, czytajac dane z klawiatury
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(graphics.state == "RUNNING") {
            if(keyCode == KeyEvent.VK_W && player.getMove() != "DOWN") {
                player.up();
            }

            if(keyCode == KeyEvent.VK_S && player.getMove() != "UP") {
                player.down();
            }

            if(keyCode == KeyEvent.VK_A && player.getMove() != "RIGHT") {
                player.left();
            }

            if(keyCode == KeyEvent.VK_D && player.getMove() != "LEFT") {
                player.right();
            }
        }
        else {
            this.start();
        }
        //if(graphics.state == "END") {
            if(keyCode == KeyEvent.VK_R) {
                this.restart();
                new Game();
            }
        //}
    }

    @Override
    public void keyReleased(KeyEvent e) {	}
    // funkcje pomocnicze
    public Snake getPlayer() {
        return player;
    }

    public void setPlayer(Snake player) {
        this.player = player;
    }

    public SnakeAI getPlayerAI() {
        return playerAI;
    }

    public void setPlayerAI(SnakeAI playerAI) {
        this.playerAI = playerAI;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Frog getFrog() {
        return frog;
    }

    public void setFrog(Frog frog) {
        this.frog = frog;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

}
