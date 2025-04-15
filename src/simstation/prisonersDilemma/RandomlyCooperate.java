package simstation.prisonersDilemma;

import mvc.Utilities;
import simstation.Agent;

public class RandomlyCooperate implements Strategy {
    Prisoner myPrisoner;
    public RandomlyCooperate(Prisoner prisoner) {
        this.myPrisoner = prisoner;
    }
    public boolean cooperate() {
        return Utilities.rng.nextBoolean();
    }
}
