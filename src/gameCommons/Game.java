package gameCommons;

import frog.Frog;
import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;
import util.Case;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game {

	public final Random randomGen = new Random();

	// Caracteristique de la partie
	public final int width;
	public final int height;
	public final int minSpeedInTimerLoops;
	public final double defaultDensity;
	public int score;
	public int scoreMax;

	private double timer;

	// Lien aux objets utilis�s
	private IEnvironment environment;
	private IFrog frog;
	private IFrog frog2;
	private List<IFrog> frogs;
	private final IFroggerGraphics graphic;

	/**
	 * 
	 * @param graphic
	 *            l'interface graphique
	 * @param width
	 *            largeur en cases
	 * @param height
	 *            hauteur en cases
	 * @param minSpeedInTimerLoop
	 *            Vitesse minimale, en nombre de tour de timer avant d�placement
	 * @param defaultDensity
	 *            densite de voiture utilisee par defaut pour les routes
	 */
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
		this.timer = System.currentTimeMillis();
	}


	public void addFrog(IFrog frog){
		frogs.add(frog);
	}
	/**
	 * Lie l'objet frog � la partie
	 * 
	 * @param frog que l'on souhaite lier à la partie
	 */
	public void setFrog(IFrog frog, IFrog frog2) {
		this.frog = frog;
		if (Main.multiplayer){
			this.frog2 = frog2;
		}
	}

	/**
	 * Lie l'objet environment a la partie
	 * 
	 * @param environment que l'on souhouaite lier à la partie
	 */
	public void setEnvironment(IEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 * @return l'interface graphique
	 */
	public IFroggerGraphics getGraphic() {
		return graphic;
	}

	private double getTimer(){
		return  ((System.currentTimeMillis() - this.timer)/1000)%60;
	}

	public IFrog getFrog() {
		return frog;
	}



	public boolean canMove(Case c){
		return environment.canMove(c);
	}

	public boolean push(Case c){
		return environment.slide(c);
	}

	public boolean isAddBonus(Case c){return environment.isAddBonus(c) ;}

	/**
	 * Teste si la partie est perdue et lance un �cran de fin appropri� si tel
	 * est le cas
	 *
	 * @return true si le partie est perdue
	 */
	public boolean testLose() {
		if (!this.environment.isSafe(this.frog.getPosition()) || !frog.isInBounds()){
			String str = String.format("<html>You Lose, your score: %d <br/> Time : %.2f <html>", this.scoreMax, getTimer());
			this.graphic.endGameScreen(str);
			Main.timer.stop();
			return true;
		}
		return false;
	}

	/**
	 * Teste si la partie est gagnee et lance un �cran de fin appropri� si tel
	 * est le cas
	 * 
	 * @return true si la partie est gagn�e
	 */
	public boolean testWin() {
		if (this.environment.isWinningPosition(this.frog.getPosition())) {
			String str = String.format("<html>You Win, your score: %d <br/> Time : %.2f <html>", this.scoreMax, getTimer());
			this.graphic.endGameScreen(str);
			Main.timer.stop();
			return true;
		}
		return false;
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {
		graphic.clear();
		environment.update();
		//this.graphic.add(new Element(frog.getPosition(), Color.GREEN));
		this.graphic.add(new Element(frog.getPosition().absc,1, Color.GREEN),3);
        if (Main.multiplayer)
		    for (IFrog f : frogs)
				this.graphic.add(new Element(f.getPosition().absc, 1, Color.GREEN),3);
		testLose();
		testWin();
	}

	public void addLane(){
		environment.addLane();
	}

}
