package graphicalElements;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class SoundLoader {

    public static void sound(String path) {


        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SoundLoader.class.getResource(path));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    // If you want the sound to loop infinitely, then put: clip.loop(Clip.LOOP_CONTINUOUSLY);
                    // If you want to stop the sound, then use clip.stop();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }

}
