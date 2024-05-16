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
    private long scd1;
    private long scd2;
    private boolean[] keysPressed;
    private Wall[] walls;
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
    private int p1Damage;
    private int p2Damage;
    private int p1Crits;
    private int p2Crits;
    private int p1Super;
    private int p2Super;
    private int p1Heals;
    private int p2Heals;
    private int p1Buffs;
    private int p2Buffs;

    public Game (){
        pellets = new ArrayList<> ();
        boxes = new ArrayList<> ();
        buffs = new ArrayList<> ();
        won = false;
        p1Health = 200;
        p2Health = 200;
        p1Attack = 10;
        p2Attack = 10;
        speed = 5;
        pSpeed = 11;
        cooldown = 200;
        x1 = 100;
        y1 = 100;
        x2 = 1204;
        y2 = 532;
        p1Heals = 0;
        p2Heals = 0;
        p1Buffs = 0;
        p2Buffs = 0;
        keysPressed = new boolean[1000];
        walls = new Wall[3];
        walls[0] = new Wall (500, 300, 20, 150);
        walls[1] = new Wall (1099, 250, 200, 30);
        walls[2] = new Wall (200, 650, 100, 25);
        setPreferredSize (new Dimension (1355, 685));
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

        JLabel[] controls1 = new JLabel[6];
        int y = 1;
        for (int i = 0; i < 6; i++){
            controls1[i] = new JLabel ();
            controls1[i].setForeground (Color.BLUE);
            controls1[i].setBounds (200, 25 + 50 * y, 200, 100);
            controls1[i].setFont (new Font ("Verdana", Font.BOLD, 20));
            add (controls1[i]);
            y++;
        }
        controls1[0].setText ("UP - W");
        controls1[1].setText ("DOWN - S");
        controls1[2].setText ("LEFT - A");
        controls1[3].setText ("RIGHT - D");
        controls1[4].setText ("SHOOT - R");
        controls1[5].setText ("MEGA SHOT - T");

        JLabel[] controls2 = new JLabel[6];
        y = 1;
        for (int i = 0; i < 6; i++){
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
        controls2[4].setText ("SHOOT - NUMPAD 1");
        controls2[5].setText ("MEGA SHOT - NUMPAD 2");

        JLabel info = new JLabel ("Info:");
        info.setForeground (Color.WHITE);
        info.setBounds (200, 385, 100, 100);
        info.setFont (new Font ("Verdana", Font.BOLD, 30));
        add (info);

        JLabel[] infos = new JLabel[7];
        y = 0;
        for (int i = 0; i < 7; i++){
            infos[i] = new JLabel ();
            infos[i].setForeground (Color.WHITE);
            infos[i].setBounds (200, 465 + 30 * y, 700, 20);
            infos[i].setFont (new Font ("Verdana", Font.BOLD, 16));
            add (infos[i]);
            y++;
        }
        infos[0].setText ("Walls - Blocks pellets (Cannot block mega pellets); Randomly teleports players");
        infos[1].setText ("Green Health Boxes - Heal 40 HP (Max: 150)");
        infos[2].setText ("Purple Buff Boxes - Randomly Increase Attack by 1, 2, or 3 (Base: 10)");
        infos[3].setText ("Critical Hit - 5% Chance; Deal x2.5 damage");
        infos[4].setText ("Super Crit - 1% Chance; Deal x6 damage");
        infos[5].setText ("Passive: Heal ~2 HP/s (Max: 200)");
        infos[6].setText ("Mega Shot - Deals 4x dmg; 5 sec cooldown; Moves at 3x speed");

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
                speed = 5;
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
                pSpeed = 16;
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
                if (p1Health < 200) p1Health += 0.035;
                if (p2Health < 200) p2Health += 0.035;
                movePlayers ();     
                movePellets ();
                repaint ();
                frameCount++;
                if (p1Health <= 0){
                    if (p2Health <= 0) win (0);
                    else win (2);
                }
                else if (p2Health <= 0) win (1);
                if (Math.random () <= 0.0015 && boxes.size () < 3){
                    boxes.add (new HealthBox ());
                }
                if (Math.random () <= 0.00075 && buffs.size () < 5){
                    buffs.add (new Buff ());
                }
            }
        });
        timer.start ();
    }    

    private void movePlayers() {
        if (keysPressed[KeyEvent.VK_W]) if (!(y1 <= 0)) y1 -= speed;
        if (keysPressed[KeyEvent.VK_A]) if (!(x1 <= 0)) x1 -= speed;
        if (keysPressed[KeyEvent.VK_S]) if (!(y1 >= 635)) y1 += speed;
        if (keysPressed[KeyEvent.VK_D]) if (!(x1 >= 1305)) x1 += speed;

        if (keysPressed[KeyEvent.VK_UP]) if (!(y2 <= 0)) y2 -= speed;
        if (keysPressed[KeyEvent.VK_LEFT]) if (!(x2 <= 0)) x2 -= speed;
        if (keysPressed[KeyEvent.VK_DOWN]) if (!(y2 >= 635)) y2 += speed;
        if (keysPressed[KeyEvent.VK_RIGHT]) if (!(x2 >= 1305)) x2 += speed;

        for (Wall wall : walls){
            int[] wallX = new int[wall.getWidth() + 49];
            int[] wallY = new int[wall.getHeight() + 49];
            for (int j = 0; j < wallX.length; j++) wallX[j] = j + wall.getX() - 49;
            for (int j = 0; j < wallY.length; j++) wallY[j] = j + wall.getY() - 49;
            for (int j = 0; j < wallX.length; j++){
                for (int k = 0; k < wallY.length; k++){
                    if (wallX[j] == x1 && wallY[k] == y1){
                        x1 = (int) (Math.random () * 1300);
                        y1 = (int) (Math.random () * 635);
                    }
                    if (wallX[j] == x2 && wallY[k] == y2){
                        x2 = (int) (Math.random () * 1300);
                        y2 = (int) (Math.random () * 635);
                    }
                }
            }
        }

        if (boxes.size() > 0){
            for (int i = boxes.size()-1; i >= 0; i--){
                for (int j = x1 - 30; j < x1 + 50; j++){
                    for (int k = y1 - 30; k < y1 + 50; k++){
                        if (boxes.size() == 0) break;
                        if (i == boxes.size()) break;
                        if (j == boxes.get(i).getX() && k == boxes.get(i).getY()){
                            p1Health += 40;
                            p1Heals++;
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

                            if (p1Health > 200) p1Health = 200;
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
                            p2Heals++;
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

                            if (p2Health > 200) p2Health = 200;
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
                            int add = 1;
                            if (Math.random () >= 2 / 3.0) add = 3;
                            else if (Math.random () >= 2 / 3.0) add = 2;
                            p1Attack += add;
                            p1Buffs++;
                            buffs.remove (i);
                            JLabel dmg = new JLabel ("+" + add + " Attack");
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
                            int add = 1;
                            if (Math.random () >= 2 / 3.0) add = 3;
                            else if (Math.random () >= 2 / 3.0) add = 2;
                            p2Attack += add;
                            p2Buffs++;
                            buffs.remove (i);
                            JLabel dmg = new JLabel ("+" + add + " Attack");
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

    private void movePellets (){
        int xTar;
        int yTar;
        for (int i = 0; i < pellets.size (); i++){
            Pellet pellet = pellets.get (i);
            int damageMult = 1;
            if (pellet.getSize () == 20) damageMult = 4;
            if (pellet.getTarget () == 2){
                xTar = x2;
                yTar = y2;
            }
            else{
                xTar = x1;
                yTar = y1;
            }
            pellet.move ();

            for (Wall wall : walls){
                int[] wallX = new int[wall.getWidth() + 6];
                int[] wallY = new int[wall.getHeight() + 6];
                for (int j = 0; j < wallX.length; j++) wallX[j] = j + wall.getX() - 6;
                for (int j = 0; j < wallY.length; j++) wallY[j] = j + wall.getY() - 6;
                for (int j = 0; j < wallX.length; j++){
                    for (int k = 0; k < wallY.length; k++){
                        if (wallX[j] == pellet.getX () && wallY[k] == pellet.getY() && pellet.getSize () == 6){
                            pellets.remove (i);
                            i--;
                        }
                    }
                }
            }

            int[] xTargets = new int[40 + pellet.getSize ()];
            int[] yTargets = new int[40 + pellet.getSize ()];
            for (int j = 0; j < xTargets.length; j++) xTargets[j] = j + xTar - pellet.getSize () + 2;
            for (int j = 0; j < yTargets.length; j++) yTargets[j] = j + yTar - pellet.getSize () + 2;

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
                                    if (Math.random() <= 0.2){
                                        p2Health -= (6 * p1Attack * damageMult);
                                        p2Hit++;
                                        p1Damage += 6 * p1Attack * damageMult;
                                        p1Super++;
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
                                        p2Health -= (2.5 * p1Attack * damageMult);
                                        p2Hit++;
                                        p1Damage += 2.5 * p1Attack * damageMult;
                                        p1Crits++;
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
                                    p2Health -= p1Attack * damageMult;
                                    p2Hit++;
                                    p1Damage += p1Attack * damageMult;
                                }
                            }
                            else{
                                if (Math.random () <= 0.05){
                                    if (Math.random() <= 0.2){
                                        p1Health -= (6 * p2Attack * damageMult);
                                        p1Hit++;
                                        p2Damage += 6 * p2Attack * damageMult;
                                        p2Super++;
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
                                        p1Health -= (2.5 * p2Attack * damageMult);
                                        p1Hit++;
                                        p2Damage += 2.5 * p2Attack * damageMult;
                                        p2Crits++;
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
                                    p1Health -= p2Attack * damageMult;
                                    p1Hit++;
                                    p2Damage += p2Attack * damageMult;
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
            for (Wall wall : walls){
                g2d.setColor (Color.GRAY);
                g2d.fillRect (wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            }
            g2d.setColor (Color.BLUE);
            g2d.fillRect (x1, y1, 50, 50);
            g2d.setColor (Color.RED);
            g2d.fillRect (x2, y2, 50, 50);
            g2d.setColor (Color.WHITE);
            g2d.fillRect (x1 - 1, y1 - 11, 59, 11);
            g2d.fillRect (x2 - 1, y2 - 11, 59, 11);
            g2d.setColor (new Color (255, 0, 255));
            g2d.fillRect (x1 + 1, y1 - 9, (int) (p1Health / 4 * 1.1), 7);
            g2d.fillRect (x2 + 1, y2 - 9, (int) (p2Health / 4 * 1.1), 7);
            for (Pellet pellet : pellets){
                if (pellet.getTarget () == 2) g2d.setColor (Color.BLUE);
                else g2d.setColor (Color.RED);
                g2d.fillRect ((int) pellet.getX (), (int) pellet.getY (), pellet.getSize (), pellet.getSize ());
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
                pellets.add (new Pellet (x1, y1, x2, y2, 6, pSpeed, 2));
            }
        }
        if (e.getKeyCode () == KeyEvent.VK_T){
            if (time - scd1 >= cooldown * 10){
                p1Fired++;
                scd1 = time;
                pellets.add (new Pellet (x1, y1, x2, y2, 20, pSpeed * 3, 2));
            }
        }
        if (e.getKeyCode () == KeyEvent.VK_NUMPAD1){
            if (time - cd2 >= cooldown){
                p2Fired++;
                cd2 = time;
                pellets.add (new Pellet (x2, y2, x1, y1, 6, pSpeed, 1));
            }
        }
        if (e.getKeyCode () == KeyEvent.VK_NUMPAD2){
            if (time - scd2 >= cooldown * 10){
                p2Fired++;
                scd2 = time;
                pellets.add (new Pellet (x2, y2, x1, y1, 20, pSpeed * 3, 1));
            }
        }
        if (won && e.getKeyCode () == KeyEvent.VK_SPACE){
            won = false;
            playing = false;
            p1Health = 200;
            p2Health = 200;
            p1Attack = 10;
            p2Attack = 10;
            x1 = 100;
            y1 = 100;
            x2 = 1200;
            y2 = 550;
            p1Fired = 0;
            p1Hit = 0;
            p1Damage = 0;
            p1Heals = 0;
            p1Buffs = 0;
            p2Fired = 0;
            p2Hit = 0;
            p2Damage = 0;
            p2Heals = 0;
            p2Buffs = 0;
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
                x = 60;
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
        winnerLabel.setBounds (520 + x, 120, 350, 50);
        add (winnerLabel);

        JLabel p1Stats = new JLabel ("Player 1 Stats:");
        p1Stats.setForeground (Color.BLUE);
        p1Stats.setFont (new Font ("Verdana", Font.BOLD, 30));
        p1Stats.setBounds (270, 230, 300, 30);
        add (p1Stats);

        JLabel p2Stats = new JLabel ("Player 2 Stats:");
        p2Stats.setForeground (Color.RED);
        p2Stats.setFont (new Font ("Verdana", Font.BOLD, 30));
        p2Stats.setBounds (820, 230, 300, 30);
        add (p2Stats);

        JLabel[][] stats = new JLabel[2][9];
        for (int r = 0; r < 2; r++){
            for (int c = 0; c < 9; c++){
                stats[r][c] = new JLabel ();
                if (r == 0) stats[r][c].setForeground (Color.BLUE);
                else stats[r][c].setForeground (Color.RED);
                stats[r][c].setFont (new Font ("Verdana", Font.BOLD, 18));
                stats[r][c].setBounds (300 + 550 * r, 270 + 30 * c, 300, 25);
                add (stats[r][c]);
            }
        }
        stats[0][0].setText ("Pellets Fired: " + p1Fired);
        stats[0][1].setText ("Accuracy: " + ((int) ((double)p2Hit / p1Fired * 100)) + "%");
        if (p1Fired == 0) stats[0][1].setText ("Accuracy: N/A");
        stats[0][2].setText ("Attack: " + p1Attack);
        stats[0][3].setText ("Damage Dealt: " + p1Damage);
        stats[0][4].setText ("Health Boxes Collected: " + p1Heals);
        stats[0][5].setText ("Attack Buffs Collected: " + p1Buffs);
        stats[0][6].setText ("Critical Hits: " + p1Crits);
        stats[0][7].setText ("Super Critical Hits: " + p1Super);
        stats[0][8].setText ("Wins: " + p1Wins);

        stats[1][0].setText ("Pellets Fired: " + p2Fired);
        stats[1][1].setText ("Accuracy: " + ((int)((double)p1Hit / p2Fired * 100)) + "%");
        if (p2Fired == 0) stats[1][1].setText ("Accuracy: N/A");
        stats[1][2].setText ("Attack: " + p2Attack);
        stats[1][3].setText ("Damage Dealt: " + p2Damage);
        stats[1][4].setText ("Health Boxes Collected: " + p2Heals);
        stats[1][5].setText ("Attack Buffs Collected: " + p2Buffs);
        stats[1][6].setText ("Critical Hits: " + p2Crits);
        stats[1][7].setText ("Super Critical Hits: " + p2Super);
        stats[1][8].setText ("Wins: " + p2Wins);

        JLabel inform = new JLabel ("*All stats except for crits and wins are reset each match");
        inform.setForeground (Color.BLACK);
        inform.setFont (new Font ("Verdana", Font.BOLD, 15));
        inform.setBounds (450, 620, 500, 20);
        add (inform);

        JLabel playAgain = new JLabel ("Press SPACE to play again");
        playAgain.setForeground (Color.CYAN);
        playAgain.setFont (new Font ("Verdana", Font.BOLD, 30));
        playAgain.setBounds (465, 570, 500, 35);
        add (playAgain);

        revalidate ();
        repaint ();
    }
}