package environnement.src.environment;

import java.awt.Color;

/* import gameCommons.Case; */
import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Car {
	private Game game;
	private Case leftPosition;
	private boolean leftToRight;
	private int length;
	private final Color colorLtR = Color.BLACK;
	private final Color colorRtL = Color.BLUE;


	/** Constructeur(s) **/

	public Car(Game game, Case beforeFirstCase, boolean leftToRight) {
		this.game = game;
		this.leftPosition = beforeFirstCase;
		this.leftToRight = leftToRight;
	}

	/** Méthodes **/

	public void deplace(int n){ //déplace la voiture de n cases dans le sens de sa voie
		if(this.leftToRight){ this.leftPosition = new Case(this.leftPosition.ord+n, this.leftPosition.absc);
		} else { this.leftPosition = new Case(this.leftPosition.ord-1, this.leftPosition.absc); }
		this.addToGraphics();
	}

	public void supprime(){  //supprime la voiture
		this.deplace(5);
		this.addToGraphics();
	}

	public boolean estHorsJeu(){ //renvoie true si la voiture n'est pas dans les limites du jeu
		if(this.leftPosition.absc >= 0 && this.leftPosition.absc <= this.game.width && this.leftPosition.ord >= 0 && this.leftPosition.ord <= this.game.height) {
			return false;
		}else{
			return true;
		}
	}

	public boolean estSurCase(Case c){ //renvoie true si la voiture est sur la case c
		return this.leftPosition == c;
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
