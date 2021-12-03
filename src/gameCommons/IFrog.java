package gameCommons;

import util.Case;
import util.Direction;

public interface IFrog {
	
	/**
	 * Donne la position actuelle de la grenouille
	 * @return la case où se trouve la grenouille
	 */
	Case getPosition();
	
	/**
	 * Donne la direction de la grenouille, c'est � dire de son dernier mouvement 
	 * @return la direction de la grenouille
	 */
	Direction getDirection();
	
	/**
	 * D�place la grenouille dans la direction donn�e et teste la fin de partie
	 * @param key change la direction en fonction de la touche appuyée
	 */
	void move(Direction key);

}
