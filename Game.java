import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game {
    private JFrame frame;
    private JPanel panel;
    private JPanel p1;
    private JPanel p2;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private boolean[] keysPressed;
    private Timer fps;
    private int wait;

    public Game() {
        x1 = 100;
        y1 = 100;
        x2 = 1200;
        y2 = 550;
        wait = 0;
        frame = new JFrame();
        p1 = new JPanel();
        p2 = new JPanel();
        panel = new JPanel();
        panel.setBounds(0, 0, 1350, 739);
        p1.setBounds(100, 100, 50, 50);
        p2.setBounds(1200, 550, 50, 50);
        panel.setBackground(new Color(50, 200, 100));
        p1.setBackground(Color.BLUE);
        p2.setBackground(Color.RED);

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(p1);
        frame.getContentPane().add(p2);
        frame.getContentPane().add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 1350, 739);
        frame.setVisible(true);

        keysPressed = new boolean[KeyEvent.KEY_LAST];
        for (int i = 0; i < keysPressed.length; i++) {
            keysPressed[i] = false;
        }

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                keysPressed[e.getKeyCode()] = true;
                animate ();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysPressed[e.getKeyCode()] = false;
                fps.stop ();
                fps = null;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // Implementation
            }
        });
    }

    private void animate(){
        fps = new Timer (16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (wait % 10 == 0) movePlayers ();
                p1.setLocation (x1, y1);
                p2.setLocation (x2, y2);
                p1.repaint ();
                p2.repaint ();
                wait++;
            }
        });
        fps.start ();
    }


    private void movePlayers() {
        if (keysPressed[KeyEvent.VK_W]) if (!(y1 <= 0)) y1--;
        if (keysPressed[KeyEvent.VK_A]) if (!(x1 <= 0))x1--;
        if (keysPressed[KeyEvent.VK_S]) if (!(y1 >= 650)) y1++;
        if (keysPressed[KeyEvent.VK_D]) if (!(x1 >= 1284)) x1++;

        if (keysPressed[KeyEvent.VK_UP]) if (!(y2 <= 0)) y2--;
        if (keysPressed[KeyEvent.VK_LEFT]) if (!(x2 <= 0)) x2--;
        if (keysPressed[KeyEvent.VK_DOWN]) if (!(y2 >= 650)) y2++;
        if (keysPressed[KeyEvent.VK_RIGHT]) if (!(x2 >= 1284)) x2++;
    }
}