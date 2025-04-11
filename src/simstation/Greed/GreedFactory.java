package simstation.Greed;
import simstation.*;
import mvc.*;

public class GreedFactory extends WorldFactory{
    public Model makeModel() {
        return new Meadow();
    }

    public View makeView(Model model) { return new MeadowView(model); }

    public String getTitle() {
        return "Greed";
    }

    public String[] getEditCommands() { return new String[] {"Start", "Pause", "Resume", "Stop", "Stats", "Greed", "Grow Back", "Move Energy"}; }

    public Command makeEditCommand(Model model, String type, Object source)
    {
        if (type.equalsIgnoreCase("Start")) return new StartCommand(model);
        if (type.equalsIgnoreCase("Pause")) return new SuspendCommand(model);
        if (type.equalsIgnoreCase("Resume")) return new ResumeCommand(model);
        if (type.equalsIgnoreCase("Stop")) return new StopCommand(model);
        if (type.equalsIgnoreCase("Stats")) return new StatsCommand(model);
        if (type.equalsIgnoreCase("Greed")) return new GreedCommand(model);
        if (type.equalsIgnoreCase("Grow Back")) return new GrowBackCommand(model);
        if (type.equalsIgnoreCase("Move Energy")) return new MoveEnergyCommand(model);
        return null;
    }

    public String[] getHelp()
    {
        return new String[] {"Start - starts the simulation",
                "Pause - suspends the simulation",
                "Resume - continues the program from a pause",
                "Stop - discontinues the simulation",
                "Stats - displays current statistics of the simulation",
                "Greed Slider - sets the amount of grass the cow takes at a time (1-100)",
                "Grow Back Rate Slider - sets the rate at which the grass grows back per tick (1-10)",
                "Move Energy Slider - sets the energy it takes for a cow to move to another patch (1-50)"
        };
    }
}
