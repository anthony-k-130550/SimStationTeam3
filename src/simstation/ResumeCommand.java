package simstation;
import mvc.*;

public class ResumeCommand extends Command {
    public ResumeCommand(Model model) {
        super(model);
    }

    public void execute() throws Exception {
        if (!(model instanceof World)) {
            throw new IllegalArgumentException("Model is not a World.");
        }
        World w = (World) model;
        w.resumeAgents();
    }
}
