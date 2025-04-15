package simstation.prisonersDilemma;

import simstation.Agent;

public class Cheat implements Strategy {
    Prisoner myPrisoner;
    public Cheat(Prisoner prisoner) {
        this.myPrisoner = prisoner;
    }
    public boolean cooperate() { return false; }
}
