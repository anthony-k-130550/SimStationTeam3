package simstation.prisonersDilemma;

public class Cheat implements Strategy {
    Prisoner myPrisoner;

    public Cheat(Prisoner prisoner) {
        this.myPrisoner = prisoner;
    }

    public boolean cooperate() {
        // System.out.println(myPrisoner.getName() + " is cheating (Cheat strategy)");
        return false;
    }
}
