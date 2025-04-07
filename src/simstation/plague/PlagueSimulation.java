package simstation.plague;

import mvc.*;
import simstation.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

class Host extends MobileAgent
{
    private boolean infected;
    private Color color;
    private int timeBeforeCured;

    public Host(boolean infected)
    {
        super();

        if (this.infected = infected) // true if the Host is infected
        {
            this.color = Color.RED;
            this.timeBeforeCured = PlagueSimulation.RECOVERY_TIME;
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
        this.infected = infected;
        setColor();
        setTimeBeforeCured(PlagueSimulation.RECOVERY_TIME);
    }

    public Color getColor() { return color; }

    public void setColor()
    {
        if (infected) // true if the Host is infected
            this.color = Color.RED;
        else
            this.color = Color.GREEN;
    }

    public int getTimeBeforeCured() { return timeBeforeCured; }

    public void setTimeBeforeCured(int timeBeforeCured) { this.timeBeforeCured = timeBeforeCured; }

    @Override
    public void update()
    {
        heading = Heading.random();
        int steps = Utilities.rng.nextInt(10) + 1;
        move(steps);

        if (this.infected) // true if this host is infected
        {
            if (this.timeBeforeCured > 0) // true if this host has not been fully healed
            {
                Host neighbor = (Host) this.world.getNeighbor(this, 15);

                if (neighbor != null && !neighbor.infected()) // true if there is a neighbor to infect
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
            else
            {
                this.setInfected(false);
            }
        }
    }
}

class PlaguePanel extends WorldPanel
{
    private JLabel initialInfectionPercentageLabel = new JLabel("Infection % Infected:");
    private JLabel infectionProbabilityLabel = new JLabel("Infection Probability:");
    private JLabel initialPopulationSizeLabel = new JLabel("Initial Population Size:");
    private JLabel recoveryTimeLabel = new JLabel("Fatality/Recovery Time:");

    private JSlider initialInfectionPercentageSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, PlagueSimulation.INITIAL_INFECTION_PERCENTAGE);
    private JSlider infectionProbabilitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, PlagueSimulation.VIRULENCE);
    private JSlider initialPopulationSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, PlagueSimulation.POPULATION_SIZE);
    private JSlider recoveryTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, PlagueSimulation.RECOVERY_TIME);

    public PlaguePanel(PlagueFactory factory)
    {
        super(factory);

        this.controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

        // CENTER TEXT!!!!!
        //this.initialInfectionPercentageText.center;
        //this.controlPanel.add(initialInfectionPercentageText);



        // initialInfectionPercentageLabel
        initialInfectionPercentageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initialInfectionPercentageLabel.setVerticalAlignment(SwingConstants.CENTER);

        // initialInfectionPercentageSlider
        initialInfectionPercentageSlider.setMajorTickSpacing(10);

        // infectionProbabilityLabel
        infectionProbabilityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infectionProbabilityLabel.setVerticalAlignment(SwingConstants.CENTER);

        // infectionProbabilitySlider
        infectionProbabilitySlider.setMajorTickSpacing(10);

        // initialPopulationSizeLabel
        initialPopulationSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initialPopulationSizeLabel.setVerticalAlignment(SwingConstants.CENTER);

        // initialPopulationSizeSlider
        initialPopulationSizeSlider.setMajorTickSpacing(10);

        // recoveryTimeLabel
        recoveryTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recoveryTimeLabel.setVerticalAlignment(SwingConstants.CENTER);

        // recoveryTimeSlider
        recoveryTimeSlider.setMajorTickSpacing(10);


        // adds initialInfectionPercentage stuff
        this.controlPanel.add(initialInfectionPercentageLabel);
        this.controlPanel.add(initialInfectionPercentageSlider);

        // adds infectionProbability stuff
        this.controlPanel.add(infectionProbabilityLabel);
        this.controlPanel.add(infectionProbabilitySlider);

        // adds initialPopulationSize stuff
        this.controlPanel.add(initialPopulationSizeLabel);
        this.controlPanel.add(initialPopulationSizeSlider);

        // adds recoveryTime stuff
        this.controlPanel.add(recoveryTimeLabel);
        this.controlPanel.add(recoveryTimeSlider);
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
    public String getTitle() { return "Plague"; }
}

public class PlagueSimulation extends World
{
    public static int VIRULENCE = 50; // % chance of infection
    public static int RESISTANCE = 2; // % chance of resisting infection
    public static int RECOVERY_TIME = 100; // max 500
    public static int POPULATION_SIZE = 200; // max 200
    public static int INITIAL_INFECTION_PERCENTAGE = 5; // max 100
    private int CURRENT_INFECTED_PERCENTAGE = INITIAL_INFECTION_PERCENTAGE;

    public PlagueSimulation() { super(); }

    public void populate()
    {
        int infectedPopulationSize = (POPULATION_SIZE * INITIAL_INFECTION_PERCENTAGE) / 100;

        for (int i = 0; i < infectedPopulationSize; i++) { addAgent(new Host(true)); }

        for (int i = infectedPopulationSize; i < POPULATION_SIZE; i++) { addAgent(new Host(false)); }
    }

    @Override
    public void updateStatistics()
    {
        super.updateStatistics();

        int numOfInfectedAgents = 0;
        for (Agent agent : worldAgents)
        {
            if (((Host)agent).infected())
            {
                numOfInfectedAgents++;
            }
        }
        CURRENT_INFECTED_PERCENTAGE = (numOfInfectedAgents * 100) / POPULATION_SIZE;
    }

    @Override
    public String getStatus()
    {
        return "# of Agents = " + POPULATION_SIZE + "\nClock: " + this.clock + "\n% Infected: " + CURRENT_INFECTED_PERCENTAGE;
    }

    public static void main(String[] args)
    {
        AppPanel panel = new PlaguePanel(new PlagueFactory());
        panel.display();
    }
}
