package environment;

import java.awt.Color;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Car {
	private final Game game;
	private Case leftPosition;
	private final boolean leftToRight;
	private final int length;
	private final Color colorLtR = Color.BLACK;
	private final Color colorRtL = Color.BLUE;

	//TODO Constructeur(s)
	// TODO : left position
	public Car(Game game, Case frontPosition ,boolean leftToRight) {
		this.game = game;
		this.leftToRight = leftToRight;
		this.length = game.randomGen.nextInt(3)+1;
		this.leftPosition = leftToRight ? new Case(frontPosition.absc-this.length, frontPosition.ord) : frontPosition;
	}


	//TODO : ajout de methodes
	// TODO : Condition pour savoir si elle peut bouger ?

	public void move(boolean bool){
		if(bool)
			this.leftPosition = new Case(this.leftPosition.absc + (this.leftToRight ? 1 : -1), this.leftPosition.ord );
		this.addToGraphics();
	}

	/**
	 * Test si la voiture est sur une case donnée
	 * @param c une case
	 * @return true si la voiture est sur cette case
	 */
	public boolean isOnCase(Case c){
		return c.absc >= this.leftPosition.absc && c.absc < this.leftPosition.absc + this.length && c.ord == this.leftPosition.ord;
	}


	public boolean isInBounds(){
		return this.leftPosition.absc + this.length > 0 || this.leftPosition.absc < this.game.width ;
	}

	/* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
	private void addToGraphics(){
		for (int i = 0; i < length; i++) {
			Color color = colorRtL;
			if (this.leftToRight){
				color = colorLtR;
			}
			game.getGraphic()
					.add(new Element(leftPosition.absc + i, leftPosition.ord, color),1);
		}
	}


}
