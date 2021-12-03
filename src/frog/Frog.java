package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

import static util.Direction.*;

public class Frog implements IFrog {
	
	private final Game game;
	private Direction curDirection;
	private Case curPosition;

	public Frog(Game game){
		this.game = game;
		this.curDirection = Direction.up;
		this.curPosition = new Case(game.width / 2, 0);
	}

	@Override
	public Case getPosition() {
		return this.curPosition;
	}

	@Override
	public void setPosition(Case curPosition) {

	}

	@Override
	public boolean isInBounds() {
		return true;
	}

	@Override
	public Direction getDirection() {
		return this.curDirection;
	}

	@Override
	public void move(Direction key) {
		this.curDirection = key;
		int height = game.height;
		int width = game.width;
        if(key == up && this.curPosition.ord < height - 1 ){
            this.curPosition = new Case(this.curPosition.absc,this.curPosition.ord+1);
        }
        else if (key == down && this.curPosition.ord > 0 ){
            this.curPosition = new Case(this.curPosition.absc,this.curPosition.ord-1);
        } else if (key == right && this.curPosition.absc < width-1){
            this.curPosition = new Case(this.curPosition.absc+1,this.curPosition.ord);
        }
        else if (key == left && this.curPosition.absc > 0){
            this.curPosition = new Case(this.curPosition.absc-1,this.curPosition.ord);
        }
		this.game.update();
	}
}
