package simstation.prisonersDilemma;

import simstation.*;

public class Prisoner extends MobileAgent {
    private int fitness = 0;
    private boolean partnerCheated = false;
    private Strategy strategy;

    public Prisoner() {
        super();
    }

    // Main interaction logic between two prisoners
    public void interact(Prisoner other) {
        boolean myMove = this.cooperate();
        boolean theirMove = other.cooperate();

        // Debug output (optional)
        // System.out.println(getName() + " (" + strategy.getClass().getSimpleName() + ") vs " +
        //     other.getName() + " (" + other.strategy.getClass().getSimpleName() + ")");

        // Payoff matrix logic
        if (myMove && theirMove) {
            addFitness(3);
            other.addFitness(3);
        } else if (myMove && !theirMove) {
            other.addFitness(5);
        } else if (!myMove && theirMove) {
            addFitness(5);
        } else {
            addFitness(1);
            other.addFitness(1);
        }

        // Track if opponent betrayed us (used by Tit-for-Tat)
        partnerCheated = !theirMove;
    }

    // Determines whether this prisoner will cooperate
    public boolean cooperate() {
        // System.out.println(getName() + " using strategy: " + strategy.getClass().getSimpleName());
        return strategy.cooperate();
    }

    // Called every tick in the simulation
    @Override
    public void update() {
        Agent neighbor = world.getNeighbor(this, 20);
        if (neighbor instanceof Prisoner) {
            interact((Prisoner) neighbor);
        }

        heading = Heading.random();
        move(10);
    }

    // Utility methods

    public int getFitness() {
        return fitness;
    }

    public void addFitness(int amount) {
        fitness += amount;
        // System.out.println(getName() + " gained " + amount + " pts. Total: " + fitness);
    }

    public boolean getPartnerCheated() {
        return partnerCheated;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }
}
