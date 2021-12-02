package environnement.src.environment;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;

import java.util.ArrayList;

public class Environment implements IEnvironment {
    Game game;
    ArrayList<Lane>voies = new ArrayList<Lane>();

    /** Constructeur(s) **/

    public Environment(Game game) {
        this.game = game;
    }

    /** Méthodes **/

    @Override
    public boolean isSafe(Case c) {  // renvoie true si la case c de l'environnement est sûr
        for(Lane l : voies){
            if(l.isSafe(c) == false){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isWinningPosition(Case c) { //renvoie true si le joueur se trouve sur la ligne d'arrivée
        return c.ord == this.game.height-1;
    }

    @Override
    public void update() { //met l'environnement à jour
        for (Lane l : voies)
            l.update();
    }
}