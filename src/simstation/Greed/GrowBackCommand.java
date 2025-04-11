package simstation.Greed;
import mvc.*;
import simstation.*;

public class GrowBackCommand extends Command {
    public GrowBackCommand(Model model) {
        super(model);
    }

    public void execute() {
        if (!(model instanceof Meadow)) {
            throw new IllegalArgumentException("Model is not a Meadow.");
        }

        int growBackResult = Integer.parseInt(Utilities.ask("Enter an Integer from 0 to 10 to set patches' grow back rate"));
        if (growBackResult < 0 || growBackResult > 10) {
            throw new IllegalArgumentException("Grow Back Rate must be between 0 and 10.");
        }
        Patch.growBackRate = growBackResult;
        model.notifySubscribers();
    }
}