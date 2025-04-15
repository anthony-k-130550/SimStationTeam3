package simstation.prisonersDilemma;

import simstation.*;

public class Prisoner extends MobileAgent {
    protected int fitness = 0;
    protected boolean cheated = false;
    Strategy strategy;

    public Prisoner()
    {
        super();
    }

    public void interact(Prisoner other) {
        boolean myMove = this.cooperate();
        boolean otherMove = other.cooperate();

        if (myMove && otherMove) {
            this.updateFitness(3);
            other.updateFitness(3);
        } else if (myMove && !otherMove) {
            other.updateFitness(5);
        } else if (!myMove && otherMove) {
            this.updateFitness(5);
        } else { //the only other case is both chose to cheat
            this.updateFitness(1);
            other.updateFitness(1);
        }
        cheated = !otherMove; //if otherMove is false, that means the other cheated, which we then remember for this agent
    }

    public boolean cooperate() {
        return strategy.cooperate();
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

    public void updateFitness(int amt) {
        fitness += amt;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public boolean getCheated() {
        return cheated;
    }
}
