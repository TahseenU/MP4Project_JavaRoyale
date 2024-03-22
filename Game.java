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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;

public class Game extends JPanel implements KeyListener{
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
    private ArrayList<Pellet> pellets;
    private ArrayList<HealthBox> boxes;
    private ArrayList<Buff> buffs;
    private Timer timer;
    private Timer fpsTimer;
    private double p1Health;
    private double p2Health;
    private int p1Attack;
    private int p2Attack;
    private int frameCount;
    private boolean won;
    private boolean playing;
    private int p1Fired;
    private int p2Fired;
    private int p1Hit;
    private int p2Hit;
    private int p1Wins;
    private int p2Wins;

    public Game (){
        pellets = new ArrayList<> ();
        boxes = new ArrayList<> ();
        buffs = new ArrayList<> ();
        won = false;
        p1Health = 150;
        p2Health = 150;
        p1Attack = 10;
        p2Attack = 10;
        speed = 5;
        pSpeed = 11;
        cooldown = 200;
        x1 = 100;
        y1 = 100;
        x2 = 1200;
        y2 = 550;
        keysPressed = new boolean[1000];
        setPreferredSize (new Dimension (1350, 700));
        setBackground (new Color (50, 200, 100));
        setFocusable (true);
        addKeyListener (this);
        intro ();
    }

    private void intro (){
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
        start.setBounds (620, 350, 100, 30);
        start.setBackground (Color.GRAY);
        start.setFont (new Font ("Verdana", Font.BOLD, 16));
        start.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                difficulty ();
            }
        });
        add (start);

        JButton controls = new JButton ("HELP");
        controls.setForeground (Color.BLUE);
        controls.setBounds (620, 410, 100, 30);
        controls.setBackground (Color.GRAY);
        controls.setFont (new Font ("Verdana", Font.BOLD, 16));
        controls.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                showControls ();
            }
        });
        add (controls);

        repaint ();
    }

    private void showControls (){
        removeAll ();
        setBackground (Color.BLACK);
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
        controls1[4].setText ("SHOOT - R");

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

        JLabel info = new JLabel ("Info:");
        info.setForeground (Color.WHITE);
        info.setBounds (200, 370, 100, 100);
        info.setFont (new Font ("Verdana", Font.BOLD, 30));
        add (info);

        JLabel[] infos = new JLabel[5];
        y = 0;
        for (int i = 0; i < 5; i++){
            infos[i] = new JLabel ();
            infos[i].setForeground (Color.WHITE);
            infos[i].setBounds (200, 460 + 35 * y, 520, 20);
            infos[i].setFont (new Font ("Verdana", Font.BOLD, 16));
            add (infos[i]);
            y++;
        }
        infos[0].setText ("Green Health Boxes - Heal 40 HP (Max: 150)");
        infos[1].setText ("Purple Attack Buff Boxes - Increase Attack by 1 (Base: 10)");
        infos[2].setText ("Critical Hit - 5% Chance; Deal x3 damage");
        infos[3].setText ("Super Crit - 0.5% Chance; Deal x10 damage");
        infos[4].setText ("Passive: Heal ~1.8 HP/s (Max: 150)");

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

    private void difficulty (){
        removeAll ();
        setBackground (new Color (50, 200, 100));

        JLabel difficulty = new JLabel ("SELECT SPEED DIFFICULTY");
        difficulty.setForeground (new Color (150, 0, 255));
        difficulty.setFont (new Font ("Verdana", Font.BOLD, 40));
        difficulty.setBounds (375, 75, 700, 50);
        add (difficulty);

        JButton casual = new JButton ("Casual");
        casual.setBackground (Color.GRAY);
        casual.setForeground (Color.BLACK);
        casual.setFont (new Font ("Verdana", Font.BOLD, 20));
        casual.setBounds (200, 250, 200, 30);
        casual.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                pSpeed = 8;
                play ();
            }
        });
        add (casual);

        JButton competitive = new JButton ("Competitive");
        competitive.setBackground (Color.GRAY);
        competitive.setForeground (Color.BLACK);
        competitive.setFont (new Font ("Verdana", Font.BOLD, 20));
        competitive.setBounds (570, 250, 200, 30);
        competitive.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                speed = 6;
                pSpeed = 11;
                play ();
            }
        });
        add (competitive);

        JButton pro = new JButton ("Pro");
        pro.setBackground (Color.GRAY);
        pro.setForeground (Color.BLACK);
        pro.setFont (new Font ("Verdana", Font.BOLD, 20));
        pro.setBounds (940, 250, 200, 30);
        pro.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                speed = 8;
                pSpeed = 15;
                play ();
            }
        });
        add (pro);

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

        repaint ();
    }

    private void play (){
        removeAll ();
        playing = true;
        frameCount = 0;
        JLabel showFPS = new JLabel ();
        showFPS.setForeground (Color.WHITE);
        showFPS.setFont (new Font ("Verdana", Font.BOLD, 10));
        showFPS.setBounds (5, 5, 50, 10);
        add (showFPS);
    
        fpsTimer = new Timer (1000, new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e){
                showFPS.setText (frameCount + " FPS");
                frameCount = 0;
            }
        });
        fpsTimer.start ();
    
        timer = new Timer (10, new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                if (p1Health < 150) p1Health += 0.03;
                if (p2Health < 150) p2Health += 0.03;
                movePlayers ();     
                movePellets ();
                repaint ();
                frameCount++;
                if (p1Health <= 0){
                    if (p2Health <= 0) win (0);
                    else win (2);
                }
                else if (p2Health <= 0) win (1);
                if (Math.random () <= 0.001){
                    boxes.add (new HealthBox ());
                }
                if (Math.random () <= 0.0005){
                    buffs.add (new Buff ());
                }
            }
        });
        timer.start ();
    }    

    private void movePlayers (){
        if (keysPressed[KeyEvent.VK_W]) if (!(y1 <= 0)) y1 -= speed;
        if (keysPressed[KeyEvent.VK_A]) if (!(x1 <= 0)) x1 -= speed;
        if (keysPressed[KeyEvent.VK_S]) if (!(y1 >= 650)) y1 += speed;
        if (keysPressed[KeyEvent.VK_D]) if (!(x1 >= 1300)) x1 += speed;

        if (keysPressed[KeyEvent.VK_UP]) if (!(y2 <= 0)) y2 -= speed;
        if (keysPressed[KeyEvent.VK_LEFT]) if (!(x2 <= 0)) x2 -= speed;
        if (keysPressed[KeyEvent.VK_DOWN]) if (!(y2 >= 650)) y2 += speed;
        if (keysPressed[KeyEvent.VK_RIGHT]) if (!(x2 >= 1300)) x2 += speed;

        if (boxes.size() > 0){
            for (int i = boxes.size()-1; i >= 0; i--){
                for (int j = x1 - 30; j < x1 + 50; j++){
                    for (int k = y1 - 30; k < y1 + 50; k++){
                        if (boxes.size() == 0) break;
                        if (i == boxes.size()) break;
                        if (j == boxes.get(i).getX() && k == boxes.get(i).getY()){
                            p1Health += 40;
                            JLabel health = new JLabel ("+40 Health");
                            health.setFont (new Font ("Verdana", Font.BOLD, 13));
                            health.setBounds (x1, y1 - 40, 100, 20);
                            health.setForeground (Color.ORANGE);
                            Timer hTimer = new Timer (10, new ActionListener (){
                                int appear = 0;
                                @Override
                                public void actionPerformed (ActionEvent e){
                                    appear++;
                                    if (appear == 50){
                                        remove (health);
                                        ((Timer) e.getSource ()).stop ();
                                    }
                                }
                            });
                            hTimer.start ();
                            add (health);

                            if (p1Health > 150) p1Health = 150;
                            boxes.remove (i);
                        }
                    }
                }
            }

            for (int i = boxes.size()-1; i >= 0; i--){
                for (int j = x2 - 30; j < x2 + 50; j++){
                    for (int k = y2 - 30; k < y2 + 50; k++){
                        if (boxes.size() == 0) break;
                        if (i == boxes.size()) break;
                        if (j == boxes.get(i).getX() && k == boxes.get(i).getY()){
                            p2Health += 40;
                            JLabel health = new JLabel ("+40 Health");
                            health.setFont (new Font ("Verdana", Font.BOLD, 13));
                            health.setBounds (x2, y2 - 40, 100, 20);
                            health.setForeground (Color.ORANGE);
                            Timer hTimer = new Timer (10, new ActionListener (){
                                int appear = 0;
                                @Override
                                public void actionPerformed (ActionEvent e){
                                    appear++;
                                    if (appear == 50){
                                        remove (health);
                                        ((Timer) e.getSource ()).stop ();
                                    }
                                }
                            });
                            hTimer.start ();
                            add (health);

                            if (p2Health > 150) p2Health = 150;
                            boxes.remove (i);
                        }
                    }
                }
            }
        }

        if (buffs.size() > 0){
            for (int i = buffs.size()-1; i >= 0; i--){
                for (int j = x1 - 25; j < x1 + 50; j++){
                    for (int k = y1 - 25; k < y1 + 50; k++){
                        if (buffs.size() == 0) break;
                        if (i == buffs.size()) break;
                        if (j == buffs.get(i).getX() && k == buffs.get(i).getY()){
                            p1Attack++;
                            buffs.remove (i);
                            System.out.println("Player 1 Attack: " + p1Attack);
                            JLabel dmg = new JLabel ("+1 Attack");
                            dmg.setFont (new Font ("Verdana", Font.BOLD, 13));
                            dmg.setBounds (x1, y1 - 40, 100, 20);
                            dmg.setForeground (Color.ORANGE);
                            Timer dTimer = new Timer (10, new ActionListener (){
                                int appear = 0;
                                @Override
                                public void actionPerformed (ActionEvent e){
                                    appear++;
                                    if (appear == 50){
                                        remove (dmg);
                                        ((Timer) e.getSource ()).stop ();
                                    }
                                }
                            });
                            dTimer.start ();
                            add (dmg);
                        }
                    }
                }
            }

            for (int i = buffs.size()-1; i >= 0; i--){
                for (int j = x2 - 25; j < x2 + 50; j++){
                    for (int k = y2 - 25; k < y2 + 50; k++){
                        if (buffs.size() == 0) break;
                        if (i == buffs.size()) break;
                        if (j == buffs.get(i).getX() && k == buffs.get(i).getY()){
                            p2Attack++;
                            buffs.remove (i);
                            System.out.println("Player 2 Attack: " + p2Attack);
                            JLabel dmg = new JLabel ("+1 Attack");
                            dmg.setFont (new Font ("Verdana", Font.BOLD, 13));
                            dmg.setBounds (x2, y2 - 40, 100, 20);
                            dmg.setForeground (Color.ORANGE);
                            Timer dTimer = new Timer (10, new ActionListener (){
                                int appear = 0;
                                @Override
                                public void actionPerformed (ActionEvent e){
                                    appear++;
                                    if (appear == 50){
                                        remove (dmg);
                                        ((Timer) e.getSource ()).stop ();
                                    }
                                }
                            });
                            dTimer.start ();
                            add (dmg);
                        }
                    }
                }
            }
        }
    }

    private void movePellets(){
        int xTar;
        int yTar;
        for (int i = 0; i < pellets.size (); i++){
            Pellet pellet = pellets.get (i);
            if (pellet.getTarget () == 2){
                xTar = x2;
                yTar = y2;
            }
            else{
                xTar = x1;
                yTar = y1;
            }
            pellet.move ();

            int[] xTargets = new int [57];
            int[] yTargets = new int [58];
            for (int j = 0; j < xTargets.length; j++) xTargets[j] = j + xTar - 6;
            for (int j = 0; j < yTargets.length; j++) yTargets[j] = j + yTar - 7;

            if (pellet.getX () < 0 || pellet.getX () >= getWidth () || pellet.getY () < 0 || pellet.getY () >= getHeight ()){
                pellets.remove (i);
                i--;
            }
            else{
                for (int j = 0; j < xTargets.length; j++){
                    for (int k = 0; k < yTargets.length; k++){
                        if (xTargets[j] == pellet.getX () && yTargets[k] == pellet.getY ()){
                            pellets.remove (i);
                            if (pellet.getTarget () == 2){
                                if (Math.random () <= 0.05){
                                    if (Math.random() <= 0.1){
                                        p2Health -= (10 * p1Attack);
                                        p2Hit++;
                                        JLabel crit = new JLabel ("SUPER CRIT");
                                        crit.setFont (new Font ("Verdana", Font.BOLD, 16));
                                        crit.setBounds (x2 - 20, y2 - 40, 175, 30);
                                        crit.setForeground (new Color (150, 0, 255));
                                        Timer cTimer = new Timer (10, new ActionListener (){
                                            int appear = 0;
                                            @Override
                                            public void actionPerformed (ActionEvent e){
                                                appear++;
                                                if (appear == 50){
                                                    remove (crit);
                                                    ((Timer) e.getSource ()).stop ();
                                                }
                                            }
                                        });
                                        cTimer.start ();
                                        add (crit);
                                    }
                                    else{
                                        p2Health -= (2.5 * p1Attack);
                                        p2Hit++;
                                        JLabel crit = new JLabel ("CRITICAL HIT");
                                        crit.setFont (new Font ("Verdana", Font.BOLD, 13));
                                        crit.setBounds (x2, y2 - 40, 100, 20);
                                        crit.setForeground (Color.ORANGE);
                                        Timer cTimer = new Timer (10, new ActionListener (){
                                            int appear = 0;
                                            @Override
                                            public void actionPerformed (ActionEvent e){
                                                appear++;
                                                if (appear == 50){
                                                    remove (crit);
                                                    ((Timer) e.getSource ()).stop ();
                                                }
                                            }
                                        });
                                        cTimer.start ();
                                        add (crit);
                                    }
                                }
                                else{
                                    p2Health -= p1Attack;
                                    p2Hit++;
                                }
                            }
                            else{
                                if (Math.random () <= 0.05){
                                    if (Math.random() <= 0.1){
                                        p1Health -= (10 * p2Attack);
                                        p1Hit++;
                                        JLabel crit = new JLabel ("SUPER CRIT");
                                        crit.setFont (new Font ("Verdana", Font.BOLD, 16));
                                        crit.setBounds (x1 - 20, y1 - 40, 175, 30);
                                        crit.setForeground (new Color (150, 0, 255));
                                        Timer cTimer = new Timer (10, new ActionListener (){
                                            int appear = 0;
                                            @Override
                                            public void actionPerformed (ActionEvent e){
                                                appear++;
                                                if (appear == 50){
                                                    remove (crit);
                                                    ((Timer) e.getSource ()).stop ();
                                                }
                                            }
                                        });
                                        cTimer.start ();
                                        add (crit);
                                    }
                                    else{
                                        p1Health -= (2.5 * p2Attack);
                                        p1Hit++;
                                        JLabel crit = new JLabel ("CRITICAL HIT");
                                        crit.setFont (new Font ("Verdana", Font.BOLD, 13));
                                        crit.setBounds (x1, y1 - 40, 100, 20);
                                        crit.setForeground (Color.ORANGE);
                                        Timer cTimer = new Timer (10, new ActionListener (){
                                            int appear = 0;
                                            @Override
                                            public void actionPerformed (ActionEvent e){
                                                appear++;
                                                if (appear == 50){
                                                    remove (crit);
                                                    ((Timer) e.getSource ()).stop ();
                                                }
                                            }
                                        });
                                        cTimer.start ();
                                        add (crit);
                                    }
                                }
                                else{
                                    p1Health -= p2Attack;
                                    p1Hit++;
                                }
                            }
                            i--;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent (g);
        if (!(won) && playing){
            Graphics2D g2d = (Graphics2D) g.create ();
            g2d.setColor (Color.BLUE);
            g2d.fillRect (x1, y1, 50, 50);
            g2d.setColor (Color.RED);
            g2d.fillRect (x2, y2, 50, 50);
            g2d.setColor (Color.WHITE);
            g2d.fillRect (x1 - 1, y1 - 11, 59, 11);
            g2d.fillRect (x2 - 1, y2 - 11, 59, 11);
            g2d.setColor (new Color (255, 0, 255));
            g2d.fillRect (x1 + 1, y1 - 9, (int) (p1Health / 3 * 1.1), 7);
            g2d.fillRect (x2 + 1, y2 - 9, (int) (p2Health / 3 * 1.1), 7);
            for (Pellet pellet : pellets){
                if (pellet.getTarget () == 2) g2d.setColor (Color.BLUE);
                else g2d.setColor (Color.RED);
                g2d.fillRect ((int) pellet.getX (), (int) pellet.getY (), 6, 6);
            }
            for (HealthBox box : boxes){
                g2d.setColor (Color.GREEN);
                g2d.fillRect (box.getX (), box.getY (), 30, 30);
                g2d.setColor (Color.WHITE);
                g2d.fillRect (box.getX () + 5, box.getY () + 12, 20, 6);
                g2d.fillRect (box.getX () + 12, box.getY () + 5, 6, 20);
            }
            for (Buff buff : buffs){
                g2d.setColor (new Color (200, 0, 255));
                g2d.fillRect (buff.getX(), buff.getY(), 25, 25);
            }
            g2d.dispose ();
        }
    }

    @Override
    public void keyPressed (KeyEvent e){
        keysPressed[e.getKeyCode ()] = true;
        long time = System.currentTimeMillis ();
        if (e.getKeyCode () == KeyEvent.VK_R){
            if (time - cd1 >= cooldown){
                p1Fired++;
                cd1 = time;
                pellets.add (new Pellet(x1, y1, x2, y2, 6, pSpeed, 2));
            }
        }
        if (e.getKeyCode () == KeyEvent.VK_ENTER){
            if (time - cd2 >= cooldown){
                p2Fired++;
                cd2 = time;
                pellets.add (new Pellet (x2, y2, x1, y1, 6, pSpeed, 1));
            }
        }
        if (won && e.getKeyCode () == KeyEvent.VK_SPACE){
            won = false;
            playing = false;
            p1Health = 150;
            p2Health = 150;
            p1Attack = 10;
            p2Attack = 10;
            x1 = 100;
            y1 = 100;
            x2 = 1200;
            y2 = 550;
            p1Fired = 0;
            p1Hit = 0;
            p2Fired = 0;
            p2Hit = 0;
            pellets = new ArrayList<> ();
            boxes = new ArrayList<> ();
            buffs = new ArrayList<> ();
            intro ();
            repaint ();
        }
    }

    @Override
    public void keyReleased (KeyEvent e){
        keysPressed[e.getKeyCode ()] = false;
    }

    @Override
    public void keyTyped (KeyEvent e){}

    private void win (int winner){
        timer.stop ();
        fpsTimer.stop ();
        removeAll ();
        won = true;
        String cong = "";
        int x = 0;
        Color color = Color.BLACK;
        switch (winner){
            case 0:
                cong = "DRAW";
                x = 80;
                break;
            case 1:
                cong = "BLUE WINS!";
                p1Wins++;
                color = Color.BLUE;
                break;
            case 2:
                cong = "RED WINS!";
                p2Wins++;
                color = Color.RED;
                break;
        }
        JLabel winnerLabel = new JLabel (cong);
        winnerLabel.setFont (new Font ("Verdana", Font.BOLD, 50));
        winnerLabel.setForeground (color);
        setLayout (null);
        winnerLabel.setBounds (520 + x, 150, 350, 50);
        add (winnerLabel);

        JLabel p1Stats = new JLabel ("Player 1 Stats:");
        p1Stats.setForeground (Color.BLUE);
        p1Stats.setFont (new Font ("Verdana", Font.BOLD, 30));
        p1Stats.setBounds (270, 300, 300, 30);
        add (p1Stats);

        JLabel p1FiredLabel = new JLabel ("Pellets Fired: " + p1Fired);
        p1FiredLabel.setForeground (Color.BLUE);
        p1FiredLabel.setFont (new Font ("Verdana", Font.BOLD, 20));
        p1FiredLabel.setBounds (300, 350, 200, 30);
        add (p1FiredLabel);
        
        JLabel p1Accuracy = new JLabel ("Accuracy: " + ((int)((double)p2Hit / p1Fired * 100)) + "%");
        if ((int)((double)p2Hit / p1Fired * 100) == 0) p1Accuracy.setText ("Accuracy: N/A");
        p1Accuracy.setForeground (Color.BLUE);
        p1Accuracy.setFont (new Font ("Verdana", Font.BOLD, 20));
        p1Accuracy.setBounds (300, 400, 200, 30);
        add (p1Accuracy);

        JLabel attack1 = new JLabel ("Attack: " + p1Attack);
        attack1.setForeground (Color.BLUE);
        attack1.setFont (new Font ("Verdana", Font.BOLD, 20));
        attack1.setBounds (300, 450, 200, 30);
        add (attack1);

        JLabel winsLabel1 = new JLabel ("Wins: " + p1Wins);
        winsLabel1.setForeground (Color.BLUE);
        winsLabel1.setFont (new Font ("Verdana", Font.BOLD, 20));
        winsLabel1.setBounds (300,500, 200, 30);
        add (winsLabel1);

        JLabel p2Stats = new JLabel ("Player 2 Stats:");
        p2Stats.setForeground (Color.RED);
        p2Stats.setFont (new Font ("Verdana", Font.BOLD, 30));
        p2Stats.setBounds (820, 300, 300, 30);
        add (p2Stats);
        
        JLabel p2FiredLabel = new JLabel ("Pellets Fired: " + p2Fired);
        p2FiredLabel.setForeground (Color.RED);
        p2FiredLabel.setFont (new Font ("Verdana", Font.BOLD, 20));
        p2FiredLabel.setBounds (850, 350, 200, 30);
        add (p2FiredLabel);

        JLabel p2Accuracy = new JLabel ("Accuracy: " + ((int)((double)p1Hit / p2Fired * 100)) + "%");
        if ((int)((double)p1Hit / p2Fired * 100) == 0) p2Accuracy.setText ("Accuracy: N/A");
        p2Accuracy.setForeground (Color.RED);
        p2Accuracy.setFont (new Font ("Verdana", Font.BOLD, 20));
        p2Accuracy.setBounds (850, 400, 200, 30);
        add (p2Accuracy);

        JLabel attack2 = new JLabel ("Attack: " + p2Attack);
        attack2.setForeground (Color.RED);
        attack2.setFont (new Font ("Verdana", Font.BOLD, 20));
        attack2.setBounds (850, 450, 200, 30);
        add (attack2);

        JLabel winsLabel2 = new JLabel ("Wins: " + p2Wins);
        winsLabel2.setForeground (Color.RED);
        winsLabel2.setFont (new Font ("Verdana", Font.BOLD, 20));
        winsLabel2.setBounds (850, 500, 200, 30);
        add (winsLabel2);

        JLabel playAgain = new JLabel ("Press SPACE to play again");
        playAgain.setForeground (Color.CYAN);
        playAgain.setFont (new Font ("Verdana", Font.BOLD, 30));
        playAgain.setBounds (450, 600, 500, 35);
        add (playAgain);

        revalidate ();
        repaint ();
    }    
}