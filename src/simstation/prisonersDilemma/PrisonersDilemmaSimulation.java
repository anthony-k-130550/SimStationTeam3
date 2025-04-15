package simstation.prisonersDilemma;

import mvc.AppPanel;
import simstation.*;
import java.util.*;

public class PrisonersDilemmaSimulation extends World {
    private int averageCheat = 0;
    private int averageCoop = 0;
    private int averageTit4Tat = 0;
    private int averageRandom = 0;

    public PrisonersDilemmaSimulation() { super(); }

    public void populate() {
        for (int i = 0; i < 10; i++)
        {
            Prisoner cheater = new Prisoner();
            Strategy cheat = new Cheat(cheater);
            cheater.setStrategy(cheat);
            addAgent(cheater);

            Prisoner cooperator = new Prisoner();
            Strategy coop = new Cooperate(cooperator);
            cooperator.setStrategy(coop);
            addAgent(cooperator);

            Prisoner tit4Tater = new Prisoner();
            Strategy revenge = new Tit4Tat(tit4Tater);
            tit4Tater.setStrategy(revenge);
            addAgent(tit4Tater);

            Prisoner undecided = new Prisoner();
            Strategy random = new RandomlyCooperate(undecided);
            undecided.setStrategy(random);
            addAgent(undecided);
        }
    }

    public void updateStatistics() {
        super.updateStatistics();
        this.averageCheat = 0;
        this.averageCoop = 0;
        this.averageTit4Tat = 0;
        this.averageRandom = 0;

        for (Agent agent: agents) {
            if (!(agent instanceof Prisoner)) {
                throw new IllegalArgumentException("Agent " + agent + " is not a Prisoner");
            }
            Prisoner p = (Prisoner) agent;
            if (p.getStrategy() instanceof Cheat) {
                this.averageCheat += p.getFitness();
            } else if (p.getStrategy() instanceof Cooperate) {
                this.averageCoop += p.getFitness();
            } else if (p.getStrategy() instanceof Tit4Tat) {
                this.averageTit4Tat += p.getFitness();
            } else if (p.getStrategy() instanceof RandomlyCooperate) {
                this.averageRandom += p.getFitness();
            }
        }
    }

    public String getStatus() {
        this.observer.update();

        return "Clock: " + this.clock
                + ", Alive: " + this.alive
                + ", Agents: " + this.numAgents
                + ", Cheat Score: " + this.averageCheat
                + ", Coop Score: " + this.averageCoop
                + ", Tit4Tat Score: " + this.averageTit4Tat
                + ", Randomly Cooperate Score: " + this.averageRandom;
    }

    public static void main(String[] args) {
        AppPanel panel = new WorldPanel(new PrisonersDilemmaFactory());
        panel.display();
    }
}
