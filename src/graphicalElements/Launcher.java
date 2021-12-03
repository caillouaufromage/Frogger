package graphicalElements;

import environmentInf.EnvironmentInf;
import frog.Frog;
import frog.FrogInf;
import gameCommons.Game;
import gameCommons.IEnvironment;
import gameCommons.IFrog;
import util.GameMode;

import javax.swing.*;
import java.awt.event.KeyListener;

public class Launcher  extends JFrame {
    private final int  pixelByCase = 32;
    public static Timer timer;
    private int  width;
    private int height;
    public Launcher(int width,int height){

        this.width = width;
        this.height = height;
        LoadingScreen ls = new LoadingScreen(width,height,pixelByCase);
        JPanel froggerGraphics = new FroggerGraphic(width, height-2);

        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(froggerGraphics);
        this.pack();
        this.setVisible(true);
        this.addKeyListener((KeyListener) froggerGraphics);

    }

    public  void init(){
        //Caract�ristiques du jeu

        int tempo = 100;
        int minSpeedInTimerLoops = 3;
        double defaultDensity = 0.04;



        GameMode mode = GameMode.Infini;

        //Cr�ation de l'interface graphique
        IFroggerGraphics graphic = new FroggerGraphic(width, height);
        //Cr�ation de la partie
        Game game = new Game(graphic, width, height, minSpeedInTimerLoops, defaultDensity, mode);
        IFrog frog =  new FrogInf(game);
        IFrog frog2 =  new FrogInf(game);

        switch (mode){
            case Classique:
                frog = new Frog(game);
                break;
            case Infini:
                frog = new FrogInf(game);
                break;
            case InfiniMultiplayer:
                frog = new FrogInf(game);
                frog2 =  new FrogInf(game);
                break;
            case ClassiqueMultiplayer:
                frog = new Frog(game);
                frog2 =  new Frog(game);
                break;
        }

        Sprite.init();
        //Cr�ation et liason de la grenouille
        game.setFrog(frog,frog2);
        graphic.setFrog(frog,frog2);
        IEnvironment env =  new EnvironmentInf(game);
        //Cr�ation et liaison de l'environnement
        game.setEnvironment(env);

        //premiers cars
        for (int i = 0; i < 40; ++i) {
            game.update();
        }
        //Boucle principale : l'environnement s'acturalise tous les tempo milisecondes
        timer = new Timer(tempo, e -> {
            game.update();
            graphic.repaint();
        });
        timer.setInitialDelay(0);
        timer.start();

    }
}
