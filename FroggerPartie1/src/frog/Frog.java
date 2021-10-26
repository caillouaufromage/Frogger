package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {
	private Case position;
	private Direction direction;

	private Game game;

	public Frog(Game game) {
		this.game = game;
		this.position = new Case(game.width/2, 0);
		this.direction = Direction.up;

	}


	@Override
	public Case getPosition(){ return this.position; }

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void move(Direction key) {
		if(key == Direction.up && this.position.ord <= game.height){
			this.position = new Case(this.position.absc, this.position.ord+1) ;
		}else if(key == Direction.down && this.position.ord !=0){
			this.position = new Case(this.position.absc, this.position.ord-1) ;
		}else if(key == Direction.left && this.position.absc !=0){
			this.position = new Case(this.position.absc-1, this.position.ord) ;
		}else if(key == Direction.right && this.position.absc <= game.width){
			this.position = new Case(this.position.absc+1, this.position.ord) ;
		}
	}


}
