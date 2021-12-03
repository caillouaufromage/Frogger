package graphicalElements;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JPanel {
    public LoadingScreen(int width, int height, int pixelByCase) {

        int widthTotal = width * pixelByCase;
        int heightTotal = height * pixelByCase;
        JWindow frame = new JWindow();
        frame.setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));
        ImageIcon imageIcon = ImageResizer.resize("../resources/backgroundLoading.png", widthTotal,heightTotal);

        frame.getContentPane().add(
                new JLabel("", imageIcon, SwingConstants.CENTER));
        frame.setBounds(0, 0, width * pixelByCase, height * pixelByCase);
        frame.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.setVisible(false);
        frame.dispose();
    }
}
