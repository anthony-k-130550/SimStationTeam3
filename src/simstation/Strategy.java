package simstation.prisonersdilemma;

public interface Strategy {
    boolean cooperate(boolean lastOpponentMove);
    String getName();
}
