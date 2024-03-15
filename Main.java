import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main{
  public static void main (String[] args){
    SwingUtilities.invokeLater (new Runnable (){
      @Override
      public void run (){
        JFrame frame = new JFrame ();
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        Game game = new Game ();
        frame.getContentPane ().add (game);
        frame.pack ();
        frame.setLocationRelativeTo (null);
        frame.setVisible (true);
      }
    });
  }
}