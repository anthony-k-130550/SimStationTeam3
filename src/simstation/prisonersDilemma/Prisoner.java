package simstation.prisonersDilemma;

import simstation.*;

public class Prisoner extends MobileAgent {
    private Strategy strategy;
    private int fitness = 0;
    private boolean lastOpponentMove = true;

    public Prisoner(Strategy strategy) {
        this.strategy = strategy;
    }

    public void interact(Prisoner other) {
        boolean myMove = strategy.cooperate(other.lastOpponentMove);
        boolean theirMove = other.strategy.cooperate(this.lastOpponentMove);

        if (myMove && theirMove) {
            fitness += 3;
            other.fitness += 3;
        } else if (!myMove && !theirMove) {
            fitness += 1;
            other.fitness += 1;
        } else if (!myMove && theirMove) {
            fitness += 5;
        } else {
            other.fitness += 5;
        }

        this.lastOpponentMove = theirMove;
        other.lastOpponentMove = myMove;
    }

    @Override
    public void update() {
        Agent neighbor = world.getNeighbor(this, 20);
        if (neighbor instanceof Prisoner) {
            interact((Prisoner) neighbor);
        }
        move(10);
    }

    public int getFitness() {
        return fitness;
    }

    public String getStrategyName() {
        return strategy.getName();
    }
}
