package simstation.prisonersDilemma;

public interface Strategy {
    boolean cooperate(boolean lastOpponentMove);
    String getName();
}
