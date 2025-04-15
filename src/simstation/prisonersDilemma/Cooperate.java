package simstation.prisonersDilemma;

public class Cooperate implements Strategy {
    Prisoner myPrisoner;
    public Cooperate(Prisoner prisoner) {
        this.myPrisoner = prisoner;
    }
    public boolean cooperate() { return true; }
    //public String getName() { return "Always Cooperate"; }
}
