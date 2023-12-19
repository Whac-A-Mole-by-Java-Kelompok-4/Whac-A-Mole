
import Api.JDBC;
import Game.GamePanel;
import Game.InformationPanel;
import Game.MainMenu;
import Tools.AudioHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {

        JDBC.connectToDatabase();
        AudioHandler.getAudio();

        JFrame window = new JFrame();
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                JDBC.stopConnection();
            }
        });
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Whac-A-Mole");

        ImageIcon icon = new ImageIcon("res/icon/icon.png");
        Image image = icon.getImage();

        window.setIconImage(image);

        MainMenu mainMenu = new MainMenu();



        window.add(mainMenu);
        window.pack();

        window.setLocationRelativeTo(null);




        window.setVisible(true);







    }
}