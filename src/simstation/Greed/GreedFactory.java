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
