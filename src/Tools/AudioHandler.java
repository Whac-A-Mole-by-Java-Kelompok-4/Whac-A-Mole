package Tools;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class AudioHandler {

    public static final int MAINMENU = 0;
    public static final int MUSICGAME = 1;
    public static final String POINT = "/sound/point.wav";
    public static final String GAMEOVER = "/sound/over.wav";
    public static final String COUNT = "/sound/count.wav";
    public static final String PUNCH = "/sound/punch.wav";
    public static final String VANISH = "/sound/vanish.wav";

    private static Clip themeMusic[] = new Clip[2];
    private static URL musicUrl[] = new URL[2];

    public  static void getAudio(){
        musicUrl[0] = AudioHandler.class.getResource("/sound/mainmenu.wav");
        musicUrl[1] = AudioHandler.class.getResource("/sound/musicgame.wav");
        setFile(MAINMENU);
        setFile(MUSICGAME);
    }


    private  static void setFile(int i){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicUrl[i]);
            themeMusic[i] = AudioSystem.getClip();
            themeMusic[i].open(audioInputStream);
        }catch (Exception e){
            System.out.println("Error");
        }
    }


    public void play(String Path) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AudioHandler.class.getResource(Path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    public  void stop(int i){
        themeMusic[i].stop();
    }
    public  void loop(int i){
        themeMusic[i].loop(Clip.LOOP_CONTINUOUSLY);
    }


}
