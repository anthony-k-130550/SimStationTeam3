package simstation.prisonersDilemma;

import simstation.*;

public class Prisoner extends MobileAgent {
    protected int fitness = 0;
    protected boolean lastOpponentMove = false;
    protected boolean myMove = false;

    public Prisoner() {
        super();
    }

    public void interact(Prisoner other) {
        //boolean theirMove = other.strategy.cooperate(this.lastOpponentMove);

        /*
        if (myMove && other.myMove) {
            fitness += 3;
            other.fitness += 3;
        } else if (!myMove && !other.myMove) {
            fitness += 1;
            other.fitness += 1;
        } else if (!myMove && other.myMove) {
            fitness += 5;
        } else {
            other.fitness += 5;
        }
         */


        if (myMove && other.myMove) {
            fitness += 3;
            //other.fitness += 3;
        } else if (!myMove && !other.myMove) {
            fitness += 1;
            //other.fitness += 1;
        } else if (!myMove && other.myMove) {
            fitness += 5;
        }

        this.lastOpponentMove = other.myMove;
        //other.lastOpponentMove = myMove;
    }

    @Override
    public void update() {

        Agent neighbor = world.getNeighbor(this, 20);
        if (neighbor instanceof Prisoner) {
            interact((Prisoner) neighbor);
        }
        heading = Heading.random();
        move(10);
    }

    public int getFitness() {
        return fitness;
    }

    /*
    public String getStrategyName() {
        return strategy.getName();
    }
     */
}
