package simstation.prisonersDilemma;

import mvc.Utilities;

public class RandomlyCooperate implements Strategy {
    Prisoner myPrisoner;

    public RandomlyCooperate(Prisoner prisoner) {
        this.myPrisoner = prisoner;
    }

    public boolean cooperate() {
        boolean choice = Utilities.rng.nextBoolean();
        // System.out.println(myPrisoner.getName() + " randomly chose to " + (choice ? "Cooperate" : "Cheat"));
        return choice;
    }
}
