package environmentInf;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;
import util.GameMode;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentInf implements IEnvironment {
    //TODO
    private final Game game;
    private final List<Lane> lanes;

    public EnvironmentInf(final Game game) {
        this.game = game;
        this.lanes = new ArrayList<>();
        lanes.add(new Lane(game, 0, 0.0));
        this.lanes.add(new Lane(game, 1, 0.0));
        for (int i = 2; i < game.height; i++) {
            this.lanes.add(new Lane(game, i));
        }
    }

    public boolean canMove(Case c) {
        for (Lane lane : this.lanes)
            if (!lane.canMove(c))
                return false;
        return true;
    }

    public boolean slide(Case c) {
        for (Lane lane : this.lanes)
            if (lane.slide(c))
                return true;
        return false;
    }

    public boolean isAddBonus(Case c) {
        for (Lane lane : this.lanes)
            if (lane.isAddBonus(c))
                return true;
        return false;
    }

    public boolean[] isRondin(Case c) {
        for (Lane lane : this.lanes)
            if (lane.isRondin(c)[0])
                return new boolean[]{true, lane.isRondin(c)[1]};
        return new boolean[]{false,false};
    }

    @Override
    public boolean isSafe(Case c) {
        for (Lane lane : this.lanes) {
            if (!lane.isSafe(c))
                return false;
        }
        return true;
    }

    @Override
    public boolean isWinningPosition(Case c) {
        if (game.mode == GameMode.Classique)
            return c.ord == this.game.height-1;
        return false;
    }

    @Override
    public void update() {
        for (Lane lane : lanes)
            lane.update();
    }

    @Override
    public void addLane() {
        this.lanes.add(new Lane(game, this.lanes.size()));
    }
}