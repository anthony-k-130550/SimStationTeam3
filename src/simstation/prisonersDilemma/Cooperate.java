package simstation.prisonersDilemma;

public class Cooperate implements Strategy {
    Prisoner myPrisoner;

    public Cooperate(Prisoner prisoner) {
        this.myPrisoner = prisoner;
    }

    public boolean cooperate() {
        // System.out.println(myPrisoner.getName() + " is cooperating (Cooperate strategy)");
        return true;
    }
}
