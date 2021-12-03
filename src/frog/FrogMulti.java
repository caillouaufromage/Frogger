package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class FrogMulti implements IFrog {
    private Case position;
    private Direction direction;
    private Game game;

    public FrogMulti(Game game) {
        this.position = new Case(game.width / 2, 1);
        this.direction = Direction.up;
        this.game = game;
    }

    public Case push(Case c, Direction dir) {
        if (game.push(c)) {
            if (dir == Direction.up) {
                ++game.score;
                return new Case(c.absc, c.ord + 1);
            }
            if (dir == Direction.down && c.ord > 0) {
                --game.score;
                return new Case(c.absc, c.ord - 1);
            }
            if (dir == Direction.right)
                return new Case(c.absc + 1, c.ord);
            if (dir == Direction.left)
                return new Case(c.absc - 1, c.ord);
        }
        return c;

    }

    public boolean isInBounds() {
        return this.position.absc >= 0 && this.position.absc < this.game.width;
    }

    @Override
    public Case getPosition() {
        return this.position;
    }

    public void setPosition(Case curPosition) {
        this.position = curPosition;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    public void addBonus(Case c) {
        if (game.isAddBonus(c))
            game.scoreMax++;
    }

    @Override
    public void move(Direction key) {
        this.direction = key;
        int width = game.width;
        if (key == Direction.up && (game.canMove(new Case(position.absc, position.ord + 1)))) {
            this.position = new Case(this.position.absc, this.position.ord + 1);
            ++game.score;
            if (this.game.score > this.game.scoreMax) {
                this.game.scoreMax = this.game.score;
                this.game.addLane();
            }
        }
        if (key == Direction.down && this.position.ord > 1 && (game.canMove(new Case(position.absc, position.ord - 1)))) {
            this.position = new Case(this.position.absc, this.position.ord - 1);
            --game.score;
        }
        if (key == Direction.right && this.position.absc < width - 1 && (game.canMove(new Case(position.absc + 1, position.ord)))) {
            this.position = new Case(this.position.absc + 1, this.position.ord);
        }
        if (key == Direction.left && this.position.absc > 0 && (game.canMove(new Case(position.absc - 1, position.ord)))) {
            this.position = new Case(this.position.absc - 1, this.position.ord);
        }
        addBonus(position);
        position = this.push(position, key);
        game.update();
    }
}
