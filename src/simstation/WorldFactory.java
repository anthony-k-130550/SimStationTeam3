package simstation;

import mvc.*;

public class WorldFactory implements AppFactory
{
    @Override
    public Model makeModel() { return new World(); }

    @Override
    public View makeView(Model model) { return new WorldView(model); }

    @Override
    public String[] getEditCommands() { return new String[] {"", "decTemp"}; }

    @Override
    public Command makeEditCommand(Model model, String type, Object source)
    {
        if (type.equalsIgnoreCase("Start")) return new StartCommand(model);
        if (type.equalsIgnoreCase("Pause")) return new SuspendCommand(model);
        if (type.equalsIgnoreCase("Resume")) return new ResumeCommand(model);
        if (type.equalsIgnoreCase("Stop")) return new StopCommand(model);
        if (type.equalsIgnoreCase("Stats")) return new StatsCommand(model);
        return null;
    }

    @Override
    public String getTitle() { return "Part 1: Simstation"; }

    @Override
    public String[] getHelp()
    {
        return new String[] {"Start - starts the simulation",
                "Pause - suspends the simulation",
                "Resume - continues the program from a pause",
                "Stop - discontinues the simulation",
                "Stats - displays current statistics of the simulation" };
    }

    @Override
    public String about()
    {
        return "Anthony Kieu, Vasudev Vinod, Tyler Fabela\n" +
            "Assignment 4 Part 1: Simstation" + "14 April 2025";
    }
}
