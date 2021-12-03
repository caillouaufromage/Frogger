package graphicalElements;

import gameCommons.IFrog;
import gameCommons.Main;
import util.Direction;

import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
    private final List<List<Element>> elementsToDisplay;
    private final int pixelByCase = 32;
    private final int height;
    private final int width;
    private IFrog frog;
    private IFrog frog2;
    private final JFrame frame;

    static boolean replay = false;

    AudioSystem BGM;

    ImageIcon restartIcon = new ImageIcon(ImageLoader.loadImage("../resources/restart.png"));
    ImageIcon exitIcon = new ImageIcon(ImageLoader.loadImage("../resources/exit.png"));



    public FroggerGraphic(int width, int height) {
        this.height = height;
        this.width = width;
        elementsToDisplay = Collections.synchronizedList(new ArrayList<>(4)) ;
        for (int i = 0 ; i< 4 ; i++)
            elementsToDisplay.add(new ArrayList<>());
        if (!replay) {
            LoadingScreen ls = new LoadingScreen(width, height, pixelByCase);
        }
        setBackground(Color.gray);
        setPreferredSize(new Dimension(width * pixelByCase, height * pixelByCase));
        JFrame frame = new JFrame("Frogger");
        this.frame = frame;
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);

        frame.addKeyListener(this);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        synchronized(elementsToDisplay) {
            for (List<Element> l : elementsToDisplay) {
                for (Element e : l ) {
                    g.drawImage(e.image,pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1,this);
                }
            }
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (!Main.timer.isRunning())
            return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                SoundLoader.sound("../resources/sfx/hop.wav");
                frog.move(Direction.up);
                break;
            case KeyEvent.VK_DOWN:
                SoundLoader.sound("../resources/sfx/hop.wav");
                frog.move(Direction.down);
                break;
            case KeyEvent.VK_LEFT:
                SoundLoader.sound("../resources/sfx/hop.wav");
                frog.move(Direction.left);
                break;
            case KeyEvent.VK_RIGHT:
                SoundLoader.sound("../resources/sfx/hop.wav");
                frog.move(Direction.right);
                break;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Z:
                frog2.move(Direction.up);
                break;
            case KeyEvent.VK_S:
                frog2.move(Direction.down);
                break;
            case KeyEvent.VK_Q:
                frog2.move(Direction.left);
                break;
            case KeyEvent.VK_D:
                frog2.move(Direction.right);
                break;
        }
    }


    public void clear() {
        for (List<Element> elements : elementsToDisplay) elements.clear();
    }

    public void add(Element e, int i) {
        (this.elementsToDisplay.get(i)).add(e);
    }

    public void setFrog(IFrog frog, IFrog frog2) {
        this.frog = frog;
        if(Main.multiplayer)
            this.frog2 = frog2;
    }

    public void endGameScreen(String s) {
        replay = true;
        frame.remove(this);
        //EndgameScreen egs = new EndgameScreen(width, height, pixelByCase);
        JLabel label = new JLabel(s);
        label.setFont(new Font("Verdana", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        label.setBackground(Color.CYAN);

        JButton restart = new JButton(restartIcon);
        restart.setVisible(true);
        restart.setSize(restartIcon.getIconWidth(),restartIcon.getIconHeight());

        restart.addActionListener(actionEvent -> {
            frame.dispose(); // close window
            frame.setVisible(false); // hide window
            Main.mainCaller();});
        label.add(restart);

        JButton quit = new JButton(exitIcon);
        quit.setVisible(true);
        quit.setLocation(0,100);
        quit.setSize(exitIcon.getIconWidth(),exitIcon.getIconHeight());

        quit.addActionListener(actionEvent -> {
            System.exit(0);});
        label.add(quit);

        frame.getRootPane().setDefaultButton(restart);
        frame.getContentPane().add(label);
        frame.repaint();

    }


}
