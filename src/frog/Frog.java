package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

import static util.Direction.*;

public class Frog implements IFrog {
	
	private final Game game;
	private Direction direction;
	private Case position;

	public Frog(Game game){
		this.game = game;
		this.direction = Direction.up;
		this.position = new Case(game.width / 2, 0);
	}

	public Case push(Case c, Direction dir) {
		if (game.push(c)) {
			if (dir == Direction.up) {
				return new Case(c.absc, c.ord + 1);
			}
			if (dir == Direction.down && c.ord > 0) {
				return new Case(c.absc, c.ord - 1);
			}
			if (dir == Direction.right)
				return new Case(c.absc + 1, c.ord);
			if (dir == Direction.left)
				return new Case(c.absc - 1, c.ord);
		}
		return c;

	}

	@Override
	public Case getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Case curPosition) {
		this.position = curPosition;
	}

	@Override
	public boolean isInBounds() {
		return this.position.absc >= 0 && this.position.absc < this.game.width;
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void move(Direction key) {
		this.direction = key;
		int height = game.height;
		int width = game.width;
        if(key == up && this.position.ord < height - 1 && game.canMove(new Case(position.absc, position.ord + 1))){
            this.position = new Case(this.position.absc,this.position.ord+1);
			game.score++;
        }
        else if (key == down && this.position.ord > 0 && game.canMove(new Case(position.absc, position.ord - 1))){
            this.position = new Case(this.position.absc,this.position.ord-1);
        } else if (key == right && this.position.absc < width-1 && game.canMove(new Case(position.absc+1, position.ord))){
            this.position = new Case(this.position.absc+1,this.position.ord);
        }
        else if (key == left && this.position.absc > 0 && game.canMove(new Case(position.absc-1, position.ord ))){
            this.position = new Case(this.position.absc-1,this.position.ord);
        }
		position = this.push(position, key);
		this.game.update();
	}
}
