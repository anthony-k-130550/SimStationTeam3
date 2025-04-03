package simstation;
import mvc.*;

public class SuspendCommand extends Command {
    public SuspendCommand(Model model) {
        super(model);
    }

    public void execute() {
        if (!(model instanceof World)) {
            throw new IllegalArgumentException("Model is not a World.");
        }
        World w = (World) model;
        w.pauseAgents();
    }
}
