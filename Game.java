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
    private Timer timer;

    public Game() {
        x1 = 100;
        y1 = 100;
        x2 = 1200;
        y2 = 550;
        frame = new JFrame();
        p1 = new JPanel();
        p2 = new JPanel();
        panel = new JPanel();
        panel.setBounds(0, 0, 1350, 700);
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
        frame.setBounds(10, 10, 1350, 700);
        frame.setVisible(true);

        keysPressed = new boolean[KeyEvent.KEY_LAST + 1];
        for (int i = 0; i < keysPressed.length; i++) {
            keysPressed[i] = false;
        }

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                keysPressed[e.getKeyCode()] = true;
                startTimer ();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysPressed[e.getKeyCode()] = false;
                timer.stop ();
                timer = null;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // Implementation
            }
        });
    }

    private void startTimer() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayers();
                p1.setLocation (x1, y1);
                p2.setLocation (x2, y2);
            }
        });
        timer.start();
    }

    private void movePlayers() {
        if (keysPressed[KeyEvent.VK_W]) y1--;
        if (keysPressed[KeyEvent.VK_A]) x1--;
        if (keysPressed[KeyEvent.VK_S]) y1++;
        if (keysPressed[KeyEvent.VK_D]) x1++;

        if (keysPressed[KeyEvent.VK_UP]) y2--;
        if (keysPressed[KeyEvent.VK_LEFT]) x2--;
        if (keysPressed[KeyEvent.VK_DOWN]) y2++;
        if (keysPressed[KeyEvent.VK_RIGHT]) x2++;
    }
}