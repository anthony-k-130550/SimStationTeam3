package simstation.Greed;
import mvc.*;
import simstation.*;

public class MoveEnergyCommand extends Command {
    public MoveEnergyCommand(Model model) {
        super(model);
    }

    public void execute() {
        if (!(model instanceof Meadow)) {
            throw new IllegalArgumentException("Model is not a Meadow.");
        }

        int moveEnergyResult = Integer.parseInt(Utilities.ask("Enter an Integer from 0 to 50 to set the energy it costs a cow to move"));
        if (moveEnergyResult < 0 || moveEnergyResult > 50) {
            throw new IllegalArgumentException("Grow Back Rate must be between 0 and 50.");
        }
        Meadow meadow = (Meadow) model;
        meadow.setMoveEnergy(moveEnergyResult);
        model.notifySubscribers();
    }
}