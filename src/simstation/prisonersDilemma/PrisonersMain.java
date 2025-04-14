package simstation.prisonersDilemma;

import simstation.*;
import mvc.*;

public class PrisonersMain {
    public static void main(String[] args) {
        AppPanel panel = new WorldPanel(new PrisonersDilemmaFactory());
        panel.display();
    }
}
