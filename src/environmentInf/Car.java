package environmentInf;

import util.ElementEnum;
import gameCommons.Game;
import graphicalElements.Element;
import util.Case;
import util.GameMode;

import java.awt.*;

public class Car {
    private final Game game;
    private Case leftPosition;
    private final boolean leftToRight;
    private final int length;
    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;
    private final boolean isRondin;
    private ElementEnum cse;
    private String name;
    private int depth;


    //TODO Constructeur(s)
    // TODO : left position
    public Car(Game game, Case frontPosition , boolean leftToRight, boolean isRondin) {

        this.game = game;
        this.leftToRight = leftToRight;
        this.length = isRondin ? game.randomGen.nextInt(4)+3: game.randomGen.nextInt(3)+2;
        this.leftPosition = leftToRight ? new Case(frontPosition.absc-this.length+1, frontPosition.ord) : frontPosition;
        this.isRondin = isRondin;

        if (game.randomGen.nextInt(6) % 2 == 0){
            if (leftToRight)
            cse = isRondin? ElementEnum.NenupharRight : ElementEnum.HerissonRight;
            else cse = isRondin? ElementEnum.NenupharLeft : ElementEnum.HerissonLeft;
        }
        else {
            if(leftToRight) cse = isRondin ? ElementEnum.TurtleRight : ElementEnum.SnakeRight;
            else cse = isRondin ? ElementEnum.TurtleLeft : ElementEnum.SnakeLeft;
        }
        name = cse.getName();
        depth = cse.getZorder();

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
    public boolean isOnScreen(){
        return this.leftPosition.absc + this.length >= -length && this.leftPosition.absc < this.game.width+length ;
    }

    /* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
    private void addToGraphics(){
        for (int i = 0; i < length; i++) {
            game.getGraphic()
                    .add(new Element(leftPosition.absc + i, leftPosition.ord - (game.mode == GameMode.Infini ? game.score : 0) , name),depth);
        }
    }
    public boolean isRondin() {
        return isRondin;
    }

    public boolean isLeftToRight() {
        return leftToRight;
    }
}
