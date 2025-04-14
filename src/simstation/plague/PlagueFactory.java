package simstation.plague;

import mvc.AppFactory;
import mvc.Command;
import mvc.Model;
import mvc.View;
import simstation.WorldFactory;

import javax.swing.*;

class PlagueFactory extends WorldFactory implements AppFactory
{
    public PlagueFactory() { super(); }

    @Override
    public Model makeModel() { return new PlagueSimulation(); }

    @Override
    public View makeView(Model model) { return new PlagueView(model); }

    @Override
    public String[] getEditCommands()
    {
        return new String[] {"Start", "Pause", "Resume", "Stop", "Stats",
                "Initial % Infected", "Infection Probability",
                "Initial Population Size", "Fatality/Recovery Time",
                "Toggle Fatality"};
    }

    @Override
    public Command makeEditCommand(Model model, String type, Object source)
    {
        Command command = super.makeEditCommand(model, type, source);

        if (command == null) // true if it is a plague-specific command
        {
            if (type.equalsIgnoreCase("Initial % Infected"))
            {
                command = new SetInitialInfectionPercentageCommand(model);
                if (source instanceof JSlider)
                {
                    ((SetInitialInfectionPercentageCommand)command).initialInfectionPercentageValue = ((JSlider)source).getValue();
                }
            }
            else if (type.equalsIgnoreCase("Infection Probability"))
            {
                command = new SetInfectionProbabilityCommand(model);
                if (source instanceof JSlider)
                {
                    ((SetInfectionProbabilityCommand)command).infectionProbabilityValue = ((JSlider)source).getValue();
                }
            }
            else if (type.equalsIgnoreCase("Initial Population Size"))
            {
                command = new SetInitialPopulationSizeCommand(model);
                if (source instanceof JSlider)
                {
                    ((SetInitialPopulationSizeCommand)command).initialPopulationSizeValue = ((JSlider)source).getValue();
                }
            }
            else if (type.equalsIgnoreCase("Fatality/Recovery Time"))
            {
                command = new SetRecoveryTimeCommand(model);
                if (source instanceof JSlider)
                {
                    ((SetRecoveryTimeCommand)command).recoveryTimeValue = ((JSlider)source).getValue();
                }
            }
            else if (type.equalsIgnoreCase("Fatal") || type.equalsIgnoreCase("Not Fatal")
                    || type.equalsIgnoreCase("Toggle Fatality"))
            {
                return new FatalCommand(model);
            }
        }

        return command;
    }

    @Override
    public String getTitle() { return "Plague Simulation"; }
}
