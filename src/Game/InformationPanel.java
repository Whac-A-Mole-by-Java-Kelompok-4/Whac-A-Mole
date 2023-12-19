package Game;

import Api.Score;
import Tools.ImageHandler;
import Tools.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InformationPanel extends JPanel implements ImageHandler {
    private BufferedImage image;
    private TimerLabel timer;
    private JLabel score;
    private JButton restartButton;
    private final int screenWidth = 788;
    private final int screenHeight = 98;

    public InformationPanel(){
        this.setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight()));
        image = setup("/bg/Info.png");

        setLayout(new FlowLayout(FlowLayout.LEFT, 100, 55));

        timer = new TimerLabel();
        score = new JLabel();
        score.setText("Score: "+Score.getScore());
        score.setFont(new Font("Moonrising", Font.BOLD, 20));
        score.setForeground(Color.decode("#93673e"));
        score.setPreferredSize(new Dimension(150,25));

        add(score);
        add(timer);

        restartButton = new JButton("Restart");
        restartButton.setBackground(Color.decode("#c4ee32"));
        restartButton.setForeground(Color.decode("#8d6731"));
        restartButton.setFont(new Font("Moonrising", Font.BOLD, 18));
        restartButton.setMargin(new Insets(0, 0, 0, 0));
        add(restartButton);
        restartButton.addActionListener(e -> {
            timer.restart();
            Score.resetScore();
            updateScore();
        });
    }
    public void paintComponent(Graphics g){
        g.drawImage(image,0,0,null);
    }
    @Override
    public BufferedImage setup(String path){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(path));
            image = UtilityTool.scaleImage(image, getScreenWidth(), getScreenHeight());
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void stopOperation(){
        timer.stop();
//        score.stop();
    }

    public int getScreenWidth(){
        return screenWidth;
    }
    public int getScreenHeight(){
        return screenHeight;
    }
    public void updateScore(){
        score.setText("Score: "+Score.getScore());
    }
}
