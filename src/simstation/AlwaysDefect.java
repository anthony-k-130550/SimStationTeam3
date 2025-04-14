package simstation.prisonersdilemma;

public class AlwaysDefect implements Strategy {
    public boolean cooperate(boolean lastOpponentMove) { return false; }
    public String getName() { return "Always Defect"; }
}
