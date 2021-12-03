package graphicalElements;

import caseSpeciale.CaseSpeciale;
import caseSpeciale.Water;
import environment.Car;
import environmentInf.CarInf;
import gameCommons.IFrog;
import gameCommons.Main;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class FroggerGraphic extends JPanel implements IFroggerGraphics, KeyListener {
    private List<List<Element>> elementsToDisplay;
    private List<Element> caseSpecialeToDisplay;
    private int pixelByCase = 25;
    private int width;
    private int height;
    private IFrog frog;
    private IFrog frog2;
    private JFrame frame;

    public FroggerGraphic(int width, int height) {
        this.width = width;
        this.height = height;
        elementsToDisplay = new ArrayList<>(4);
        for (int i = 0 ; i< 4 ; i++)
            elementsToDisplay.add(new ArrayList<>());

        setBackground(Color.GRAY);
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

        for (List<Element> l : elementsToDisplay) {
            for (Element e : l ) {
                g.setColor(e.color);
                g.fillRect(pixelByCase * e.absc, pixelByCase * (height - 1 - e.ord), pixelByCase, pixelByCase - 1);
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
                frog.move(Direction.up);
                break;
            case KeyEvent.VK_DOWN:
                frog.move(Direction.down);
                break;
            case KeyEvent.VK_LEFT:
                frog.move(Direction.left);
                break;
            case KeyEvent.VK_RIGHT:
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
        frame.remove(this);
        JLabel label = new JLabel(s);
        label.setFont(new Font("Verdana", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label);
        frame.repaint();

    }

}
