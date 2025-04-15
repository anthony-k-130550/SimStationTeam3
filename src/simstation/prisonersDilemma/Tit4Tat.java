package simstation.prisonersDilemma;

import mvc.Utilities;

public class Tit4Tat extends Prisoner {

    public boolean cooperate(boolean lastOpponentMove) { return lastOpponentMove; }

    @Override
    public void update() {
        myMove = lastOpponentMove;
        super.update();
    }
}
