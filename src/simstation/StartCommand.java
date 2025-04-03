package simstation;
import mvc.*;

public class StartCommand extends Command {
    public StartCommand(Model model) {
        super(model);
    }

    public void execute() {
        if (!(model instanceof World)) {
            throw new IllegalArgumentException("Model is not a World.");
        }
        World w = (World) model;
        w.startAgents();
    }
}
