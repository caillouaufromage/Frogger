package environnement.src.environment;

import gameCommons.Game;
import util.Case;

import java.util.ArrayList;

public class Lane {
	private Game game;
	private int ord;
	private int speed;
	private ArrayList<Car> cars = new ArrayList<>();
	private boolean leftToRight;
	private double density;
	int chrono;

	/** Constructeur(s) **/

	public Lane(Game game, int ord, int speed, ArrayList<Car> cars, boolean leftToRight, double density) {
		this.game = game;
		this.ord = ord;
		this.speed = speed;
		this.cars = cars;
		this.leftToRight = leftToRight;
		this.density = density;
		this.chrono = 0;
	}

	public void update() {

		// Toutes les voitures se déplacent d'une case au bout d'un nombre "tic
		// d'horloge" égal à leur vitesse
		// Notez que cette méthode est appelée à chaque tic d'horloge

		// Les voitures doivent etre ajoutes a l interface graphique meme quand
		// elle ne bougent pas

		// A chaque tic d'horloge, une voiture peut être ajoutée

		chrono =+ 1;
		if(chrono == this.speed) { // dès que le chrono atteint la vitesse de la Voie, les Voitures avancent de 'this.speed' Cases
			this.deplace();
			//this.supprimeL();
			mayAddCar();
			chrono = 0;
		}
	}

	/** Méthodes **/

	public void deplace(){ //déplace toutes les Voitures de la Lane de this.speed cases
		for(Car c: this.cars)
			c.deplace(this.speed);
	}

	public void supprimeL() { //supprime toutes les voitures de la Lane qui sont hors jeu
		for (Car c : this.cars){
			if (c.estHorsJeu()) {
				c.supprimeC();
			}
		}
	}



	public boolean isSafe(Case c) {
		for (Car car : this.cars)
			if (car.estSurCase(c)) {
				return false;
			}
		return true;
	}
	/*
	 * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase() 
	 */

	/**
	 * Ajoute une voiture au début de la voie avec probabilité égale à la
	 * densité, si la première case de la voie est vide
	 */
	private void mayAddCar() {
		if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
			if (game.randomGen.nextDouble() < density) {
				cars.add(new Car(game, getBeforeFirstCase(), leftToRight));
			}
		}
	}

	private Case getFirstCase() {
		if (leftToRight) {
			return new Case(0, ord);
		} else
			return new Case(game.width - 1, ord);
	}

	private Case getBeforeFirstCase() {
		if (leftToRight) {
			return new Case(-1, ord);
		} else
			return new Case(game.width, ord);
	}

}
