package simstation.prisonersdilemma;

import mvc.Utilities;

public class RandomStrategy implements Strategy {
    public boolean cooperate(boolean lastOpponentMove) {
        return Utilities.rng.nextBoolean();
    }
    public String getName() { return "Random"; }
}
