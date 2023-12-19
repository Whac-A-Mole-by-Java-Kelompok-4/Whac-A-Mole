package Game;

import Api.JDBC;
import Api.Register;
import Tools.AudioHandler;
import Tools.ImageHandler;
import Tools.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

public class MainMenu extends JPanel implements ImageHandler {
//    Screen Setting
    private final int screenWidth = 788;
    private final int screenHeight = 720;
    private BufferedImage background;

    private AudioHandler audioHandler;

    public MainMenu() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 320));

        audioHandler= new AudioHandler();
        audioHandler.loop(AudioHandler.MAINMENU);


        JLabel usrLabel = new JLabel("Input Your Name");
        JTextField usernameInput = new JTextField();
        JButton startButton = new JButton("Start Game");
        JButton leaderboardButton = new JButton("Leaderboard");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(300, screenHeight));
        buttonPanel.setOpaque(false);

        buttonPanel.add(usrLabel);
        buttonPanel.add(usernameInput);
        buttonPanel.add(startButton);
        buttonPanel.add(leaderboardButton);

        usrLabel.setPreferredSize(new Dimension(300, 20));
        usrLabel.setFont(new Font("Moonrising", Font.BOLD, 14));
        usrLabel.setForeground(Color.decode("#8d6731"));
        usrLabel.setHorizontalAlignment(JLabel.CENTER);

        usernameInput.setPreferredSize(new Dimension(300, 35));
        usernameInput.setBackground(Color.decode("#c4ee32"));
        usernameInput.setForeground(Color.decode("#8d6731"));
        usernameInput.setFont(new Font("Moonrising", Font.BOLD, 14));
        usernameInput.setBorder(new MatteBorder(3, 3, 3, 3, Color.decode("#8d6731")));
        usernameInput.setCaretColor(Color.decode("#8d6731"));
        usernameInput.setHorizontalAlignment(JTextField.CENTER);

        startButton.setPreferredSize(new Dimension(300, 50));
        startButton.setBackground(Color.decode("#8d6731"));
        startButton.setForeground(Color.decode("#c4ee32"));
        startButton.setFont(new Font("Moonrising", Font.BOLD, 25));

        leaderboardButton.setPreferredSize(new Dimension(300, 50));
        leaderboardButton.setBackground(Color.decode("#8d6731"));
        leaderboardButton.setForeground(Color.decode("#c4ee32"));
        leaderboardButton.setFont(new Font("Moonrising", Font.BOLD, 25));

        add(buttonPanel);


        startButton.addActionListener(e -> {
            if (!usernameInput.getText().equals("")){
                try {
                    Register.register(usernameInput.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                audioHandler.stop(AudioHandler.MAINMENU);

                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (parentFrame != null) {
                    parentFrame.dispose(); // Menutup frame
                }

                JFrame gameFrame = new JFrame();
                gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                gameFrame.setResizable(false);
                gameFrame.setTitle("Whac-A-Mole");

                ImageIcon icon = new ImageIcon("res/icon/icon.png");
                Image image = icon.getImage();

                gameFrame.setIconImage(image);
                Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                        new ImageIcon("res/weapon/paluw.png").getImage(),
                        new Point(0,0),"custom cursor");
                gameFrame.setCursor(cursor);

                InformationPanel informationPanel = new InformationPanel();
                GamePanel gamePanel = new GamePanel(informationPanel);

                gameFrame.setLayout(new BoxLayout(gameFrame.getContentPane(),BoxLayout.Y_AXIS));
                gameFrame.add(informationPanel);
                gameFrame.add(gamePanel);
                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);

                gameFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent windowEvent) {
                        informationPanel.stopOperation();
                        audioHandler.stop(AudioHandler.MUSICGAME);
                        JFrame window = new JFrame();
                        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        window.setResizable(false);
                        window.setTitle("Whac-A-Mole");
                        window.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                super.windowClosing(e);
                                JDBC.stopConnection();
                            }
                        });

                        ImageIcon icon = new ImageIcon("res/icon/icon.png");
                        Image image = icon.getImage();

                        window.setIconImage(image);

                        MainMenu mainMenu = new MainMenu();
                        window.add(mainMenu);
                        window.pack();

                        window.setLocationRelativeTo(null);
                        window.setVisible(true);
                    }
                });
            }
        });
        leaderboardButton.addActionListener(e -> {
            LeaderBoard leaderboardPanel = new LeaderBoard();
            JFrame leaderboardFrame = new JFrame("Leaderboard");
            leaderboardFrame.add(leaderboardPanel);
            leaderboardFrame.pack();
            leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            leaderboardFrame.setResizable(false);
            leaderboardFrame.setLocationRelativeTo(null);
            leaderboardFrame.setVisible(true);

        });
        background = setup("/bg/background1.png");
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
