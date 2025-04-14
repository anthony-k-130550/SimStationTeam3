package simstation.prisonersDilemma;

public class AlwaysCooperate implements Strategy {
    public boolean cooperate(boolean lastOpponentMove) { return true; }
    public String getName() { return "Always Cooperate"; }
}
