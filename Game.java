import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
    private Timer timer;
    private double p1Health;
    private double p2Health;
    private int damage;
    private long start;
    private int wait;
    private boolean won;
    private boolean playing;

    public Game (){
        won = false;
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
        intro ();
    }

    public void intro (){
        removeAll ();
        setBackground (new Color (50, 200, 100));
        JLabel intro = new JLabel ("Java Royale");
        intro.setFont (new Font ("Verdana", Font.BOLD, 70));
        intro.setBounds (450, 175, 700, 100);
        intro.setForeground (Color.RED);
        setLayout (null);
        add (intro);

        JButton start = new JButton ("PLAY");
        start.setForeground (Color.BLUE);
        start.setBounds (600, 400, 140, 30);
        start.setBackground (Color.GRAY);
        start.setFont (new Font ("Verdana", Font.BOLD, 16));
        start.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                play ();
            }
        });
        add (start);

        JButton controls = new JButton ("CONTROLS");
        controls.setForeground (Color.BLUE);
        controls.setBounds (600, 460, 140, 30);
        controls.setBackground (Color.GRAY);
        controls.setFont (new Font ("Verdana", Font.BOLD, 16));
        controls.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                showControls ();
            }
        });
        add (controls);
    }

    public void showControls (){
        removeAll ();
        setBackground (Color.GRAY);
        JLabel ctrl = new JLabel ("CONTROLS");
        add (ctrl);

        JLabel p1 = new JLabel ("Player 1:");
        p1.setForeground (Color.BLUE);
        p1.setBounds (200, 50, 150, 50);
        p1.setFont (new Font ("Verdana", Font.BOLD, 30));
        add (p1);

        JLabel p2 = new JLabel ("Player 2:");
        p2.setForeground (Color.RED);
        p2.setBounds (850, 50, 150, 50);
        p2.setFont (new Font ("Verdana", Font.BOLD, 30));
        add (p2);

        JLabel[] controls1 = new JLabel[5];
        int y = 1;
        for (int i = 0; i < 5; i++){
            controls1[i] = new JLabel ();
            controls1[i].setForeground (Color.BLUE);
            controls1[i].setBounds (200, 30 + 50 * y, 200, 100);
            controls1[i].setFont (new Font ("Verdana", Font.BOLD, 20));
            add (controls1[i]);
            y++;
        }
        controls1[0].setText ("UP - W");
        controls1[1].setText ("DOWN - S");
        controls1[2].setText ("LEFT - A");
        controls1[3].setText ("RIGHT - D");
        controls1[4].setText ("SHOOT - X");

        JLabel[] controls2 = new JLabel[5];
        y = 1;
        for (int i = 0; i < 5; i++){
            controls2[i] = new JLabel ();
            controls2[i].setForeground (Color.RED);
            controls2[i].setBounds (850, 25 + 50 * y, 300, 100);
            controls2[i].setFont (new Font ("Verdana", Font.BOLD, 20));
            add (controls2[i]);
            y++;
        }
        controls2[0].setText ("UP - UP ARROW");
        controls2[1].setText ("DOWN - DOWN ARROW");
        controls2[2].setText ("LEFT - LEFT ARROW");
        controls2[3].setText ("RIGHT - RIGHT ARROW");
        controls2[4].setText ("SHOOT - ENTER");

        JButton back = new JButton ("BACK");
        back.setForeground (Color.BLACK);
        back.setBackground (Color.WHITE);
        back.setBounds (20, 20, 90, 25);
        back.setFont (new Font ("Verdana", Font.BOLD, 15));
        back.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                intro ();
            }
        });
        add (back);
    }

    public void play (){
        removeAll ();
        playing = true;
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
                    if (p2Health <= 0) win (0);
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
        if (!(won) && playing) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x1, y1, 50, 50);
            g2d.setColor(Color.RED);
            g2d.fillRect(x2, y2, 50, 50);
            g2d.setColor(Color.WHITE);
            g2d.fillRect(x1, y1 - 9, 54, 9);
            g2d.fillRect(x2, y2 - 9, 54, 9);
            g2d.setColor(Color.RED);
            g2d.fillRect(x1 + 2, y1 - 7, (int) (p1Health / 2), 5);
            g2d.fillRect(x2 + 2, y2 - 7, (int) (p2Health / 2), 5);
            for (Pellet pellet : pellets) {
                if (pellet.getTarget() == 2) g2d.setColor(Color.BLUE);
                else g2d.setColor(Color.RED);
                g2d.fillRect((int) pellet.getX(), (int) pellet.getY(), 5, 5);
            }
            g2d.dispose();
        }
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
    public void keyTyped (KeyEvent e){}

    public void win (int winner) {
        timer.stop();
        won = true;
        String cong = "";
        Color color = Color.BLACK;
        switch (winner){
            case 0:
                cong = "DRAW";
                break;
            case 1:
                cong = "BLUE WINS!";
                color = Color.BLUE;
                break;
            case 2:
                cong = "RED WINS!";
                color = Color.RED;
                break;
        }
        JLabel winnerLabel = new JLabel (cong);
        winnerLabel.setFont (new Font ("Arial", Font.BOLD, 50));
        winnerLabel.setForeground (color);
        setLayout (null);
        winnerLabel.setBounds (535, 325, 300, 50);
        add (winnerLabel);
        revalidate ();
        repaint ();
    }    
}