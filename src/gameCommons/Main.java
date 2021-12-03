package gameCommons;

import environmentInf.EnvironmentInf;
import frog.Frog;
import frog.FrogInf;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import graphicalElements.Sprite;
import util.GameMode;

import javax.swing.*;

public class Main {

	public static Timer timer;
	public static boolean multiplayer = false;


	public static void main(String[] args) {

		//Caract�ristiques du jeu
		int width = 26;
		int height = 20;
		int tempo = 100;
		int minSpeedInTimerLoops = 3;
		double defaultDensity = 0.10;



		GameMode mode = GameMode.Classique;

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

	public static void mainCaller(){
		main(null);
	}
}
