package environmentInf;

import util.Case;
import gameCommons.Game;

import java.util.ArrayList;

import gameCommons.IEnvironment;

public class EnvironmentInf implements IEnvironment {
    //TODO

    private final Game game;
    private ArrayList<LaneInf> lanes;

    public EnvironmentInf(final Game game) {
        this.game = game;
        this.lanes = new ArrayList<>();
        lanes.add(new LaneInf(game, 0, 0.0));
        this.lanes.add(new LaneInf(game, 1, 0.0));
        for (int i = 2; i < game.height; i++) {
            this.lanes.add(new LaneInf(game, i));
        }
    }

    public boolean canMove(Case c) {
        for (LaneInf lane : this.lanes)
            if (!lane.canMove(c))
                return false;
        return true;
    }

    public boolean slide(Case c) {
        for (LaneInf lane : this.lanes)
            if (lane.slide(c))
                return true;
        return false;
    }

    public boolean isAddBonus(Case c) {
        for (LaneInf lane : this.lanes)
            if (lane.isAddBonus(c))
                return true;
        return false;
    }

    public boolean[] isRondin(Case c) {
        for (LaneInf lane : this.lanes)
            if (lane.isRondin(c)[0])
                return new boolean[]{true, lane.isRondin(c)[1]};
        return new boolean[]{false,false};
    }

    @Override
    public boolean isSafe(Case c) {

        for (LaneInf lane : this.lanes) {
            if (!lane.isSafe(c))
                return false;

        }
        return true;
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return false;
    }

    @Override
    public void update() {
        for (LaneInf lane : lanes)
            lane.update();
    }

    @Override
    public void addLane() {
        this.lanes.add(new LaneInf(game, this.lanes.size()));
    }
}