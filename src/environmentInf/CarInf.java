package environmentInf;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public class CarInf {
    private final Game game;
    private Case leftPosition;
    private final boolean leftToRight;
    private final int length;
    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;
    private final boolean isRondin;


    //TODO Constructeur(s)
    // TODO : left position
    public CarInf(Game game, Case frontPosition ,boolean leftToRight, boolean isRondin) {
        this.game = game;
        this.leftToRight = leftToRight;
        this.length = isRondin ? game.randomGen.nextInt(4)+3: game.randomGen.nextInt(3)+2;
        this.leftPosition = leftToRight ? new Case(frontPosition.absc-this.length+1, frontPosition.ord) : frontPosition;
        this.isRondin = isRondin;
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
        if (isRondin)
            return leftToRight ? c.absc >= this.leftPosition.absc-1 && c.absc < this.leftPosition.absc + this.length && c.ord == this.leftPosition.ord
                    : c.absc >= this.leftPosition.absc && c.absc <= this.leftPosition.absc + this.length && c.ord == this.leftPosition.ord;
        return c.absc >= this.leftPosition.absc && c.absc < this.leftPosition.absc + this.length && c.ord == this.leftPosition.ord;
    }

    /**
     * Test si la voiture est dans l'écran
     * @return true si la voiture est dans l'écran
     */
    public boolean isInBounds(){
        return this.leftPosition.absc + this.length >= -length && this.leftPosition.absc < this.game.width+length ;
    }

    /* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
    private void addToGraphics(){
        for (int i = 0; i < length; i++) {
            Color color = isRondin ? Color.cyan : colorRtL;
            if (this.leftToRight){
                color = isRondin? Color.WHITE : colorLtR;
            }
            game.getGraphic()
                    .add(new Element(leftPosition.absc + i, leftPosition.ord - this.game.score, color),1);
        }
    }
    public boolean isRondin() {
        return isRondin;
    }

    public boolean isLeftToRight() {
        return leftToRight;
    }
}
