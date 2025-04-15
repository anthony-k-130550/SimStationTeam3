package simstation.prisonersDilemma;

import mvc.Utilities;
import simstation.Agent;

public class RandomlyCooperate extends Prisoner {

    public boolean cooperate(boolean lastOpponentMove) {
        return Utilities.rng.nextBoolean();
    }

    @Override
    public void update() {
        myMove = Utilities.rng.nextBoolean();
        super.update();
    }
}
