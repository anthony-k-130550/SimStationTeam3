package simstation.prisonersDilemma;

import simstation.Agent;

public class Cheat extends Prisoner {

    public boolean cooperate(boolean lastOpponentMove) { return false; }
    //public String getName() { return "Always Defect"; }
}
