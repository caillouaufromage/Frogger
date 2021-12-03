package environmentInf;


import caseSpeciale.*;
import gameCommons.Game;
import util.Case;

import java.util.ArrayList;
import java.util.List;

public class LaneInf {
    private final Game game;
    private final int ord;
    private final int speed;
    private final ArrayList<CarInf> cars = new ArrayList<>();
    private final ArrayList<CaseSpeciale> caseSpeciales = new ArrayList<>();
    private final boolean leftToRight;
    private final double density;
    private int tic;
    private final boolean mustAppear;
    public LaneInf(Game game, int ord, double density) {
        this.game = game;
        this.ord = ord;
        this.speed = game.randomGen.nextInt(game.minSpeedInTimerLoops) + 1;
        this.leftToRight = game.randomGen.nextBoolean();
        this.density = density;
        this.mustAppear = this.ord % this.game.height >= game.height/2 && this.ord % game.height < game.height-1;
        for (int i = 0; i < 40; ++i) {
            this.moveCars(true);
            this.mayAddCar();
        }
        if(!mustAppear)
            this.generateObstacles();
        if (mustAppear) {
            this.generateWater();
        }

    }

    public LaneInf(Game game, int ord) {
        this(game, ord, game.defaultDensity);
    }



    public void generateObstacles(){
        if (this.ord > 1) {
            if (game.randomGen.nextInt(20) == 0) {
                this.caseSpeciales.add(new Trap(game, game.randomGen.nextInt(game.width - 1), this.ord, CaseSpecialeEnum.Trap));
            }
            if (game.randomGen.nextInt(20) == 0) {
                this.caseSpeciales.add(new Wall(game, game.randomGen.nextInt(game.width - 1), this.ord, CaseSpecialeEnum.Wall));
            }
            if (game.randomGen.nextInt(20) == 0) {
                this.caseSpeciales.add(new Slide(game, game.randomGen.nextInt(game.width - 3) + 2, this.ord, CaseSpecialeEnum.Slide));
            }
            if (game.randomGen.nextInt(20) == 0) {
                this.caseSpeciales.add(new Bonus(game, game.randomGen.nextInt(game.width - 1), this.ord, CaseSpecialeEnum.Bonus));
            }
        }
    }

    public void generateWater(){
        for (int i = 0; i < game.width; i++)
            this.caseSpeciales.add(new Water(game,i,this.ord,CaseSpecialeEnum.Water));
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
        if (this.tic <= this.speed) {
            this.moveCars(false);
            this.moveCaseSpeciale();

            return;
        }

        this.moveCars(true);
        if (isRondin(frogPos)[0] ) {
            if(isRondin(frogPos)[1])
                game.getFrog().setPosition(new Case(frogPos.absc + 1, frogPos.ord));
            if(!isRondin(frogPos)[1]) game.getFrog().setPosition(new Case(frogPos.absc-1, frogPos.ord));
        }

        this.mayAddCar();
        this.moveCaseSpeciale();
        tic = 0;

    }

    // TODO : ajout de methodes
    private void moveCaseSpeciale(){
        for (CaseSpeciale c : this.caseSpeciales)
                    c.display();
    }

    /**
     */
    private void moveCars(boolean bool) {
            for (CarInf car : this.cars)
                car.move(bool);
            removeCars();
    }

    /**
     * @param c
     * @return
     */
    public boolean isAddBonus(Case c){
        for(CaseSpeciale cs : caseSpeciales){
            if (cs instanceof Bonus &&  cs.isOnCase(c)) {
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
     public boolean canMove(Case c){
        for (CaseSpeciale cs : caseSpeciales){
            if (cs instanceof Wall) {
                if (((Wall) cs).isOnCase(c)) {
                    return false;
                }
            }
        }
        return true;
     }

    /**
     * @param c
     * @return
     */
     public boolean slide(Case c){
        for(CaseSpeciale cs : caseSpeciales){
            if (cs instanceof Slide && cs.isOnCase(c)) {
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

    public boolean isSafe(Case c) {
        for(CarInf car : cars)
            if (car.isRondin() && car.isOnCase(c))
                return true;
        for(CaseSpeciale cs : caseSpeciales){
            if ((cs instanceof Trap || cs instanceof Water) && cs.isOnCase(c))
                return false;
        }
        for (CarInf car : this.cars) {
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
    public boolean[] isRondin(Case c){
        for (CarInf car : this.cars)
            if (car.isRondin() && car.isOnCase(c) ) {
                return new boolean[]{true, car.isLeftToRight()};
            }
        return new boolean[]{false,false};
    }

    /**
     * @param c
     * @return
     */
    public boolean isThereCar(Case c){
        for (CarInf car : this.cars)
            if (car.isOnCase(c)) {
                return false;
            }
        return true;
    }


    /**
     *
     */
    private void removeCars() {
        List<CarInf> toremove =new ArrayList<>();
        if (!this.cars.isEmpty()) {
            for (CarInf car : cars){
                if (!car.isInBounds())
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
                if(mustAppear)
                    cars.add(new CarInf(game, getBeforeFirstCase(), leftToRight,true));
                else
                    cars.add(new CarInf(game, getBeforeFirstCase(), leftToRight,false));
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
