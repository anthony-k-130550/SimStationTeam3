package simstation.Greed;
import mvc.*;
import simstation.*;

public class GreedCommand extends Command {
    public GreedCommand(Model model) {
        super(model);
    }

    public void execute() {
        if (!(model instanceof Meadow)) {
            throw new IllegalArgumentException("Model is not a Meadow.");
        }

        int greedResult = Integer.parseInt(Utilities.ask("Enter an Integer from 0 to 100 to set cow's greediness"));
        if (greedResult < 0 || greedResult > 100) {
            throw new IllegalArgumentException("Greediness must be between 0 and 100.");
        }
        Cow.greediness = greedResult;
        model.notifySubscribers(); //so that the control panel knows to change the slider
    }
}