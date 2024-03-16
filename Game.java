import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
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
    public static Timer timer;
    private double p1Health;
    private double p2Health;
    private int damage;
    private long start;
    private int wait;

    public Game() {
        start = System.currentTimeMillis ();
        wait = 0;
        p1Health = 100;
        p2Health = 100;
        damage = 10;
        speed = 5;
        pSpeed = 11;
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

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (p1Health < 100) p1Health += 0.01;
                if (p2Health < 100) p2Health += 0.01;
                movePlayers();     
                movePellets();
                repaint();
                int fps = (int) (1000 / (System.currentTimeMillis () - start));
                if (wait % 100 == 0) System.out.println(fps + " FPS");
                start = System.currentTimeMillis();
                wait++;
                if (p1Health <= 0){
                    if (p2Health <= 0) win (2);
                    else win (2);
                }
                else if (p2Health <= 0) win (1);
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

            int[] xTargets = new int [55];
            int[] yTargets = new int [55];
            for (int j = 0; j < xTargets.length; j++) xTargets[j] = j + xTar - 4;
            for (int j = 0; j < yTargets.length; j++) yTargets[j] = j + yTar - 4;

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
        g2d.setColor(Color.WHITE);
        g2d.fillRect (x1, y1 - 9, 54, 9);
        g2d.fillRect (x2, y2 - 9, 54, 9);
        g2d.setColor (Color.RED);
        g2d.fillRect (x1 + 2, y1 - 7, (int) (p1Health / 2), 5);
        g2d.fillRect (x2 + 2, y2 - 7, (int) (p2Health / 2), 5);
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
    public void keyReleased(KeyEvent e){
        keysPressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e){}

    public void win (int winner){
        //timer.stop ();
        String cong = "";
        switch (winner){
            case 0: cong = "DRAW";
            case 1: cong = "BLUE WINS!";
            case 2: cong = "RED WINS!";
        }
        add (new JLabel (cong));
        setBackground (new Color (50, 200, 100));
    }
}