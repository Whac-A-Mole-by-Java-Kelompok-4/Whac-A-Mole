package Game;

import Api.Score;
import Tools.AudioHandler;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class TimerLabel extends JLabel implements Runnable {
    private Thread thread;
    private int time;
    private AudioHandler audioHandler;
    public static boolean isTimeUp = false;
    TimerLabel(){
        audioHandler = new AudioHandler();
        isTimeUp=false;
        time=30;
        setText("Time: "+time);
        thread = new Thread(this);
        setFont(new Font("Moonrising", Font.BOLD, 20));
        setForeground(Color.decode("#93673e"));
        setPreferredSize(new Dimension(150,25));
        thread.start();
    }

    public void restart(){
        isTimeUp=false;
        setForeground(Color.decode("#93673e"));
        time=30;
        setText("Time: "+time);
        if (thread==null){
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop(){
        thread = null;
    }

    @Override
    public void run() {
        while (thread!=null){
            try {
                if (time==0){
                    audioHandler.play(AudioHandler.GAMEOVER);
                    setText("Time's Up");
                    Score.updateScore();
                    isTimeUp=true;
                    thread=null;
                    break;
                }
                if (time<=10){
                    setForeground(Color.RED);
                    audioHandler.play(AudioHandler.COUNT);
                }
                Thread.sleep(1000);
                time--;
                setText("Time: "+time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
