import java.io.File;
import java.io.IOException;
import java.lang.InterruptedException;
import javax.sound.sampled.*;
import javax.swing.ActionListener;
import javax.swing.Timer;

public class Sound{
    public void background () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File background = new File ("MP4Project_JavaRoyale-main/Chestplate.jpg");
        AudioInputStream stream = AudioSystem.getAudioInputStream (background);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void shoot () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File shoot = new File ("MP4Project_JavaRoyale-main/Shoot.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (shoot);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void megaShoot () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File shoot = new File ("MP4Project_JavaRoyale-main/MegaShoot.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (shoot);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void blueWin () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File congratulate = new File ("MP4Project_JavaRoyale-main/MegaShoot.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (congratulate);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void redWin () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File congratulate = new File ("MP4Project_JavaRoyale-main/Red.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (congratulate);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        clip.start ();
    }
    public void draw () throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        File ermWhatTheSigma = new File ("MP4Project_JavaRoyale-main/Draw.wav");
        AudioInputStream stream = AudioSystem.getAudioInputStream (ermWhatTheSigma);
        Clip clip = AudioSystem.getClip ();
        clip.open (stream);
        Thread.sleep (1000);
        clip.start ();
    }
}