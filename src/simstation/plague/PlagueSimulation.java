package simstation.plague;

import mvc.*;
import simstation.*;

import java.util.ArrayList;

public class PlagueSimulation extends World
{
    public static int VIRULENCE = 50; // % chance of infection
    public static int RESISTANCE = 2; // % chance of resisting infection
    public static int RECOVERY_TIME = 200; // max 500
    public static int POPULATION_SIZE = 150; // max 200
    public static int INITIAL_INFECTION_PERCENTAGE = 10; // max 100
    private int CURRENT_INFECTED_PERCENTAGE = INITIAL_INFECTION_PERCENTAGE;
    private boolean isFatal = false;

    public PlagueSimulation() { super(); }

    public boolean isFatal() { return isFatal; }

    public void setFatal(boolean isFatal)
    {
        this.isFatal = isFatal;
        for (Agent a : this.agents) // loops through all the agents
        {
            if (this.isFatal && ((Host)a).infected()) // true if an infected agent should be dead after recovery
                ((Host)a).setShouldDie(true);
            else
                ((Host)a).setShouldDie(false);
        }
    }

    @Override
    public void startAgents()
    {
        this.stopAgents();
        this.agents = new ArrayList<Agent>();
        populate();
        for (Agent agent : agents) { agent.start(); }
        observer = new ObserverAgent();
        observer.setWorld(this);
        observer.start();
        this.clock = 0;
    }

    @Override
    public void populate()
    {
        int infectedPopulationSize = (POPULATION_SIZE * INITIAL_INFECTION_PERCENTAGE) / 100;

        for (int i = 0; i < infectedPopulationSize; i++) { addAgent(new Host(this, true)); }

        for (int i = infectedPopulationSize; i < POPULATION_SIZE; i++) { addAgent(new Host(this, false)); }

        this.updateStatistics();
    }

    @Override
    public void updateStatistics()
    {
        this.numAgents = agents.size();
        this.clock++;
        int numOfInfectedAgents = 0;
        this.alive = 0;
        for (Agent agent : agents) // loops through the agents
        {
            if (((Host)agent).infected()) { numOfInfectedAgents++; }
            if (!((Host)agent).isDead()) { this.alive++; }
        }
        if ((POPULATION_SIZE) > 0)
            CURRENT_INFECTED_PERCENTAGE = (numOfInfectedAgents * 100) / POPULATION_SIZE;
        else
            CURRENT_INFECTED_PERCENTAGE = 0;
    }

    @Override
    public String getStatus()
    {
        this.observer.update();
        return "Original Population = " + POPULATION_SIZE
                + "\n# of Living Hosts = " + this.alive
                + "\nClock: " + this.clock
                + "\n% Infected: " + CURRENT_INFECTED_PERCENTAGE;
    }

    public static void main(String[] args)
    {
        AppPanel panel = new PlaguePanel(new PlagueFactory());
        panel.display();
    }
}
