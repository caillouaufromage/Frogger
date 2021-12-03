package gameCommons;

import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;
import graphicalElements.SoundLoader;
import util.Case;
import util.ElementEnum;
import util.GameMode;

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

	public GameMode mode;

	private double timer;

	// Lien aux objets utilis�s
	private IEnvironment environment;
	private IFrog frog;
	private IFrog frog2;
	private List<IFrog> frogs;
	private final IFroggerGraphics graphic;
    private final ElementEnum frogProps = ElementEnum.FrogPlayer;
    private final String name = frogProps.getName();
    private final int depth = frogProps.getZorder();
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
	public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity, GameMode mode) {
		super();
		this.graphic = graphic;
		this.width = width;
		this.height = height;
		this.minSpeedInTimerLoops = minSpeedInTimerLoop;
		this.defaultDensity = defaultDensity;
		this.timer = System.currentTimeMillis();
		this.mode = mode;

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
		return  ((System.currentTimeMillis() - this.timer)/1000);
	}

	public IFrog getFrog() {
		return frog;
	}

	public IFrog getFrog2(){ return frog2;}



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
			String str = String.format("<html>Player 1 Lose, your score: %d <br/> Time : %.2f <html>", this.scoreMax, getTimer());
			this.graphic.endGameScreen(str);
			Main.timer.stop();
			SoundLoader.sound("../resources/sfx/gameover.wav");
			return true;
		}
		/*if (!this.environment.isSafe(this.frog2.getPosition()) || !frog2.isInBounds()){
			String str = String.format("<html>Player 2 Lose, your score: %d <br/> Time : %.2f <html>", this.scoreMax, getTimer());
			this.graphic.endGameScreen(str);
			Main.timer.stop();
			return true;
		}*/
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
			String str = String.format("<html>Player 1 Win, your score: %d <br/> Time : %.2f <html>", this.scoreMax, getTimer());
			this.graphic.endGameScreen(str);
			Main.timer.stop();
			return true;
		}
		/*if (this.environment.isWinningPosition(this.frog2.getPosition())) {
			String str = String.format("<html>Player 2 Win, your score: %d <br/> Time : %.2f <html>", this.scoreMax, getTimer());
			this.graphic.endGameScreen(str);
			Main.timer.stop();
			return true;
		}*/
		return false;
	}

	/**
	 * Actualise l'environnement, affiche la grenouille et verifie la fin de
	 * partie.
	 */
	public void update() {
		graphic.clear();
		environment.update();
		if (mode == GameMode.Classique)
			this.graphic.add(new Element(frog.getPosition(), name),this.depth);
		else{
			this.graphic.add(new Element(frog.getPosition().absc, 1, name), this.depth);
			if (Main.multiplayer)
				this.graphic.add(new Element(frog2.getPosition().absc, 1, name), this.depth);
		}
		testLose();
		testWin();
	}

	public void addLane(){
		environment.addLane();
	}

}
