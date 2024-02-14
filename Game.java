import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Timer;

public class Game implements KeyListener{
    private JFrame frame;
    private JPanel panel;
    private JPanel p1;
    private JPanel p2;
    private int x1;
    private int y1;
    public Game () {
        x1 = 100;
        y1 = 100;
        frame = new JFrame ();
        panel = new JPanel ();
        p1 = new JPanel ();
        p2 = new JPanel ();
        frame.setBounds (10, 10, 1900, 1000);
        p1.setBounds (x1, y1, 50, 50);
        p2.setBounds (200, 100, 50, 50);
        p1.setBackground(Color.BLUE);
        p2.setBackground (Color.RED);
        panel.setBackground (new Color (150, 255, 150));
        frame.add (p1);
        frame.add (p2);
        frame.add (panel);
        frame.setVisible (true);
        frame.addKeyListener (this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println ("Finish");
    }

    @Override
    public void keyTyped (KeyEvent e) {
        String key = e.getKeyText (e.getKeyCode ());
        if (key == "a" || key == "A") x1 -= 10;
        p1.repaint ();
    }

}