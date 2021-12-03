package environmentInf;


import environment.Car;
import gameCommons.Game;
import util.Case;

import java.util.ArrayList;

public class LaneInf {
    private final Game game;
    private final int ord;
    private final int speed;
    private final ArrayList<CarInf> cars = new ArrayList<>();
    private final boolean leftToRight;
    private final double density;
    private int timer;

    public LaneInf(Game game, int ord, double density) {
        this.game = game;
        this.ord = ord;
        this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops) + 1;
        this.leftToRight = game.randomGen.nextBoolean();
        this.density = density;
        for (int i = 0; i < 40; ++i) {
            this.moveCars(true);
            this.mayAddCar();
        }
    }

    public LaneInf(Game game, int ord) {
        this(game, ord, game.defaultDensity);
    }

    public void update() {

        // TODO

        // Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
        // d'horloge" �gal � leur vitesse
        // Notez que cette m�thode est appel�e � chaque tic d'horloge

        // Les voitures doivent etre ajoutes a l interface graphique meme quand
        // elle ne bougent pas

        // A chaque tic d'horloge, une voiture peut �tre ajout�e
        ++timer;
        if (this.timer <= this.speed) {
            this.moveCars(false);
            return;
        }
        this.moveCars(true);
        mayAddCar();
        timer = 0;

    }


    // TODO : ajout de methodes

    private void moveCars(boolean bool) {
        for (CarInf car : this.cars)
            car.move(bool);
        removeCars();
    }

    /**
     * Teste si une case est sure, c'est � dire que la grenouille peut s'y poser sans mourir
     *
     * @param c une case
     * @return true si la case est sure
     */

    public boolean isSafe(Case c) {
        for (CarInf car : this.cars)
            if (car.isOnCase(c)) {
                return false;
            }
        return true;
    }

    private void removeCars() {
        if (!this.cars.isEmpty())
            this.cars.removeIf(car -> !car.isInBounds());
    }
    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
     * densit�, si la premi�re case de la voie est vide
     */
    private void mayAddCar() {
        if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
            if (game.randomGen.nextDouble() < density) {
                cars.add(new CarInf(game, getBeforeFirstCase(), leftToRight));
            }
        }
    }

    private Case getFirstCase() {
        if (leftToRight) {
            return new Case(0, ord);
        } else
            return new Case(game.width - 1, ord);
    }

    private Case getBeforeFirstCase() {
        if (leftToRight) {
            return new Case(-1, ord);
        } else
            return new Case(game.width, ord);
    }
}
