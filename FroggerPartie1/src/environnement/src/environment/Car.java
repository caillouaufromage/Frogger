package environnement.src.environment;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.BLACK;
	private final Color colorRtL = Color.BLUE;


	/** Constructeur(s) **/

	public Car(Game game, Case c, boolean leftToRight) {
		this.game = game;
		this.leftPosition = c;
		this.leftToRight = leftToRight;
	}
	public Car(Game game, Case c, boolean leftToRight, int length) {
		this.game = game;
		this.leftPosition = c;
		this.length = length;
		this.leftToRight = leftToRight;
	}

	/** Méthodes **/

	public void deplace(int n){ //déplace la voiture de n cases dans le sens de sa voie
		if(this.leftToRight){
			this.leftPosition = new Case(this.leftPosition.ord+n, this.leftPosition.absc);
		} else {
			this.leftPosition = new Case(this.leftPosition.ord-n, this.leftPosition.absc); }
		this.addToGraphics();
	}


	public void supprimeC(){  //supprime la voiture
		this.deplace(5);
		this.addToGraphics();
	}

	public boolean estHorsJeu(){ //renvoie true si la voiture n'est pas dans les limites du jeu
		return (this.leftPosition.absc + this.length > 0 || this.leftPosition.absc + this.length <= this.game.width );
	}

	public boolean estSurCase(Case c){ //renvoie true si la voiture est sur la case c
		return c.absc >= this.leftPosition.absc && c.absc < this.leftPosition.absc + this.length && c.ord == this.leftPosition.ord;
	}
	
	/* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant à la voiture*/
	private void addToGraphics() {
		for (int i = 0; i < length; i++) {
			Color color = colorRtL;
			if (this.leftToRight){
				color = colorLtR;
			}
			game.getGraphic()
					.add(new Element(leftPosition.absc + i, leftPosition.ord, color));
		}
	}

}
