package simstation.prisonersDilemma;

import mvc.AppPanel;
import simstation.*;
import java.util.*;

public class PrisonersDilemmaSimulation extends World {
    private int averageCheat = 0;
    private int averageCoop = 0;
    private int averageTit4Tat = 0;
    private int averageRandom = 0;

    public PrisonersDilemmaSimulation() {
        super();
    }

    public void populate() {
        for (int i = 0; i < 10; i++) {
            Prisoner cheater = new Prisoner();
            cheater.setStrategy(new Cheat(cheater));
            addAgent(cheater);

            Prisoner cooperator = new Prisoner();
            cooperator.setStrategy(new Cooperate(cooperator));
            addAgent(cooperator);

            Prisoner tit = new Prisoner();
            tit.setStrategy(new Tit4Tat(tit));
            addAgent(tit);

            Prisoner rand = new Prisoner();
            rand.setStrategy(new RandomlyCooperate(rand));
            addAgent(rand);
        }

        // System.out.println("Population created with 4 strategy types.");
    }

    public void updateStatistics() {
        super.updateStatistics();
        averageCheat = averageCoop = averageTit4Tat = averageRandom = 0;

        for (Agent agent : agents) {
            Prisoner p = (Prisoner) agent;
            if (p.getStrategy() instanceof Cheat) {
                averageCheat += p.getFitness();
            } else if (p.getStrategy() instanceof Cooperate) {
                averageCoop += p.getFitness();
            } else if (p.getStrategy() instanceof Tit4Tat) {
                averageTit4Tat += p.getFitness();
            } else if (p.getStrategy() instanceof RandomlyCooperate) {
                averageRandom += p.getFitness();
            }
        }
    }

    public String getStatus() {
        this.observer.update();

        return "Clock: " + this.clock
                + ", Alive: " + this.alive
                + ", Agents: " + this.numAgents
                + ", Cheat Score: " + averageCheat
                + ", Coop Score: " + averageCoop
                + ", Tit4Tat Score: " + averageTit4Tat
                + ", Random Score: " + averageRandom;
    }

    public static void main(String[] args) {
        AppPanel panel = new WorldPanel(new PrisonersDilemmaFactory());
        panel.display();
    }
}
