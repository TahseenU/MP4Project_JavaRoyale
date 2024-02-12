import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game{
    private JFrame frame;
    private JPanel panel;
    private JPanel p1;
    private JPanel p2;
    public Game () {
        frame = new JFrame ();
        panel = new JPanel ();
        p1 = new JPanel ();
        p2 = new JPanel ();
        panel.setBackground (new Color (150, 255, 150));
        frame.setBounds (10, 10, 1900, 1000);
        frame.add (panel);
        p1.setBounds (100, 100, 50, 50);
        p2.setBounds (1600, 900, 50, 50);
        p1.setBackground(Color.BLUE);
        p2.setBackground (Color.RED);
        frame.add (p1);
        frame.add (p2);
        frame.setVisible (true);
    }
}