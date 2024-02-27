import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game {
    private JFrame frame;
    private JPanel panel;
    public static JPanel p1;
    public static JPanel p2;
    public static int x1;
    public static int y1;
    public static int x2;
    public static int y2;
    private boolean[] keysPressed;

    public Game() {
        x1 = 100;
        y1 = 100;
        x2 = 200;
        y2 = 100;
        frame = new JFrame();
        p1 = new JPanel();
        p2 = new JPanel();
        panel = new JPanel();
        panel.setBounds(0, 0, 1350, 700);
        p1.setBounds(100, 100, 50, 50);
        p2.setBounds(200, 100, 50, 50);
        panel.setBackground(new Color(50, 200, 100));
        p1.setBackground(Color.BLUE);
        p2.setBackground(Color.RED);

        frame.getContentPane().setLayout(null); // Use absolute layout
        frame.getContentPane().add(p1);
        frame.getContentPane().add(p2);
        frame.getContentPane().add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(10, 10, 1350, 700);
        frame.setVisible(true);

        // Initialize key flags for each player
        keysPressed = new boolean[KeyEvent.KEY_LAST + 1];
        for (int i = 0; i < keysPressed.length; i++) {
            keysPressed[i] = false;
        }

        // Attach a KeyListener to the frame
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                keysPressed[e.getKeyCode()] = true;
                movePlayers();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysPressed[e.getKeyCode()] = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // Implementation
            }
        });
    }

    private void movePlayers() {
        // Player 1 movement
        if (keysPressed[KeyEvent.VK_W]) y1 -= 10;
        if (keysPressed[KeyEvent.VK_A]) x1 -= 10;
        if (keysPressed[KeyEvent.VK_S]) y1 += 10;
        if (keysPressed[KeyEvent.VK_D]) x1 += 10;
        p1.setLocation(x1, y1);

        // Player 2 movement
        if (keysPressed[KeyEvent.VK_UP]) y2 -= 10;
        if (keysPressed[KeyEvent.VK_LEFT]) x2 -= 10;
        if (keysPressed[KeyEvent.VK_DOWN]) y2 += 10;
        if (keysPressed[KeyEvent.VK_RIGHT]) x2 += 10;
        p2.setLocation(x2, y2);
    }
}
