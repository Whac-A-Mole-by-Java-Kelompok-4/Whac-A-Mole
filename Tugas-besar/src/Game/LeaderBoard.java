package Game;

import Api.Score;
import Tools.ImageHandler;
import Tools.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LeaderBoard extends JPanel implements ImageHandler {

    public final int screenWidth = 437;
    private final int screenHeight = 672;
    private BufferedImage background;
    public LeaderBoard(){

        background = setup("/bg/leaderBoard.png");
        try{
            Score.getUserScore();
        }catch (Exception e){
            e.printStackTrace();
        }

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 8));
        JPanel gap = new JPanel();
        gap.setPreferredSize(new Dimension(350, 100));
        gap.setOpaque(false);
        add(gap);

        for (int i = 0; i < 10; i++) {
            if (Score.getUsernames().get(i) != null){
                JPanel userPanel = new JPanel();

                userPanel.setPreferredSize(new Dimension(350, 45));
                userPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

                userPanel.setBackground(Color.decode("#b3823e"));
                userPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.decode("#8d6731")));

                JLabel nameLabel = new JLabel(Score.getUsernames().get(i));
                JLabel number = new JLabel(i+1+"");
                JLabel score = new JLabel(Score.getHighscores().get(i)+"");

                number.setPreferredSize(new Dimension(30, 30));
                number.setHorizontalAlignment(JLabel.CENTER);
                number.setFont(new Font("Moonrising", Font.BOLD, 16));
                number.setForeground(Color.decode("#c4ee32"));

                nameLabel.setPreferredSize(new Dimension(200, 30));

                nameLabel.setFont(new Font("Moonrising", Font.BOLD, 14));
                nameLabel.setForeground(Color.WHITE);

                score.setPreferredSize(new Dimension(30, 30));
                score.setOpaque(true);
                score.setHorizontalAlignment(JLabel.CENTER);
                score.setBackground(Color.decode("#b3823e"));
                score.setFont(new Font("Moonrising", Font.BOLD, 14));
                score.setForeground(Color.WHITE);

                userPanel.add(number);
                userPanel.add(nameLabel);
                userPanel.add(score);

                add(userPanel);
            }
        }
        setPreferredSize(new Dimension(screenWidth, screenHeight));
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
    }

}
