import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.InterruptedException;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.*;

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
    private ArrayList<Armor> armors;
    private Sound sound;
    private Timer timer;
    private Timer fpsTimer;
    private double p1Health;
    private double p2Health;
    private int p1Attack;
    private int p2Attack;
    private int p1Defense;
    private int p2Defense;
    private int frameCount;
    private boolean won;
    private boolean continued;
    private boolean finished;
    private boolean playing;
    private int p1Fired;
    private int p2Fired;
    private int p1Hit;
    private int p2Hit;
    private int p1Wins;
    private int p2Wins;
    private double p1Damage;
    private double p2Damage;
    private int p1Crits;
    private int p2Crits;
    private int p1Super;
    private int p2Super;
    private int p1Heals;
    private int p2Heals;
    private int p1Buffs;
    private int p2Buffs;
    private int p1Shields;
    private int p2Shields;
    private int p1FiredTotal;
    private int p2FiredTotal;
    private int p1HitTotal;
    private int p2HitTotal;
    private double p1DamageTotal;
    private double p2DamageTotal;
    private int p1CritsTotal;
    private int p2CritsTotal;
    private int p1SuperTotal;
    private int p2SuperTotal;
    private int p1HealsTotal;
    private int p2HealsTotal;
    private int p1BuffsTotal;
    private int p2BuffsTotal;
    private int p1ShieldsTotal;
    private int p2ShieldsTotal;

    public Game (){
        keysPressed = new boolean[1000];
        sound = new Sound ();
        walls = new Wall[3];
        walls[0] = new Wall (500, 300, 20, 150);
        walls[1] = new Wall (1100, 250, 200, 30);
        walls[2] = new Wall (200, 650, 100, 25);
        setPreferredSize (new Dimension (1355, 685));
        setBackground (new Color (50, 200, 100));
        setFocusable (true);
        addKeyListener (this);
        try {
            sound.background();
        }
        catch (UnsupportedAudioFileException except){}
        catch (IOException except){} 
        catch (LineUnavailableException except){}
        intro ();
        bgTimer.start ();
    }

    Timer bgTimer = new Timer (45000, new ActionListener (){
        @Override
        public void actionPerformed (ActionEvent e){
            try {
                sound.background();
            }
            catch (UnsupportedAudioFileException except){}
            catch (IOException except){} 
            catch (LineUnavailableException except){}
        }
    });

    private void intro (){
        pellets = new ArrayList<> ();
        boxes = new ArrayList<> ();
        buffs = new ArrayList<> ();
        armors = new ArrayList<> ();
        won = false;
        continued = false;
        finished = false;
        p1Health = 200;
        p2Health = 200;
        p1Attack = 10;
        p2Attack = 10;
        p1Defense = 5;
        p2Defense = 5;
        speed = 5;
        pSpeed = 8;
        cooldown = 200;
        x1 = 100;
        y1 = 100;
        x2 = 1202;
        y2 = 532;
        p1Fired = 0;
        p1Hit = 0;
        p1Damage = 0;
        p1Heals = 0;
        p1Buffs = 0;
        p1Shields = 0;
        p1Crits = 0;
        p1Super = 0;
        p2Fired = 0;
        p2Hit = 0;
        p2Damage = 0;
        p2Heals = 0;
        p2Buffs = 0;
        p2Shields = 0;
        p2Crits = 0;
        p2Super = 0;

        removeAll ();
        setBackground (new Color (50, 200, 100));
        JLabel intro = new JLabel ("Java Royale 2");
        intro.setFont (new Font ("Verdana", Font.BOLD, 70));
        intro.setBounds (400, 175, 700, 100);
        intro.setForeground (Color.RED);
        setLayout (null);
        add (intro);

        JButton start = new JButton ("PLAY");
        start.setForeground (Color.BLUE);
        start.setBounds (620, 350, 100, 30);
        start.setContentAreaFilled (false);
        start.setFont (new Font ("Verdana", Font.BOLD, 20));
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
        controls.setContentAreaFilled (false);
        controls.setFont (new Font ("Verdana", Font.BOLD, 20));
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

        JLabel ctrl = new JLabel ("CONTROLS");
        ctrl.setForeground (Color.BLACK);
        ctrl.setBounds (480, 50, 400, 100);
        ctrl.setFont (new Font ("Verdana", Font.BOLD, 50));
        add (ctrl);

        JLabel p1 = new JLabel ("Player 1:");
        p1.setForeground (Color.BLUE);
        p1.setBounds (200, 200, 150, 50);
        p1.setFont (new Font ("Verdana", Font.BOLD, 30));
        add (p1);

        JLabel p2 = new JLabel ("Player 2:");
        p2.setForeground (Color.RED);
        p2.setBounds (850, 200, 150, 50);
        p2.setFont (new Font ("Verdana", Font.BOLD, 30));
        add (p2);

        JLabel[] controls1 = new JLabel[6];
        int y = 1;
        for (int i = 0; i < 6; i++){
            controls1[i] = new JLabel ();
            controls1[i].setForeground (Color.BLUE);
            controls1[i].setBounds (200, 200 + 50 * y, 200, 100);
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
            controls2[i].setBounds (850, 200 + 50 * y, 300, 100);
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

        JButton more = new JButton ("MORE INFO");
        more.setForeground (Color.BLACK);
        more.setBackground (Color.WHITE);
        more.setBounds (1050, 640, 150, 25);
        more.setFont (new Font ("Verdana", Font.BOLD, 15));
        more.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                moreInfo ();
            }
        });
        add (more);

        revalidate ();
        repaint ();
    }

    private void moreInfo (){
        removeAll ();

        JLabel info = new JLabel ("INFO");
        info.setForeground (Color.BLACK);
        info.setBounds (575, 50, 250, 100);
        info.setFont (new Font ("Verdana", Font.BOLD, 50));
        add (info);

        JLabel[] infos = new JLabel[8];
        int y = 0;
        for (int i = 0; i < 8; i++){
            infos[i] = new JLabel ();
            infos[i].setForeground (Color.BLACK);
            infos[i].setBounds (150, 225 + 50 * y, 1200, 30);
            infos[i].setFont (new Font ("Verdana", Font.BOLD, 24));
            add (infos[i]);
            y++;
        }
        infos[0].setText ("Walls - Blocks pellets (Cannot block mega pellets)");
        infos[1].setText ("Green Health Boxes - Heal 40 HP (Max: 150)");
        infos[2].setText ("Purple Buff Boxes - Randomly Increase Attack by 1, 2, or 3 (Base: 10)");
        infos[3].setText ("Chestplates - Increases Defense by 1 (Base: 5)");
        infos[4].setText ("Critical Hit - 5% Chance; Deal x2.5 damage");
        infos[5].setText ("Super Crit - 1% Chance; Deal x6 damage");
        infos[6].setText ("Passive: Heal ~2 HP/s (Max: 200)");
        infos[7].setText ("Mega Shot - Larger Pellets that deal 5x dmg; 4 sec cooldown; Moves at 3.5x speed");

        JButton back = new JButton ("BACK");
        back.setForeground (Color.BLACK);
        back.setBackground (Color.WHITE);
        back.setBounds (20, 20, 90, 25);
        back.setFont (new Font ("Verdana", Font.BOLD, 15));
        back.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed (ActionEvent e){
                showControls ();
            }
        });
        add (back);

        revalidate ();
        repaint ();
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
        try {
            sound.background();
        }
        catch (UnsupportedAudioFileException except){}
        catch (IOException except){} 
        catch (LineUnavailableException except){}
        bgTimer.start ();
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
                if (Math.random () <= 0.001 && armors.size () < 4){
                    armors.add (new Armor ());
                }
            }
        });
        timer.start ();
    }

    private boolean checkRight (int x, int y, Wall wall){
        if (x + 56 > wall.getX() && x + 50 < wall.getX () + 5 && y + 50 > wall.getY () && y < wall.getY () + wall.getHeight ()) return false;
        return true;
    }
    private boolean checkLeft (int x, int y, Wall wall){
        if (x < wall.getX() + wall.getWidth () + 4 && x > wall.getX () + wall.getWidth () - 5 && y + 50 > wall.getY () && y < wall.getY () + wall.getHeight ()) return false;
        return true;
    }
    private boolean checkDown (int x, int y, Wall wall){
        if (y + 54 > wall.getY() && y + 50 < wall.getY () + 5 && x + 50 > wall.getX () && x < wall.getX () + wall.getWidth ()) return false;
        return true;
    }
    private boolean checkUp (int x, int y, Wall wall){
        if (y < wall.getY() + wall.getHeight () + 4 && y > wall.getY () + wall.getHeight () - 5 && x + 50 > wall.getX () && x < wall.getX () + wall.getWidth ()) return false;
        return true;
    }

    private void movePlayers() {
        boolean[] directions1 = checkDirections(x1, y1);
        boolean[] directions2 = checkDirections(x2, y2);
        movePlayer1(directions1);
        movePlayer2(directions2);

        checkBoxes();
        checkBuffs();
        checkArmors();
    }
    
    private boolean[] checkDirections(int x, int y) {
        boolean[] directions = {true, true, true, true};
    
        for (Wall w : walls) {
            if (!checkRight(x, y, w)) directions[0] = false;
            if (!checkLeft(x, y, w)) directions[1] = false;
            if (!checkDown(x, y, w)) directions[2] = false;
            if (!checkUp(x, y, w)) directions[3] = false;
        }
        return directions;
    }
    
    private void movePlayer1(boolean[] directions) {
        if (keysPressed[KeyEvent.VK_W] && directions[3] && y1 > 0) y1 -= speed;
        if (keysPressed[KeyEvent.VK_A] && directions[1] && x1 > 0) x1 -= speed;
        if (keysPressed[KeyEvent.VK_S] && directions[2] && y1 < 635) y1 += speed;
        if (keysPressed[KeyEvent.VK_D] && directions[0] && x1 < 1305) x1 += speed;
    }
    
    private void movePlayer2(boolean[] directions) {
        if (keysPressed[KeyEvent.VK_UP] && directions[3] && y2 > 0) y2 -= speed;
        if (keysPressed[KeyEvent.VK_LEFT] && directions[1] && x2 > 0) x2 -= speed;
        if (keysPressed[KeyEvent.VK_DOWN] && directions[2] && y2 < 635) y2 += speed;
        if (keysPressed[KeyEvent.VK_RIGHT] && directions[0] && x2 < 1305) x2 += speed;
    }
    
    private void checkBoxes() {
        for (int i = boxes.size() - 1; i >= 0; i--) checkCollisionWithBox(x1, y1, boxes.get(i), 40, true);
        for (int i = boxes.size() - 1; i >= 0; i--) checkCollisionWithBox(x2, y2, boxes.get(i), 40, false);

    }
    
    private void checkCollisionWithBox(int x, int y, HealthBox box, int healthGain, boolean isPlayer1) {
        for (int j = x - 30; j < x + 50; j++) {
            for (int k = y - 30; k < y + 50; k++) {
                if (j == box.getX() && k == box.getY()) {
                    if (isPlayer1) {
                        p1Health = Math.min(p1Health + healthGain, 200);
                        p1Heals++;
                    } else {
                        p2Health = Math.min(p2Health + healthGain, 200);
                        p2Heals++;
                    }
                    displayHealthMessage(x, y, healthGain);
                    boxes.remove(box);
                    return;
                }
            }
        }
    }
    
    private void displayHealthMessage(int x, int y, int healthGain) {
        JLabel health = new JLabel("+" + healthGain + " Health");
        health.setFont(new Font("Verdana", Font.BOLD, 13));
        health.setBounds(x, y - 40, 100, 20);
        health.setForeground(Color.ORANGE);
        Timer hTimer = new Timer(10, new ActionListener() {
            int appear = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                appear++;
                if (appear == 50) {
                    remove(health);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        hTimer.start();
        add(health);
    }
    
    private void checkBuffs() {
        for (int i = buffs.size() - 1; i >= 0; i--) checkCollisionWithBuff(x1, y1, buffs.get(i), true);
        for (int i = buffs.size() - 1; i >= 0; i--) checkCollisionWithBuff(x2, y2, buffs.get(i), false);
    }
    
    private void checkCollisionWithBuff(int x, int y, Buff buff, boolean isPlayer1) {
        for (int j = x - 25; j < x + 50; j++) {
            for (int k = y - 25; k < y + 50; k++) {
                if (j == buff.getX() && k == buff.getY()) {
                    int add = (Math.random() >= 2 / 3.0) ? 3 : ((Math.random() >= 2 / 3.0) ? 2 : 1);
                    if (isPlayer1) {
                        p1Attack += add;
                        p1Buffs++;
                    } else {
                        p2Attack += add;
                        p2Buffs++;
                    }
                    displayBuffMessage(x, y, add);
                    buffs.remove(buff);
                    return;
                }
            }
        }
    }
    
    private void displayBuffMessage(int x, int y, int add) {
        JLabel dmg = new JLabel("+" + add + " Attack");
        dmg.setFont(new Font("Verdana", Font.BOLD, 13));
        dmg.setBounds(x, y - 40, 100, 20);
        dmg.setForeground(Color.ORANGE);
        Timer dTimer = new Timer(10, new ActionListener() {
            int appear = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                appear++;
                if (appear == 50) {
                    remove(dmg);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        dTimer.start();
        add(dmg);
    }
    
    private void checkArmors() {
        for (int i = armors.size() - 1; i >= 0; i--) checkCollisionWithArmor(x1, y1, armors.get(i), true);
        for (int i = armors.size() - 1; i >= 0; i--) checkCollisionWithArmor(x2, y2, armors.get(i), false);
    }
    
    private void checkCollisionWithArmor(int x, int y, Armor armor, boolean isPlayer1) {
        for (int j = x - 30; j < x + 50; j++) {
            for (int k = y - 30; k < y + 50; k++) {
                if (j == armor.getX() && k == armor.getY()) {
                    if (isPlayer1) {
                        p1Defense++;
                        p1Shields++;
                    } else {
                        p2Defense++;
                        p2Shields++;
                    }
                    displayArmorMessage(x, y);
                    armors.remove(armor);
                    return;
                }
            }
        }
    }
    
    private void displayArmorMessage(int x, int y) {
        JLabel def = new JLabel("+1 Defense");
        def.setFont(new Font("Verdana", Font.BOLD, 13));
        def.setBounds(x, y - 40, 100, 20);
        def.setForeground(Color.ORANGE);
        Timer dTimer = new Timer(10, new ActionListener() {
            int appear = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                appear++;
                if (appear == 50) {
                    remove(def);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        dTimer.start();
        add(def);
    }
    

    private void hitBox (int[] arr, int var, int r){
        if (r < 56){
            arr[r] = var + r - 6;
            hitBox (arr, var, r + 1);
        }
    }

    private void movePellets (){
        int xTar;
        int yTar;
        for (int i = 0; i < pellets.size (); i++){
            Pellet pellet = pellets.get (i);
            double damageMult = 1;
            if (pellet.getSize () > 6) damageMult = 5;
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

            int[] xTargets = new int[50 + pellet.getSize ()];
            int[] yTargets = new int[50 + pellet.getSize ()];
            hitBox (xTargets, xTar, 0);
            hitBox (yTargets, yTar, 0);

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
                                        p2Health -= 30 * p1Attack * damageMult / p2Defense;
                                        p2Hit++;
                                        p1Damage += 30 * p1Attack * damageMult / p2Defense;
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
                                        p2Health -= 12.5 * p1Attack * damageMult / p2Defense;
                                        p2Hit++;
                                        p1Damage += 12.5 * p1Attack * damageMult / p2Defense;
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
                                    p2Health -= 5 * p1Attack * damageMult / p2Defense;
                                    p2Hit++;
                                    p1Damage += 5 * p1Attack * damageMult / p2Defense;
                                }
                            }
                            else{
                                if (Math.random () <= 0.05){
                                    if (Math.random() <= 0.2){
                                        p1Health -= 30 * p2Attack * damageMult / p1Defense;
                                        p1Hit++;
                                        p2Damage += 30 * p2Attack * damageMult / p1Defense;
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
                                        p1Health -= 12.5 * p2Attack * damageMult / p1Defense;
                                        p1Hit++;
                                        p2Damage += 12.5 * p2Attack * damageMult / p1Defense;
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
                                    p1Health -= 5 * p2Attack * damageMult / p1Defense;
                                    p1Hit++;
                                    p2Damage += 5 * p2Attack * damageMult / p1Defense;
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
        ImageIcon bgImage = new ImageIcon ("MP4Project_JavaRoyale-main/Bkgd.jpg");
        g.drawImage (bgImage.getImage (), 0, 0, this.getWidth (), this.getHeight (), null);
        if (!(won) && playing){
            Graphics2D g2d = (Graphics2D) g.create ();
            for (Wall wall : walls){
                ImageIcon brick = new ImageIcon ("MP4Project_JavaRoyale-main/BrickWall.jpg");
                g.drawImage (brick.getImage(), wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), null);
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
            for (Armor shield : armors){
                ImageIcon chestplate = new ImageIcon ("MP4Project_JavaRoyale-main/Chestplate.jpg");
                g.drawImage (chestplate.getImage(), shield.getX(), shield.getY(), 40, 40, null);
            }
            g2d.dispose ();
        }
    }

    @Override
    public void keyPressed (KeyEvent e){
        keysPressed[e.getKeyCode ()] = true;
        long time = System.currentTimeMillis ();
        if (e.getKeyCode () == KeyEvent.VK_R && playing){
            if (time - cd1 >= cooldown){
                p1Fired++;
                cd1 = time;
                pellets.add (new Pellet (x1, y1, x2, y2, 6, pSpeed, 2));
                try {
                    sound.shoot();
                }
                catch (UnsupportedAudioFileException except){}
                catch (IOException except){} 
                catch (LineUnavailableException except){}
            }
        }
        if (e.getKeyCode () == KeyEvent.VK_T && playing){
            if (time - scd1 >= cooldown * 20){
                p1Fired++;
                scd1 = time;
                pellets.add (new Pellet (x1, y1, x2, y2, 20, pSpeed * 3.5, 2));
                try {
                    sound.megaShoot();
                }
                catch (UnsupportedAudioFileException except){}
                catch (IOException except){} 
                catch (LineUnavailableException except){}
            }
        }
        if (e.getKeyCode () == KeyEvent.VK_NUMPAD1 && playing){
            if (time - cd2 >= cooldown){
                p2Fired++;
                cd2 = time;
                pellets.add (new Pellet (x2, y2, x1, y1, 6, pSpeed, 1));
                try {
                    sound.shoot();
                }
                catch (UnsupportedAudioFileException except){}
                catch (IOException except){} 
                catch (LineUnavailableException except){}
            }
        }
        if (e.getKeyCode () == KeyEvent.VK_NUMPAD2 && playing){
            if (time - scd2 >= cooldown * 20){
                p2Fired++;
                scd2 = time;
                pellets.add (new Pellet (x2, y2, x1, y1, 20, pSpeed * 3.5, 1));
                try {
                    sound.megaShoot();
                }
                catch (UnsupportedAudioFileException except){}
                catch (IOException except){} 
                catch (LineUnavailableException except){}
            }
        }
        if (won && e.getKeyCode () == KeyEvent.VK_SPACE){
            if (finished){
                won = false;
                playing = false;
                intro ();
                repaint ();
            }

            else if (continued){
                totalStats ();
            }
            else{
                showStats ();
            }
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
        playing = false;
        won = true;
        p1FiredTotal += p1Fired;
        p2FiredTotal += p2Fired;
        p1HitTotal += p1Hit;
        p2HitTotal += p2Hit;
        p1DamageTotal += p1Damage;
        p2DamageTotal += p2Damage;
        p1CritsTotal += p1Crits;
        p2CritsTotal += p2Crits;
        p1SuperTotal += p1Super;
        p2SuperTotal += p2Super;
        p1HealsTotal += p1Heals;
        p2HealsTotal += p2Heals;
        p1BuffsTotal += p1Buffs;
        p2BuffsTotal += p2Buffs;
        p1ShieldsTotal += p1Shields;
        p2ShieldsTotal += p2Shields;
        String cong = "";
        int x = 0;
        Color color = Color.BLACK;
        switch (winner){
            case 0:
                cong = "DRAW";
                x = 110;
                try {
                    sound.draw();
                }
                catch (UnsupportedAudioFileException except){}
                catch (IOException except){} 
                catch (LineUnavailableException except){}
                catch (InterruptedException except){}
                break;
            case 1:
                cong = "BLUE WINS!";
                p1Wins++;
                color = Color.BLUE;
                try {
                    sound.blueWin();
                }
                catch (UnsupportedAudioFileException except){}
                catch (IOException except){} 
                catch (LineUnavailableException except){}
                break;
            case 2:
                cong = "RED WINS!";
                p2Wins++;
                color = Color.RED;
                x = 20;
                try {
                    sound.redWin();
                }
                catch (UnsupportedAudioFileException except){}
                catch (IOException except){} 
                catch (LineUnavailableException except){}
                break;
        }
        JLabel winnerLabel = new JLabel (cong);
        winnerLabel.setFont (new Font ("Verdana", Font.BOLD, 75));
        winnerLabel.setForeground (color);
        setLayout (null);
        winnerLabel.setBounds (420 + x, 280, 700, 100);
        add (winnerLabel);

        JLabel next = new JLabel ("Press SPACE to continue");
        next.setForeground (Color.CYAN);
        next.setFont (new Font ("Verdana", Font.BOLD, 30));
        next.setBounds (465, 580, 500, 35);
        add (next);

        revalidate ();
        repaint ();
    }

    private void showStats (){
        continued = true;
        removeAll ();
        JLabel statsLabel = new JLabel ("MATCH STATS");
        statsLabel.setForeground (Color.BLUE);
        statsLabel.setFont (new Font ("Verdana", Font.BOLD, 50));
        statsLabel.setBounds (470, 90, 500, 60);
        add (statsLabel);

        JLabel p1Stats = new JLabel ("Player 1 Stats:");
        p1Stats.setForeground (Color.BLUE);
        p1Stats.setFont (new Font ("Verdana", Font.BOLD, 30));
        p1Stats.setBounds (270, 200, 300, 30);
        add (p1Stats);

        JLabel p2Stats = new JLabel ("Player 2 Stats:");
        p2Stats.setForeground (Color.RED);
        p2Stats.setFont (new Font ("Verdana", Font.BOLD, 30));
        p2Stats.setBounds (820, 200, 300, 30);
        add (p2Stats);

        JLabel[][] stats = new JLabel[2][10];
        for (int r = 0; r < 2; r++){
            for (int c = 0; c < 10; c++){
                stats[r][c] = new JLabel ();
                if (r == 0) stats[r][c].setForeground (Color.BLUE);
                else stats[r][c].setForeground (Color.RED);
                stats[r][c].setFont (new Font ("Verdana", Font.BOLD, 18));
                stats[r][c].setBounds (300 + 550 * r, 235 + 30 * c, 300, 25);
                add (stats[r][c]);
            }
        }
        stats[0][0].setText ("Pellets Fired: " + p1Fired);
        stats[0][1].setText ("Accuracy: " + ((int) ((double)p2Hit / p1Fired * 100)) + "%");
        if (p1Fired == 0) stats[0][1].setText ("Accuracy: N/A");
        stats[0][2].setText ("Attack: " + p1Attack);
        stats[0][3].setText ("Defense: " + p1Defense);
        stats[0][4].setText ("Damage Dealt: " + Math.round (p1Damage * 100) / 100.0);
        stats[0][5].setText ("Health Boxes Collected: " + p1Heals);
        stats[0][6].setText ("Attack Buffs Collected: " + p1Buffs);
        stats[0][7].setText ("Shields Collected: " + p1Shields);
        stats[0][8].setText ("Critical Hits: " + p1Crits);
        stats[0][9].setText ("Super Critical Hits: " + p1Super);

        stats[1][0].setText ("Pellets Fired: " + p2Fired);
        stats[1][1].setText ("Accuracy: " + ((int)((double)p1Hit / p2Fired * 100)) + "%");
        if (p2Fired == 0) stats[1][1].setText ("Accuracy: N/A");
        stats[1][2].setText ("Attack: " + p2Attack);
        stats[1][3].setText ("Defense: " + p2Defense);
        stats[1][4].setText ("Damage Dealt: " + Math.round (p2Damage * 100) / 100.0);
        stats[1][5].setText ("Health Boxes Collected: " + p2Heals);
        stats[1][6].setText ("Attack Buffs Collected: " + p2Buffs);
        stats[1][7].setText ("Shields Collected: " + p2Shields);
        stats[1][8].setText ("Critical Hits: " + p2Crits);
        stats[1][9].setText ("Super Critical Hits: " + p2Super);

        JLabel next = new JLabel ("Press SPACE to continue");
        next.setForeground (Color.CYAN);
        next.setFont (new Font ("Verdana", Font.BOLD, 30));
        next.setBounds (465, 580, 500, 35);
        add (next);

        revalidate ();
        repaint ();
    }

    private void totalStats (){
        finished = true;
        removeAll ();
        JLabel statsLabel = new JLabel ("SESSION STATS");
        statsLabel.setForeground (Color.RED);
        statsLabel.setFont (new Font ("Verdana", Font.BOLD, 50));
        statsLabel.setBounds (450, 90, 500, 60);
        add (statsLabel);

        JLabel p1Stats = new JLabel ("Player 1 Stats:");
        p1Stats.setForeground (Color.BLUE);
        p1Stats.setFont (new Font ("Verdana", Font.BOLD, 30));
        p1Stats.setBounds (270, 200, 300, 30);
        add (p1Stats);

        JLabel p2Stats = new JLabel ("Player 2 Stats:");
        p2Stats.setForeground (Color.RED);
        p2Stats.setFont (new Font ("Verdana", Font.BOLD, 30));
        p2Stats.setBounds (820, 200, 300, 30);
        add (p2Stats);

        JLabel[][] stats = new JLabel[2][9];
        for (int r = 0; r < 2; r++){
            for (int c = 0; c < 9; c++){
                stats[r][c] = new JLabel ();
                if (r == 0) stats[r][c].setForeground (Color.BLUE);
                else stats[r][c].setForeground (Color.RED);
                stats[r][c].setFont (new Font ("Verdana", Font.BOLD, 18));
                stats[r][c].setBounds (300 + 550 * r, 235 + 30 * c, 300, 25);
                add (stats[r][c]);
            }
        }
        stats[0][0].setText ("Pellets Fired: " + p1FiredTotal);
        stats[0][1].setText ("Accuracy: " + ((int) ((double)p2HitTotal / p1FiredTotal * 100)) + "%");
        if (p1Fired == 0) stats[0][1].setText ("Accuracy: N/A");
        stats[0][2].setText ("Damage Dealt: " + Math.round (p1DamageTotal * 100) / 100.0);
        stats[0][3].setText ("Health Boxes Collected: " + p1HealsTotal);
        stats[0][4].setText ("Attack Buffs Collected: " + p1BuffsTotal);
        stats[0][5].setText ("Shields Collected: " + p1ShieldsTotal);
        stats[0][6].setText ("Critical Hits: " + p1CritsTotal);
        stats[0][7].setText ("Super Critical Hits: " + p1SuperTotal);
        stats[0][8].setText ("Wins: " + p1Wins);

        stats[1][0].setText ("Pellets Fired: " + p2FiredTotal);
        stats[1][1].setText ("Accuracy: " + ((int)((double)p1HitTotal / p2FiredTotal * 100)) + "%");
        if (p2Fired == 0) stats[1][1].setText ("Accuracy: N/A");
        stats[1][2].setText ("Damage Dealt: " + Math.round (p2DamageTotal * 100) / 100.0);
        stats[1][3].setText ("Health Boxes Collected: " + p2HealsTotal);
        stats[1][4].setText ("Attack Buffs Collected: " + p2BuffsTotal);
        stats[1][5].setText ("Shields Collected: " + p2ShieldsTotal);
        stats[1][6].setText ("Critical Hits: " + p2CritsTotal);
        stats[1][7].setText ("Super Critical Hits: " + p2SuperTotal);
        stats[1][8].setText ("Wins: " + p2Wins);

        JLabel complete = new JLabel ("Press SPACE to play again");
        complete.setForeground (Color.CYAN);
        complete.setFont (new Font ("Verdana", Font.BOLD, 30));
        complete.setBounds (465, 580, 500, 35);
        add (complete);

        revalidate ();
        repaint ();
    }
}