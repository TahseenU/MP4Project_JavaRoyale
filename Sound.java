import java.io.File;
import java.io.IOException;
import java.lang.InterruptedException;
import javax.sound.sampled.*;
import javax.swing.ActionListener;
import javax.swing.Timer;

public class Sound{
    public void background () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File background = new File ("C:/Users/utahs/Downloads/Background.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (background);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void shoot () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File shoot = new File ("C:/Users/utahs/Downloads/Shoot.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (shoot);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void megaShoot () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File shoot = new File ("C:/Users/utahs/Downloads/MegaShoot.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (shoot);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void blueWin () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File congratulate = new File ("C:/Users/utahs/Downloads/Blue.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (congratulate);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void redWin () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File congratulate = new File ("C:/Users/utahs/Downloads/Red.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (congratulate);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void draw () throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        File ermWhatTheSigma = new File ("C:/Users/utahs/Downloads/Draw.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (ermWhatTheSigma);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        Thread.sleep (1000);
        clip.start ();
    }
}