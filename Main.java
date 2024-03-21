import javax.swing.JFrame;

public class Main{
  public static void main (String[] args){
    JFrame frame = new JFrame ();
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        Game game = new Game ();
        frame.getContentPane ().add (game);
        frame.pack ();
        frame.setLocationRelativeTo (null);
        frame.setVisible (true);
  }
}