package simstation.plague;

import mvc.*;
import simstation.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

class Host extends MobileAgent
{
    private boolean infected;
    private Color color;
    private int timeBeforeCured;
    private boolean shouldDie;

    public Host(World world, boolean infected)
    {
        super(world);

        this.shouldDie = false;

        if (this.infected = infected) // true if the Host is infected
        {
            this.color = Color.RED;
            this.timeBeforeCured = PlagueSimulation.RECOVERY_TIME;
            if (((PlagueSimulation)this.world).isFatal()) this.shouldDie = true;
        }
        else
        {
            this.color = Color.GREEN;
            this.timeBeforeCured = 0;
        }
    }

    public boolean infected() { return infected; }

    public void setInfected(boolean infected)
    {
        if (this.infected = infected) // true if the Host is infected
        {
            this.color = Color.RED;
            if (((PlagueSimulation)this.world).isFatal()) this.shouldDie = true;
        }
        else
        {
            this.color = Color.GREEN;
        }

        setTimeBeforeCured(PlagueSimulation.RECOVERY_TIME);
    }

    public Color getColor() { return color; }

    public int getTimeBeforeCured() { return timeBeforeCured; }

    public boolean getShouldDie() { return shouldDie; }

    public void setShouldDie(boolean shouldDie) { this.shouldDie = shouldDie; }

    public void setTimeBeforeCured(int timeBeforeCured) { this.timeBeforeCured = timeBeforeCured; }

    @Override
    public void update()
    {
        if (this.color != Color.BLACK)
        {
            heading = Heading.random();
            int steps = Utilities.rng.nextInt(15) + 1;
            move(steps);

            if (this.infected) // true if this host is infected
            {
                if (this.timeBeforeCured > 0) // true if this host has not been fully healed
                {
                    Host neighbor = (Host)this.world.getNeighbor(this, 15);

                    if (neighbor != null && !neighbor.infected() && neighbor.getColor() != Color.BLACK) // true if there is a neighbor to infect
                    {
                        if (Utilities.rng.nextInt(100) < PlagueSimulation.VIRULENCE) // true if the virus is able to infect
                        {
                            if (Utilities.rng.nextInt(100) >= PlagueSimulation.RESISTANCE) // true if the neighbor could not defend from virus
                            {
                                neighbor.setInfected(true);
                            }
                        }
                    }

                    this.timeBeforeCured--;
                }
                else if (this.shouldDie) // true if the timer is up and fatality is on
                {
                    //this.world.getWorldAgents().remove(this);
                    this.color = Color.BLACK;
                }
                else
                {
                    this.setInfected(false);
                }
            }
        }
    }
}

//================================================================

class FatalCommand extends Command
{
    public FatalCommand(Model model) { super(model); }

    public void execute()
    {
        PlagueSimulation w = (PlagueSimulation) this.model;
        w.setFatal(!w.isFatal());
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

//================================================================

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

class PlagueView extends WorldView
{
    public PlagueView(Model model) { super(model); }

    public void paintComponent(Graphics gc)
    {
        super.paintComponent(gc);

        if (((PlagueSimulation)this.model).isFatal()) this.setBackground(Color.DARK_GRAY);
        else this.setBackground(Color.GRAY);

        World w = (World)this.model;
        Iterator<Agent> iter = w.getAgents().iterator();

        // loops through the list
        while (iter.hasNext()) { this.drawAgent((Host)iter.next(), gc); }
    }

    public void drawAgent(Host a, Graphics gc)
    {
        gc.setColor(a.getColor());
        gc.fillOval(a.getX(), a.getY(), DIAM, DIAM);
    }
}

//================================================================

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
            if (type.equalsIgnoreCase("Initial Population Size"))
            {
                command = new SetInitialPopulationSizeCommand(model);
                if (source instanceof JSlider)
                {
                    ((SetInitialPopulationSizeCommand)command).initialPopulationSizeValue = ((JSlider)source).getValue();
                }
            }
            if (type.equalsIgnoreCase("Fatality/Recovery Time"))
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

//================================================================

public class PlagueSimulation extends World
{
    public static int VIRULENCE = 50; // % chance of infection
    public static int RESISTANCE = 2; // % chance of resisting infection
    public static int RECOVERY_TIME = 200; // max 500
    public static int POPULATION_SIZE = 150; // max 200
    public static int INITIAL_INFECTION_PERCENTAGE = 10; // max 100
    private int CURRENT_INFECTED_PERCENTAGE = INITIAL_INFECTION_PERCENTAGE;
    private boolean isFatal = false;

    public PlagueSimulation() { super(); }

    public boolean isFatal() { return isFatal; }

    public void setFatal(boolean isFatal)
    {
        this.isFatal = isFatal;
        for (Agent a : this.agents) // loops through all the agents
        {
            if (this.isFatal && ((Host)a).infected()) // true if an infected agent should be dead after recovery
                ((Host)a).setShouldDie(true);
            else
                ((Host)a).setShouldDie(false);
        }
    }

    @Override
    public void startAgents()
    {
        this.agents.clear();
        populate();
        for (Agent agent : agents) { agent.start(); }
        observer.start();
    }

    @Override
    public void populate()
    {
        int infectedPopulationSize = (POPULATION_SIZE * INITIAL_INFECTION_PERCENTAGE) / 100;

        for (int i = 0; i < infectedPopulationSize; i++) { addAgent(new Host(this, true)); }

        for (int i = infectedPopulationSize; i < POPULATION_SIZE; i++) { addAgent(new Host(this, false)); }

        this.updateStatistics();
    }

    @Override
    public void updateStatistics()
    {
        super.updateStatistics();

        int numOfInfectedAgents = 0;
        this.numAgents = 0;
        for (Agent agent : agents) // loops through the agents
        {
            if (((Host)agent).infected()) { numOfInfectedAgents++; }
            if (((Host)agent).getColor() != Color.BLACK) { numAgents++; }
        }
        CURRENT_INFECTED_PERCENTAGE = (numOfInfectedAgents * 100) / POPULATION_SIZE;
    }

    @Override
    public String getStatus()
    {
        return "Original Population = " + POPULATION_SIZE
                + "\n# of Living Hosts = " + this.numAgents
                + "\nClock: " + this.clock
                + "\n% Infected: " + CURRENT_INFECTED_PERCENTAGE;
    }

    public static void main(String[] args)
    {
        AppPanel panel = new PlaguePanel(new PlagueFactory());
        panel.display();
    }
}
