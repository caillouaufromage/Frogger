package environmentInf;


import caseSpeciale.CaseSpeciale;
import gameCommons.Game;
import util.Case;
import util.ElementEnum;
import util.GameMode;

import java.util.ArrayList;
import java.util.List;

public class Lane {
    private final Game game;
    private final int ord;
    private final int speed;
    private final List<Car> cars = new ArrayList<>();
    private final List<CaseSpeciale> caseSpeciales = new ArrayList<>();
    private final boolean leftToRight;
    private final boolean mustAppearRoads;
    private final boolean mustAppearWater;
    private final ElementEnum[] arrRoad = {ElementEnum.Road, ElementEnum.RoadMid, ElementEnum.RoadBot, ElementEnum.RoadTop};
    private final ElementEnum[] arrWater = {ElementEnum.Water, ElementEnum.Water1, ElementEnum.Water2};
    private double density;
    private int tic;




    public Lane(Game game, int ord, double density) {
        this.game = game;
        this.ord = ord;
        this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops) + 1;
        this.leftToRight = game.randomGen.nextBoolean();
        this.density = density;
        this.mustAppearRoads = this.ord % game.height >= 0 && this.ord % game.height < (game.height / 2) - 1;
        this.mustAppearWater = this.ord % game.height >= (game.height / 2) && this.ord % game.height < game.height - 1;



        for (int i = 0; i < 40; ++i) {
            this.moveCars(true);
            this.mayAddCar();
        }
        if (mustAppearRoads) {
            this.generateRoad();
            this.generateObstacles();
        }
             if (mustAppearWater) {
            this.generateWater();
        }
         if (this.ord % game.height == game.height - 1 || ord % game.height == game.height / 2 - 1) {
            this.density = 0;
            generateBerge();
        }
    }

    public Lane(Game game, int ord) {
        this(game, ord, game.defaultDensity);
    }

    public void generateObstacles() {
        if (ord <= 1 )
            return;
        if (game.randomGen.nextInt(20) == 0) {
            this.caseSpeciales.add(new CaseSpeciale(game, game.randomGen.nextInt(game.width - 1), this.ord, ElementEnum.Trap));
        }
        if (game.randomGen.nextInt(20) == 0) {
            this.caseSpeciales.add(new CaseSpeciale(game, game.randomGen.nextInt(game.width - 1), this.ord, ElementEnum.Wall));
        }
        if (game.randomGen.nextInt(20) == 0) {
            this.caseSpeciales.add(new CaseSpeciale(game, game.randomGen.nextInt(game.width - 3) + 2, this.ord, ElementEnum.Slide));
        }
        if (game.mode == GameMode.Infini && game.randomGen.nextInt(20) == 0) {
            this.caseSpeciales.add(new CaseSpeciale(game, game.randomGen.nextInt(game.width - 1), this.ord, ElementEnum.Bonus));
        }
    }

    public void generateRoad() {
        for (int i = 0; i < game.width; i++) {
            int r = game.randomGen.nextInt(arrRoad.length);
            ElementEnum e = arrRoad[r];
            this.caseSpeciales.add(new CaseSpeciale(game, i, this.ord, e));
        }
    }

    public void generateWater() {
        for (int i = 0; i < game.width; i++) {
            int r = game.randomGen.nextInt(arrWater.length);
            ElementEnum e = arrWater[r];
            this.caseSpeciales.add(new CaseSpeciale(game, i, this.ord, e));
        }
    }

    public void generateBerge() {
        if (this.ord % game.height == game.height - 1) {
            for (int i = 0; i < game.width; i++)
                this.caseSpeciales.add(new CaseSpeciale(game, i, this.ord, ElementEnum.BergeTop));
        } else if (ord == game.height / 2 - 1) {
            for (int i = 0; i < game.width; i++)
                this.caseSpeciales.add(new CaseSpeciale(game, i, this.ord, ElementEnum.BergeBot));
        }
    }

    public void update() {


        // TODO

        // Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
        // d'horloge" �gal � leur vitesse
        // Notez que cette m�thode est appel�e � chaque tic d'horloge

        // Les voitures doivent etre ajoutes a l interface graphique meme quand
        // elle ne bougent pas

        // A chaque tic d'horloge, une voiture peut �tre ajout�e
        ++tic;
        Case frogPos = game.getFrog().getPosition();
        //Case frog2Pos = game.getFrog2().getPosition();
        if (this.tic <= this.speed) {
            this.moveCars(false);
            this.moveCaseSpeciale();
            return;
        }

        this.moveCars(true);
        if (isRondin(frogPos)[0]) {
            if (isRondin(frogPos)[1])
                game.getFrog().setPosition(new Case(frogPos.absc + 1, frogPos.ord));
            if (!isRondin(frogPos)[1]) game.getFrog().setPosition(new Case(frogPos.absc - 1, frogPos.ord));
        }
        /*if (isRondin(frog2Pos)[0] ) {
            if(isRondin(frog2Pos)[1])
                game.getFrog2().setPosition(new Case(frog2Pos.absc + 1, frog2Pos.ord));
            if(!isRondin(frog2Pos)[1]) game.getFrog2().setPosition(new Case(frog2Pos.absc-1, frog2Pos.ord));
        }*/

        this.mayAddCar();
        this.moveCaseSpeciale();
        tic = 0;

    }

    // TODO : ajout de methodes
    private void moveCaseSpeciale() {
        for (CaseSpeciale c : this.caseSpeciales)
            c.display();
    }

    /**
     *
     */
    private void moveCars(boolean bool) {
        for (Car car : this.cars)
            car.move(bool);
        removeCars();
    }

    /**
     * @param c
     * @return
     */
    public boolean isAddBonus(Case c) {
        for (CaseSpeciale cs : caseSpeciales) {
            if (cs.getCaseSpeciale() == ElementEnum.Bonus && cs.isOnCase(c)) {
                caseSpeciales.remove(cs);
                return true;
            }
        }
        return false;
    }

    /**
     * @param c
     * @return
     */
    public boolean canMove(Case c) {
        for (CaseSpeciale cs : caseSpeciales) {
            if (cs.getCaseSpeciale() == ElementEnum.Wall && cs.isOnCase(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param c
     * @return
     */
    public boolean slide(Case c) {
        for (CaseSpeciale cs : caseSpeciales) {
            if (cs.getCaseSpeciale() == ElementEnum.Slide && cs.isOnCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Teste si une case est sure, c'est � dire que la grenouille peut s'y poser sans mourir
     *
     * @param c une case
     * @return true si la case est sure
     */
    private boolean isWater(CaseSpeciale cs){
        return cs.getCaseSpeciale() == ElementEnum.Water || cs.getCaseSpeciale() == ElementEnum.Water1 || cs.getCaseSpeciale() == ElementEnum.Water2;
    }

    public boolean isSafe(Case c) {
        for(Car car : cars)
            if (car.isRondin() && car.isOnCase(c))
                return true;
        for(CaseSpeciale cs : caseSpeciales){
            if ((cs.getCaseSpeciale() == ElementEnum.Trap || isWater(cs) ) && cs.isOnCase(c))
                return false;
        }
        for (Car car : this.cars) {
            if (!car.isRondin() && car.isOnCase(c)) {
                return false;
            }
        }
        return true;

    }
    /**
     * @param c
     * @return
     */
    public boolean[] isRondin(Case c) {
        for (Car car : this.cars)
            if (car.isRondin() && car.isOnCase(c)) {
                return new boolean[]{true, car.isLeftToRight()};
            }
        return new boolean[]{false, false};
    }

    /**
     * @param c
     * @return
     */
    private boolean isThereCar(Case c) {
        for (Car car : this.cars)
            if (car.isOnCase(c)) {
                return false;
            }
        return true;
    }

    /**
     *
     */
    private void removeCars() {
        List<Car> toremove = new ArrayList<>();
        if (!this.cars.isEmpty()) {
            for (Car car : cars) {
                if (!car.isOnScreen())
                    toremove.add(car);
            }
        }
        cars.removeAll(toremove);
    }

    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
     * densit�, si la premi�re case de la voie est vide
     */
    private void mayAddCar() {
        if (isThereCar(getFirstCase()) && isThereCar(getBeforeFirstCase())) {
            if (game.randomGen.nextDouble() < density) {
                if (mustAppearWater)
                    cars.add(new Car(game, getBeforeFirstCase(), leftToRight, true));
                else if (mustAppearRoads)
                    cars.add(new Car(game, getBeforeFirstCase(), leftToRight, false));
            }
        }
    }


    /**
     * @return
     */
    private Case getFirstCase() {
        if (leftToRight) {
            return new Case(0, ord);
        } else
            return new Case(game.width - 1, ord);
    }

    /**
     * @return
     */
    private Case getBeforeFirstCase() {
        if (leftToRight) {
            return new Case(-1, ord);
        } else
            return new Case(game.width, ord);
    }
}
