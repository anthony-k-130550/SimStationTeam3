package simstation.prisonersdilemma;

public class TitForTat implements Strategy {
    public boolean cooperate(boolean lastOpponentMove) { return lastOpponentMove; }
    public String getName() { return "Tit For Tat"; }
}
