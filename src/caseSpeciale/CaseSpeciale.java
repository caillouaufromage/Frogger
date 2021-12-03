package caseSpeciale;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

public class CaseSpeciale extends Element {
    private CaseSpecialeEnum caseSpeciale;
    private int length;
    protected Game game;

    public CaseSpeciale(Game game, int absc, int ord, CaseSpecialeEnum caseSpeciale) {
        super(absc, ord, caseSpeciale.getColor());
        this.caseSpeciale = caseSpeciale;
        this.game = game;
        this.setLengthAndMaxLength();
    }


    private void setLengthAndMaxLength() {
        int maxLength = 0;
        if (this instanceof Wall) {
            maxLength = 4;
            this.length = game.randomGen.nextInt(maxLength) + 1;
        }
        if (this instanceof Slide) {
            maxLength = 3;
            this.length = game.randomGen.nextInt(maxLength) + 1;
        }
        if (this instanceof Water) {this.length = 1;}
        if (this instanceof Bonus) {this.length = 1;}
        if (this instanceof Trap) this.length = 1;
    }

    public CaseSpecialeEnum getCaseSpeciale() {
        return caseSpeciale;
    }

    public void display() {
        this.addToGraphics();
    }

    public boolean isOnCase(Case c) {
        return c.absc >= this.absc && c.absc < this.absc + this.length && c.ord == this.ord;
    }

    private void addToGraphics() {
        if (this instanceof Wall) {
            for (int i = 0; i < length; i++) {
                game.getGraphic()
                        .add(new Wall(game, absc + i, ord - game.score, CaseSpecialeEnum.Wall), 3);
            }
            return;
        }
        if (this instanceof Water) {
            for (int i = 0; i < length; i++) {
                game.getGraphic()
                        .add(new Water(game, absc + i, ord - game.score, CaseSpecialeEnum.Water), 0);
            }
            return;
        }
        if (this instanceof Bonus) {
            for (int i = 0; i < length; i++) {
                game.getGraphic()
                        .add(new Bonus(game, absc + i, ord - game.score, CaseSpecialeEnum.Bonus), 0);
            }
            return;
        }
        if (this instanceof Slide) {
            for (int i = 0; i < length; i++) {
                game.getGraphic()
                        .add(new Slide(game, absc + i, ord - game.score, CaseSpecialeEnum.Slide), 0);
            }
            return;
        }
        if (this instanceof Trap) {
            for (int i = 0; i < length; i++) {
                game.getGraphic()
                        .add(new Trap(game, absc + i, ord - game.score, CaseSpecialeEnum.Trap), 0);
            }
            return;
        }

    }
}
