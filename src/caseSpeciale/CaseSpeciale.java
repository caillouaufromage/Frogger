package caseSpeciale;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;
import util.ElementEnum;
import util.GameMode;

public class CaseSpeciale extends Element {
    private final ElementEnum caseSpeciale;
    private int length;
    protected Game game;
    private final int zorder;

    public CaseSpeciale(Game game, int absc, int ord, ElementEnum caseSpeciale) {
        super(absc, ord, caseSpeciale.getName());
        this.caseSpeciale = caseSpeciale;
        this.game = game;
        this.setLengthAndMaxLength();
        this.zorder = caseSpeciale.getZorder();
    }


    private void setLengthAndMaxLength() {
        int maxLength;
        switch (this.caseSpeciale){
            case Wall:
                maxLength = 4;
                this.length = game.randomGen.nextInt(maxLength) + 1;
                break;
            default:
                this.length = 1;
        }
    }

    public ElementEnum getCaseSpeciale() {
        return caseSpeciale;
    }

    public void display() {
        this.addToGraphics();
    }

    public boolean isOnCase(Case c) {
        return c.absc >= this.absc && c.absc < this.absc + this.length && c.ord == this.ord;
    }

    private void addToGraphics() {
        for (int i = 0; i < length; i++)
            game.getGraphic().add(new CaseSpeciale(game, absc + i, ord - (game.mode == GameMode.Infini ? game.score : 0), this.caseSpeciale), zorder);
    }
}
