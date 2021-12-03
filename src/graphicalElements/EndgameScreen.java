package graphicalElements;

import javax.swing.*;
import java.awt.*;

public class EndgameScreen extends JPanel {
    private JLayeredPane lpane = new JLayeredPane();
    private JPanel panelBlue = new JPanel();
    private JPanel panelGreen = new JPanel();
    public EndgameScreen(int width, int height, int pixelByCase) {

        int widthTotal = width * pixelByCase;
        int heightTotal = height * pixelByCase;


        JFrame window = new JFrame();
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setSize(widthTotal,heightTotal);
        ImageIcon imageIcon = ImageResizer.resize("../resources/backgroundLoading.png", widthTotal,heightTotal);
        ImageIcon score = ImageResizer.resize("../resources/img/bonus.png", 100,100);
        layeredPane.add(
                new JLabel("Score : ",score,SwingConstants.CENTER),1,0);
        window.setBounds(0, 0, width * pixelByCase, height * pixelByCase);
        window.add(layeredPane);
        window.setVisible(true);
    }
}
