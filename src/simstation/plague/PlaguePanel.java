package simstation.plague;

import mvc.Command;
import mvc.Model;
import mvc.Utilities;
import simstation.WorldPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;

class PlaguePanel extends WorldPanel implements ChangeListener
{
    private JPanel slidersPanel = new JPanel();

    private JSlider initialInfectionPercentageSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, PlagueSimulation.INITIAL_INFECTION_PERCENTAGE);
    private JSlider infectionProbabilitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, PlagueSimulation.VIRULENCE);
    private JSlider initialPopulationSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, PlagueSimulation.POPULATION_SIZE);
    private JSlider recoveryTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, PlagueSimulation.RECOVERY_TIME);

    private JButton fatalityButton = new JButton("Not Fatal");

    public PlaguePanel(PlagueFactory factory)
    {
        super(factory);

        this.slidersPanel.setLayout(new GridLayout(5, 1));

        // initialInfectionPercentageSlider
        initialInfectionPercentageSlider.setMajorTickSpacing(20);
        initialInfectionPercentageSlider.setPaintTicks(true);
        initialInfectionPercentageSlider.setPaintLabels(true);
        initialInfectionPercentageSlider.setMinorTickSpacing(10);
        initialInfectionPercentageSlider.addChangeListener(this);

        // infectionProbabilitySlider
        infectionProbabilitySlider.setMajorTickSpacing(20);
        infectionProbabilitySlider.setPaintTicks(true);
        infectionProbabilitySlider.setPaintLabels(true);
        infectionProbabilitySlider.setMinorTickSpacing(10);
        infectionProbabilitySlider.addChangeListener(this);

        // initialPopulationSizeSlider
        initialPopulationSizeSlider.setMajorTickSpacing(50);
        initialPopulationSizeSlider.setPaintTicks(true);
        initialPopulationSizeSlider.setPaintLabels(true);
        initialPopulationSizeSlider.setMinorTickSpacing(25);
        initialPopulationSizeSlider.addChangeListener(this);

        // recoveryTimeSlider
        recoveryTimeSlider.setMajorTickSpacing(100);
        recoveryTimeSlider.setPaintTicks(true);
        recoveryTimeSlider.setPaintLabels(true);
        recoveryTimeSlider.setMinorTickSpacing(25);
        recoveryTimeSlider.addChangeListener(this);

        // fatalityButton
        JPanel fatalityPanel = new JPanel();
        fatalityPanel.setOpaque(false);
        fatalityButton.addActionListener(this);
        fatalityPanel.add(fatalityButton);


        // adds initialInfectionPercentage stuff
        JPanel pp = new JPanel();
        pp.setLayout(new BorderLayout());
        pp.setOpaque(false);

        JPanel ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(new JLabel("Initial % Infected:"));
        pp.add(ppp, BorderLayout.NORTH);

        ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(initialInfectionPercentageSlider);
        pp.add(ppp, BorderLayout.CENTER);

        this.slidersPanel.add(pp);

        // adds infectionProbability stuff
        pp = new JPanel();
        pp.setLayout(new BorderLayout());
        pp.setOpaque(false);

        ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(new JLabel("Infection Probability:"));
        pp.add(ppp, BorderLayout.NORTH);

        ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(infectionProbabilitySlider);
        pp.add(ppp, BorderLayout.CENTER);

        this.slidersPanel.add(pp);

        // adds initialPopulationSize stuff
        pp = new JPanel();
        pp.setLayout(new BorderLayout());
        pp.setOpaque(false);

        ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(new JLabel("Initial Population Size:"));
        pp.add(ppp, BorderLayout.NORTH);

        ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(initialPopulationSizeSlider);
        pp.add(ppp, BorderLayout.CENTER);

        this.slidersPanel.add(pp);

        // adds recoveryTime stuff
        pp = new JPanel();
        pp.setLayout(new BorderLayout());
        pp.setOpaque(false);

        ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(new JLabel("Fatality/Recovery Time:"));
        pp.add(ppp, BorderLayout.NORTH);

        ppp = new JPanel();
        ppp.setOpaque(false);
        ppp.add(recoveryTimeSlider);
        pp.add(ppp, BorderLayout.CENTER);

        this.slidersPanel.add(pp);

        // adds not fatal button
        this.slidersPanel.add(fatalityPanel);

        this.controlPanel.add(slidersPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        super.actionPerformed(ae);
        this.update();
        this.view.repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource() == initialInfectionPercentageSlider)
            PlagueSimulation.INITIAL_INFECTION_PERCENTAGE = initialInfectionPercentageSlider.getValue();
        if (e.getSource() == infectionProbabilitySlider)
            PlagueSimulation.VIRULENCE = infectionProbabilitySlider.getValue();
        if (e.getSource() == initialPopulationSizeSlider)
            PlagueSimulation.POPULATION_SIZE = initialPopulationSizeSlider.getValue();
        if (e.getSource() == recoveryTimeSlider)
            PlagueSimulation.RECOVERY_TIME = recoveryTimeSlider.getValue();
        model.changed();
    }

    @Override
    public void update()
    {
        initialInfectionPercentageSlider.setValue(((PlagueSimulation)model).INITIAL_INFECTION_PERCENTAGE);
        infectionProbabilitySlider.setValue(((PlagueSimulation)model).VIRULENCE);
        initialPopulationSizeSlider.setValue(((PlagueSimulation)model).POPULATION_SIZE);
        recoveryTimeSlider.setValue(((PlagueSimulation)model).RECOVERY_TIME);
        if (((PlagueSimulation)this.model).isFatal()) fatalityButton.setText("Fatal");
        else fatalityButton.setText("Not Fatal");
    }
}

//================================================================

class SetInitialInfectionPercentageCommand extends Command
{
    Integer initialInfectionPercentageValue = null;

    public SetInitialInfectionPercentageCommand(Model model) { super(model); }

    public void execute() throws Exception
    {
        if (initialInfectionPercentageValue == null)
        {
            String response = Utilities.ask("Initial % Infected = ?");
            initialInfectionPercentageValue = Integer.valueOf(response);
        }
        PlagueSimulation.INITIAL_INFECTION_PERCENTAGE = initialInfectionPercentageValue;
    }
}

class SetInfectionProbabilityCommand extends Command
{
    Integer infectionProbabilityValue = null;

    public SetInfectionProbabilityCommand(Model model) { super(model); }

    public void execute() throws Exception
    {
        if (infectionProbabilityValue == null)
        {
            String response = Utilities.ask("Infection Probability = ?");
            infectionProbabilityValue = Integer.valueOf(response);
        }
        PlagueSimulation.VIRULENCE = infectionProbabilityValue;
    }
}

class SetInitialPopulationSizeCommand extends Command
{
    Integer initialPopulationSizeValue = null;

    public SetInitialPopulationSizeCommand(Model model) { super(model); }

    public void execute() throws Exception
    {
        if (initialPopulationSizeValue == null)
        {
            String response = Utilities.ask("Initial Population Size = ?");
            initialPopulationSizeValue = Integer.valueOf(response);
        }
        PlagueSimulation.POPULATION_SIZE = initialPopulationSizeValue;
    }
}

class SetRecoveryTimeCommand extends Command
{
    Integer recoveryTimeValue = null;

    public SetRecoveryTimeCommand(Model model) { super(model); }

    public void execute() throws Exception
    {
        if (recoveryTimeValue == null)
        {
            String response = Utilities.ask("Fatality/Recovery Time = ?");
            recoveryTimeValue = Integer.valueOf(response);
        }
        PlagueSimulation.RECOVERY_TIME = recoveryTimeValue;
    }
}
