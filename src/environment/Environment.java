package environment;

import java.util.ArrayList;

import util.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;

public class Environment implements IEnvironment {
		
	//TODO

    private Game game;
    private ArrayList<Lane> lanes;

    public Environment(Game game) {
        lanes = new ArrayList<>();
        this.game = game;
        lanes.add(new Lane(game,0,0)); // première ligne vide pour notre grenouille
        for (int i = 1; i < game.height - 1; i++){
            this.lanes.add(new Lane(game,i));
        }
        this.lanes.add(new Lane(game,game.height-1,0));
    }


    @Override
    public boolean isSafe(Case c) {
        for (Lane lane : this.lanes)
            if (!lane.isSafe(c))
                return false;
        return true;
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return c.ord == this.game.height-1;
    }

    @Override
    public void update() {
        for (Lane lane : lanes)
            lane.update();
    }

    @Override
    public void addLane() {

    }
}
