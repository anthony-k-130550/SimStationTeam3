package simstation.plague;

import mvc.*;
import simstation.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
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

class FatalCommand extends Command
{
    public FatalCommand(Model model)
    {
        super(model);
    }

    public void execute()
    {
        PlagueSimulation w = (PlagueSimulation) this.model;
        w.setFatal(!w.isFatal());
    }
}

class PlaguePanel extends WorldPanel
{
    private JLabel initialInfectionPercentageLabel = new JLabel("Initial % Infected:");
    private JLabel infectionProbabilityLabel = new JLabel("Infection Probability:");
    private JLabel initialPopulationSizeLabel = new JLabel("Initial Population Size:");
    private JLabel recoveryTimeLabel = new JLabel("Fatality/Recovery Time:");

    private JSlider initialInfectionPercentageSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, PlagueSimulation.INITIAL_INFECTION_PERCENTAGE);
    private JSlider infectionProbabilitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, PlagueSimulation.VIRULENCE);
    private JSlider initialPopulationSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, PlagueSimulation.POPULATION_SIZE);
    private JSlider recoveryTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, PlagueSimulation.RECOVERY_TIME);

    private JButton fatalityButton = new JButton("Not Fatal");

    public PlaguePanel(PlagueFactory factory)
    {
        super(factory);

        //this.controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS)); // doesn't really work
        this.controlPanel.setLayout(new GridLayout(10, 1)); // technically works
        //this.controlPanel.setLayout(new GridLayout(10, 5)); // technically works

        // initialInfectionPercentageLabel
        initialInfectionPercentageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initialInfectionPercentageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // initialInfectionPercentageSlider
        JPanel initialInfectionPercentagePanel = new JPanel();
        initialInfectionPercentagePanel.setOpaque(false);
        initialInfectionPercentageSlider.setMajorTickSpacing(10);
        initialInfectionPercentageSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                PlagueSimulation.INITIAL_INFECTION_PERCENTAGE = initialInfectionPercentageSlider.getValue();
            }
        });
        initialInfectionPercentagePanel.add(initialInfectionPercentageSlider);

        // infectionProbabilityLabel
        infectionProbabilityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infectionProbabilityLabel.setVerticalAlignment(SwingConstants.CENTER);

        // infectionProbabilitySlider
        JPanel infectionProbabilityPanel = new JPanel();
        infectionProbabilityPanel.setOpaque(false);
        infectionProbabilitySlider.setMajorTickSpacing(10);
        infectionProbabilitySlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                PlagueSimulation.VIRULENCE = infectionProbabilitySlider.getValue();
            }
        });
        infectionProbabilityPanel.add(infectionProbabilitySlider);

        // initialPopulationSizeLabel
        initialPopulationSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initialPopulationSizeLabel.setVerticalAlignment(SwingConstants.CENTER);

        // initialPopulationSizeSlider
        JPanel initialPopulationSizePanel = new JPanel();
        initialPopulationSizePanel.setOpaque(false);
        initialPopulationSizeSlider.setMajorTickSpacing(10);
        initialPopulationSizeSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                PlagueSimulation.POPULATION_SIZE = initialPopulationSizeSlider.getValue();
            }
        });
        initialPopulationSizePanel.add(initialPopulationSizeSlider);

        // recoveryTimeLabel
        recoveryTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recoveryTimeLabel.setVerticalAlignment(SwingConstants.CENTER);

        // recoveryTimeSlider
        JPanel recoveryTimePanel = new JPanel();
        recoveryTimePanel.setOpaque(false);
        recoveryTimeSlider.setMajorTickSpacing(10);
        recoveryTimeSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                PlagueSimulation.RECOVERY_TIME = recoveryTimeSlider.getValue();
            }
        });
        recoveryTimePanel.add(recoveryTimeSlider);

        // fatalityButton
        JPanel fatalityPanel = new JPanel();
        fatalityPanel.setOpaque(false);
        fatalityButton.addActionListener(this);
        fatalityPanel.add(fatalityButton);


        // adds initialInfectionPercentage stuff
        this.controlPanel.add(initialInfectionPercentageLabel);
        this.controlPanel.add(initialInfectionPercentagePanel);

        // adds infectionProbability stuff
        this.controlPanel.add(infectionProbabilityLabel);
        this.controlPanel.add(infectionProbabilityPanel);

        // adds initialPopulationSize stuff
        this.controlPanel.add(initialPopulationSizeLabel);
        this.controlPanel.add(initialPopulationSizePanel);

        // adds recoveryTime stuff
        this.controlPanel.add(recoveryTimeLabel);
        this.controlPanel.add(recoveryTimePanel);

        // adds not fatal button
        this.controlPanel.add(fatalityPanel);
    }
}

class PlagueView extends WorldView
{
    public PlagueView(Model model)
    {
        super(model);
    }

    public void paintComponent(Graphics gc)
    {
        super.paintComponent(gc);

        if (((PlagueSimulation)this.model).isFatal()) this.setBackground(Color.DARK_GRAY);
        else this.setBackground(Color.GRAY);

        World w = (World)this.model;
        Iterator<Agent> iter = w.getWorldAgents().iterator();

        // loops through the list
        while (iter.hasNext()) { this.drawAgent((Host)iter.next(), gc); }
    }

    public void drawAgent(Host a, Graphics gc)
    {
        gc.setColor(a.getColor());
        gc.fillOval(a.getX(), a.getY(), DIAM, DIAM);
    }
}

class PlagueFactory extends WorldFactory implements AppFactory
{
    public PlagueFactory() { super(); }

    @Override
    public Model makeModel() { return new PlagueSimulation(); }

    @Override
    public View makeView(Model model) { return new PlagueView(model); }

    @Override
    public Command makeEditCommand(Model model, String type, Object source)
    {
        Command originalCommand = super.makeEditCommand(model, type, source);
        if (originalCommand != null) return originalCommand;
        if (type.equalsIgnoreCase("Not Fatal")) return new FatalCommand(model);
        return null;
    }

    @Override
    public String getTitle() { return "Plague"; }
}

public class PlagueSimulation extends World
{
    public static int VIRULENCE = 100; // % chance of infection
    public static int RESISTANCE = 2; // % chance of resisting infection
    public static int RECOVERY_TIME = 70; // max 500
    public static int POPULATION_SIZE = 100; // max 200
    public static int INITIAL_INFECTION_PERCENTAGE = 10; // max 100
    private int CURRENT_INFECTED_PERCENTAGE = INITIAL_INFECTION_PERCENTAGE;
    private boolean isFatal = false;

    public PlagueSimulation() { super(); }

    public boolean isFatal() { return isFatal; }

    public void setFatal(boolean isFatal) { this.isFatal = isFatal; changed(); }

    public void populate()
    {
        int infectedPopulationSize = (POPULATION_SIZE * INITIAL_INFECTION_PERCENTAGE) / 100;

        for (int i = 0; i < infectedPopulationSize; i++) { addAgent(new Host(this, true)); }

        for (int i = infectedPopulationSize; i < POPULATION_SIZE; i++) { addAgent(new Host(this, false)); }
    }

    @Override
    public void updateStatistics()
    {
        super.updateStatistics();

        int numOfInfectedAgents = 0;

        for (Agent agent : worldAgents) // loops through the agents
        {
            if (((Host)agent).infected()) { numOfInfectedAgents++; }
        }

        CURRENT_INFECTED_PERCENTAGE = (numOfInfectedAgents * 100) / POPULATION_SIZE;
    }

    @Override
    public String getStatus()
    {
        return "# of Agents = " + POPULATION_SIZE
                + "\nClock: " + this.clock
                + "\n% Infected: " + CURRENT_INFECTED_PERCENTAGE;
    }

    public static void main(String[] args)
    {
        AppPanel panel = new PlaguePanel(new PlagueFactory());
        panel.display();
    }
}
