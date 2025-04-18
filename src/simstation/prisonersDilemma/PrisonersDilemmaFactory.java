package simstation.prisonersDilemma;

import simstation.*;
import mvc.*;

public class PrisonersDilemmaFactory extends WorldFactory {
    public Model makeModel() {
        return new PrisonersDilemmaSimulation();
    }

    public String getTitle() {
        return "Prisoner's Dilemma";
    }

    public String about() {
        return "Prisoner's Dilemma Tournament Simulation, customized by a student, 2025";
    }

    public String[] getHelp() {
        return new String[] {
            "Start - begins the simulation",
            "Pause - suspends all agents",
            "Resume - resumes agent threads",
            "Stop - halts the simulation",
            "Stats - shows average fitness by strategy"
        };
    }
}
