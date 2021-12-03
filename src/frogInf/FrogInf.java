package frogInf;

import gameCommons.Game;
import gameCommons.IFrog;
import graphicalElements.Element;
import util.Case;
import util.Direction;

import java.awt.*;

import static util.Direction.*;

public class FrogInf implements IFrog
{
	private Case curPosition;
	private Direction curDirection;
	private Game game;

	public FrogInf(Game game) {
		this.curPosition = new Case(game.width / 2, 1);
		this.curDirection = Direction.up;
		this.game = game;
	}

	@Override
	public Case getPosition() {
		return this.curPosition;
	}

	@Override
	public Direction getDirection() {
		return this.curDirection;
	}

	@Override
	public void move(Direction key) {
		this.curDirection = key;
		int width = game.width;
		if (key == Direction.up) {
			this.curPosition = new Case(this.curPosition.absc, this.curPosition.ord + 1);
			++game.score;
			if (this.game.score > this.game.scoreMax) {
				this.game.scoreMax = this.game.score;
				this.game.addLane();
			}
		}
		if (key == Direction.down && this.curPosition.ord > 1) {
			this.curPosition = new Case(this.curPosition.absc, this.curPosition.ord - 1);
			--game.score;
		}
		if (key == Direction.right && this.curPosition.absc < width - 1) {
			this.curPosition = new Case(this.curPosition.absc + 1, this.curPosition.ord);
		}
		if (key == Direction.left && this.curPosition.absc > 0) {
			this.curPosition = new Case(this.curPosition.absc - 1, this.curPosition.ord);
		}
		game.update();
	}
}
