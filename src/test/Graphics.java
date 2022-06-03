package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * klasa odpowiedzialna za graficzny obraz gry
 */
public class Graphics
        extends JPanel
        implements ActionListener{
    private Timer t = new Timer(50, this);
    public String state;



    private Snake s;
    private SnakeAI sAI;
    private Food f;
    private Frog frog;
    private Game game;
    private Obstacle o;
    Random random;

    /**
     * konstruktor z parametrem silnika gry
     * @param g
     */
    public Graphics(Game g) {
        t.start();
        state = "START";
        random = new Random();
        game = g;
        s = g.getPlayer();
        sAI = g.getPlayerAI();
        f = g.getFood();
        o = g.getObstacle();
        frog = g.getFrog();
        //add a keyListner
        this.addKeyListener(g);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    /**
     * funkcja odpowiedzialna za aspekt graficzny gry
     * @param g
     */
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, Game.width * Game.dimension + 5, Game.height * Game.dimension + 5);

        if(state == "START") {
            g2d.setColor(Color.white);
            g2d.drawString("Press Any Key", Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension - 20);
        }
        else if(state == "RUNNING") {
            g2d.setColor(Color.red);
            g2d.fillOval(f.getX() * Game.dimension, f.getY() * Game.dimension, Game.dimension, Game.dimension);
            g2d.setColor(Color.green);
            g2d.fillRect(frog.getX() * Game.dimension, frog.getY() * Game.dimension, Game.dimension, Game.dimension);
            g2d.setColor(Color.blue);
            for(int i = 0;i<8;i++) {
                g2d.fillRect(o.obstacleX[i] * Game.dimension, o.obstacleY[i] * Game.dimension, Game.dimension, Game.dimension);
            }
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            for(Rectangle r : s.getBody()) {
                g2d.fill(r);
            }
            g2d.setColor(Color.CYAN);
            for(Rectangle r : sAI.getBody()) {
                g2d.fill(r);
            }
        }
        else {
            g2d.setColor(Color.white);
            g2d.drawString("AI Score: " + (sAI.getBody().size() - 3), Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension - 40);
            g2d.drawString("Your Score: " + (s.getBody().size() - 3), Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension - 20);
            g2d.drawString("Click 'r' to restart", Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension);
            String kurde = "";
            try{
                BufferedReader reader = new BufferedReader(new FileReader("results.txt"));
                String line;
                while((line = reader.readLine()) != null){
                    kurde += line;
                }
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            List<String> myList = new ArrayList<String>(Arrays.asList(kurde.split(" ")));
            List<Integer> newList = myList.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
            Collections.sort(newList);
            String topResult1 = "Top 1: " + newList.get(newList.size()-1);
            String topResult2 = "Top 2: " + newList.get(newList.size()-2);
            String topResult3 = "Top 3: " + newList.get(newList.size()-3);
            g2d.drawString(topResult1, Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension+20);
            g2d.drawString(topResult2, Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension+40);
            g2d.drawString(topResult3, Game.width/2 * Game.dimension - 40, Game.height / 2 * Game.dimension+60);
            //saveResult();
            //new Game();
            //state = "START";
        }
    }


    /**
     * napisanie funkcji polegajace na odswiezaniu sytuacji graficznej gry
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        game.update();
    }

}