package simstation.prisonersdilemma;

import simstation.*;
import java.util.*;

public class PrisonersDilemmaSimulation extends World {

    public void populate() {
        for (int i = 0; i < 25; i++) addAgent(new Prisoner(new AlwaysCooperate()));
        for (int i = 0; i < 25; i++) addAgent(new Prisoner(new AlwaysDefect()));
        for (int i = 0; i < 25; i++) addAgent(new Prisoner(new TitForTat()));
        for (int i = 0; i < 25; i++) addAgent(new Prisoner(new RandomStrategy()));
    }

    public void updateStatistics() {
        this.clock++;
        this.numAgents = agents.size();
        this.alive = agents.size();
    }

    public String getStatus() {
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
    }
}
