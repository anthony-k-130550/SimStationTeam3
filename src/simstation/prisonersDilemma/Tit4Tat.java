package simstation.prisonersDilemma;

public class Tit4Tat implements Strategy {
    Prisoner myPrisoner;
    public Tit4Tat(Prisoner prisoner) {
        this.myPrisoner = prisoner;
    }
    public boolean cooperate() {
        return !(myPrisoner.getCheated());
    }
}
