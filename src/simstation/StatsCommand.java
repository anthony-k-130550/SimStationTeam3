package simstation;
import mvc.*;

public class StatsCommand extends Command {
    public StatsCommand(Model model) {
        super(model);
    }

    public void execute() {
        if (!(model instanceof World)) {
            throw new IllegalArgumentException("Model is not a World.");
        }
        World w = (World) model;
        Utilities.inform(w.getStatus());
    }
}
