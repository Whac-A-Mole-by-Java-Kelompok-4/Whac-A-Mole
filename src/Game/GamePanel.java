package Game;

import Entity.Rabbit;
import Tools.AudioHandler;
import Tools.ImageHandler;
import Tools.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable, ImageHandler {
    private final int screenWidth = 788;
    private final int screenHeight = 622;
    private Rabbit[] rabbits = new Rabbit[6];
    private final int FPS = 60;
    private Thread gameThread;
    private AudioHandler audioHandler;
    private BufferedImage background;
    public GamePanel(InformationPanel informationPanel) {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                audioHandler.play(AudioHandler.PUNCH);
            }
        });

        audioHandler= new AudioHandler();
        audioHandler.loop(AudioHandler.MUSICGAME);

        rabbits[0] = new Rabbit(200,150,20, informationPanel);
        rabbits[1] = new Rabbit(350,150,40, informationPanel);
        rabbits[2] = new Rabbit(500, 150, 10, informationPanel);
        rabbits[3] = new Rabbit(200, 300, 50, informationPanel);
        rabbits[4] = new Rabbit(350, 300, 60, informationPanel);
        rabbits[5] = new Rabbit(500, 300, 35, informationPanel);

        background = setup("/bg/BG.png");
        startGameThread();

    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        while(gameThread != null){
            try {
                Thread.sleep(1000/FPS);
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public BufferedImage setup(String path){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(path));
            image = UtilityTool.scaleImage(image, screenWidth, screenHeight);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(background,0,0,null);

        for (int i = 0; i < rabbits.length; i++) {
            this.add(rabbits[i].getCollision());
            rabbits[i].draw(g2);
        }
        if (TimerLabel.isTimeUp){
            Color halfTransparent = new Color(187, 173, 160, 127); // Warna setengah transparan dengan alpha 127 (diambil dari kode hex #bbada0)
            g2.setColor(halfTransparent);
            g2.fillRoundRect(105,0,screenWidth-207,screenHeight-110,15,15);
            g2.setColor(Color.decode("#885a1a"));
            g2.setFont(new Font("Moonrising", Font.BOLD, 50));
            g2.drawString("GAME OVER", 250, 270);
        }
        g2.dispose();
    }
}
