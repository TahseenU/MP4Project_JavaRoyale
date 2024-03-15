import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JPanel implements KeyListener {
    private int speed;
    private int pSpeed;

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int cooldown;
    private long cd1;
    private long cd2;
    private boolean[] keysPressed;
    private ArrayList<Pellet> pellets = new ArrayList<>();
    private double p1Health;
    private double p2Health;
    private int damage;

    public Game() {
        p1Health = 100;
        p2Health = 100;
        damage = 10;
        speed = 5;
        pSpeed = 10;
        cooldown = 200;
        x1 = 100;
        y1 = 100;
        x2 = 1200;
        y2 = 550;
        keysPressed = new boolean[KeyEvent.KEY_LAST];
        setPreferredSize(new Dimension(1350, 700));
        setBackground(new Color(50, 200, 100));
        setFocusable(true);
        addKeyListener(this);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1Health += 0.005;
                p2Health += 0.005;
                movePlayers();     
                movePellets();
                repaint();
            }
        });
        timer.start();
    }

    private void movePlayers() {
        if (keysPressed[KeyEvent.VK_W]) if (!(y1 <= 0)) y1 -= speed;
        if (keysPressed[KeyEvent.VK_A]) if (!(x1 <= 0))x1 -= speed;
        if (keysPressed[KeyEvent.VK_S]) if (!(y1 >= 650)) y1 += speed;
        if (keysPressed[KeyEvent.VK_D]) if (!(x1 >= 1300)) x1 += speed;

        if (keysPressed[KeyEvent.VK_UP]) if (!(y2 <= 0)) y2 -= speed;
        if (keysPressed[KeyEvent.VK_LEFT]) if (!(x2 <= 0)) x2 -= speed;
        if (keysPressed[KeyEvent.VK_DOWN]) if (!(y2 >= 650)) y2 += speed;
        if (keysPressed[KeyEvent.VK_RIGHT]) if (!(x2 >= 1300)) x2 += speed;
    }

    private void movePellets() {
        int xTar;
        int yTar;
        for (int i = 0; i < pellets.size (); i++) {
            Pellet pellet = pellets.get (i);
            if (pellet.getTarget () == 2){
                xTar = x2;
                yTar = y2;
            }
            else{
                xTar = x1;
                yTar = y1;
            }
            pellet.move();

            int[] xTargets = new int [53];
            int[] yTargets = new int [53];
            for (int j = 0; j < xTargets.length; j++) xTargets[j] = j + xTar - 2;
            for (int j = 0; j < yTargets.length; j++) yTargets[j] = j + yTar - 2;

            if (pellet.getX() < 0 || pellet.getX() >= getWidth() || pellet.getY() < 0 || pellet.getY() >= getHeight()) {
                pellets.remove(i);
                i--;
            }
            else{
                for (int j = 0; j < xTargets.length; j++){
                    for (int k = 0; k < yTargets.length; k++){
                        if (xTargets[j] == pellet.getX () && yTargets[k] == pellet.getY ()){
                            pellets.remove (i);
                            if (pellet.getTarget () == 2) p2Health -= damage;
                            else p1Health -= damage;
                            if (p1Health <= 0 || p2Health <= 0) setVisible (false);
                            i--;
                        }
                    }
                }
            }
            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x1, y1, 50, 50);
        g2d.setColor(Color.RED);
        g2d.fillRect(x2, y2, 50, 50);
        for (Pellet pellet : pellets) {
            if (pellet.getTarget () == 2) g2d.setColor (Color.BLUE);
            else g2d.setColor (Color.RED);
            g2d.fillRect((int) pellet.getX(), (int) pellet.getY(), 5, 5);
        }
        g2d.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed[e.getKeyCode()] = true;
        long time = System.currentTimeMillis ();
        if (e.getKeyCode() == KeyEvent.VK_X) {
            if (time - cd1 >= cooldown){
                cd1 = time;
                pellets.add (new Pellet(x1, y1, x2, y2, 5, pSpeed, 2));
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (time - cd2 >= cooldown){
                cd2 = time;
                pellets.add (new Pellet(x2, y2, x1, y1, 5, pSpeed, 1));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}