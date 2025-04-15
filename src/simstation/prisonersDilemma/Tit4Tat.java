package simstation.prisonersDilemma;

public class Tit4Tat implements Strategy {
    Prisoner myPrisoner;

    public Tit4Tat(Prisoner prisoner) {
        this.myPrisoner = prisoner;
    }

    public boolean cooperate() {
        boolean coop = !myPrisoner.getCheated();
        // System.out.println(myPrisoner.getName() + " using Tit4Tat: " + (coop ? "Cooperate" : "Cheat"));
        return coop;
    }
}
