package Entity;

import Api.Score;
import Game.TimerLabel;
import Tools.AudioHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Rabbit extends Entity  {

    private int startingShow ;
    private int frameCount;
    private JLabel collision;
    private BufferedImage hide;
    private BufferedImage show1, show2, show3;
    private BufferedImage died1, died2, died3;
    private boolean isHide = true;
    private boolean isDead = false;



    private AudioHandler audioHandler;


    public Rabbit (int x, int y, int speed2){
        super(x,y,speed2);


        audioHandler = new AudioHandler();

        startingShow = 150;
        frameCount = 0;

        getRabbitImage();

        collision = new JLabel();

        setSpeed(getSpeed()+startingShow);

        collision.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                audioHandler.play(AudioHandler.PUNCH);
                if (!isHide&&!isDead&&!TimerLabel.isTimeUp){
                    audioHandler.play(AudioHandler.POINT);
                    Score.addScore();
                    isDead = true;
                    frameCount = startingShow;
                }
            }
        });
    }

    public void getRabbitImage(){
        hide = setup("/rabbit/hide0.png");
        show1 = setup("/rabbit/show1.png");
        show2 = setup("/rabbit/show2.png");
        show3 = setup("/rabbit/show3.png");
        died1 = setup("/rabbit/died1.png");
        died2 = setup("/rabbit/died2.png");
        died3 = setup("/rabbit/died3.png");
    }

    public JLabel getCollision() {
        return collision;
    }


    public void draw(Graphics2D g2)
    {
        BufferedImage img = null;
        frameCount++;
        if (frameCount < startingShow){
            img = hide;
        }
        if (!TimerLabel.isTimeUp){
            if (isDead){
                if (frameCount >= startingShow && frameCount < startingShow+41) {
                    isHide = false;
                    if (frameCount < startingShow+35) {

                        img = died1;
                    }
                    if (frameCount >= startingShow+35&& frameCount < startingShow+38 ){
                        img = died2;
                    }
                    if (frameCount >= startingShow+38&& frameCount < startingShow+41){
                        img = died3;


                    }
                }else{
                    isDead = false;
                    isHide = true;
                    frameCount = 0;
                    img= hide;
                }
            } else{
                if (frameCount >=startingShow&& frameCount < getSpeed()) {
                    isHide = false;
                    if (frameCount == startingShow) {
                        audioHandler.play(AudioHandler.VANISH);
                        img = show1;
                    }
                    if (frameCount == startingShow+1 ){
                        img = show2;
                    }
                    if (frameCount > startingShow+1){
                        img = show3;
                    }
                    if (frameCount == getSpeed()-2){
                        img = show2;

                    }
                    if (frameCount == getSpeed()-1){
                        img = show1;

                    }
                }else{
                    img= hide;
                }
            }
        }else {
            img = hide;
        }

        if (frameCount >= getSpeed() &&! isDead){
            frameCount =0;
            isHide = true;
        }
        collision.setBounds(getXKoordinat(), getYKoordinat(),getEntitySize(),getEntitySize());
        g2.drawImage(img,getXKoordinat(), getYKoordinat(),null);
    }



}
