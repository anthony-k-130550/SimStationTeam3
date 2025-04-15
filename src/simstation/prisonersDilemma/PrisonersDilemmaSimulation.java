package simstation.prisonersDilemma;

import mvc.AppPanel;
import simstation.*;
import java.util.*;

public class PrisonersDilemmaSimulation extends World {

    private int averageStats = 0;

    public PrisonersDilemmaSimulation() { super(); }

    public void populate() {
        for (int i = 0; i < 10; i++)
        {
            addAgent(new Cooperate());
            addAgent(new Cheat());
            addAgent(new Tit4Tat());
            addAgent(new RandomlyCooperate());
        }
    }

    public void updateStatistics() {
        super.updateStatistics();
        this.averageStats = 0;
        for (int i = 0; i < this.numAgents; i++)
            this.averageStats += ((Prisoner)this.agents.get(i)).getFitness();
        this.averageStats /= this.numAgents;
    }

    public String getStatus() {
        /*
        Map<String, List<Integer>> stats = new HashMap<>();
        for (Agent a : agents) {
            Prisoner p = (Prisoner) a;
            stats.computeIfAbsent(p.getStrategyName(), k -> new ArrayList<>()).add(p.getFitness());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Clock: ").append(clock).append(", Agents: ").append(numAgents).append("\n");

        for (String s : stats.keySet()) {
            List<Integer> scores = stats.get(s);
            double avg = scores.stream().mapToInt(i -> i).average().orElse(0.0);
            sb.append(s).append(" Avg Fitness: ").append(String.format("%.2f", avg)).append("\n");
        }

        return sb.toString();

         */

        this.observer.update();

        return "Clock: " + this.clock
                + ", Alive: " + this.alive
                + ", Agents: " + this.numAgents
                + ", Average Fitness: " + this.averageStats;
    }

    public static void main(String[] args) {
        AppPanel panel = new WorldPanel(new PrisonersDilemmaFactory());
        panel.display();
    }
}
