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

    public Host(boolean infected)
    {
        super();

        if (this.infected = infected) // true if the Host is infected
            this.color = Color.RED;
        else
            this.color = Color.GREEN;
    }

    public boolean infected() { return infected; }

    public void setInfected(boolean infected)
    {
        this.infected = infected;
        setColor();
    }

    public Color getColor() { return color; }

    public void setColor()
    {
        if (infected) // true if the Host is infected
            this.color = Color.RED;
        else
            this.color = Color.GREEN;
    }

    /*
    @Override
    public void update()
    {
        Heading heading = Heading.random();
        int steps = Utilities.rng.nextInt(20) + 1;
        move(steps);
    }
     */
}

class PlaguePanel extends WorldPanel
{
    private JTextField initialInfectionPercentageText = new JTextField("Initial % Infected:");
    private JTextField infectionProbabilityText = new JTextField("Infection Probability:");
    private JTextField initialPopulationSizeText = new JTextField("Initial Population Size:");
    private JTextField recoveryTimeText = new JTextField("Fatality/Recovery Time:");
    private JSlider initialInfectionPercentageSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, PlagueSimulation.INITIAL_INFECTION_PERCENTAGE);
    private JSlider infectionProbabilitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, PlagueSimulation.VIRULENCE);
    private JSlider initialPopulationSizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, PlagueSimulation.POPULATION_SIZE);
    private JSlider recoveryTimeSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, PlagueSimulation.RECOVERY_TIME);

    // include sliders for simulation
    public PlaguePanel(PlagueFactory factory)
    {
        super(factory);


        this.controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

        // CENTER TEXT!!!!!
        //this.initialInfectionPercentageText.center;
        this.controlPanel.add(initialInfectionPercentageText);
        this.controlPanel.add(initialInfectionPercentageSlider);


        this.controlPanel.add(infectionProbabilityText);
        this.controlPanel.add(infectionProbabilitySlider);


        this.controlPanel.add(initialPopulationSizeText);
        this.controlPanel.add(initialPopulationSizeSlider);


        this.controlPanel.add(recoveryTimeText);
        this.controlPanel.add(recoveryTimeSlider);


        // add sliders
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
    public static int RECOVERY_TIME = 500; // max 500
    public static int POPULATION_SIZE = 200; // max 200
    public static int INITIAL_INFECTION_PERCENTAGE = 100; // max 100
    public static int CURRENT_INFECTED_PERCENTAGE = INITIAL_INFECTION_PERCENTAGE;

    public PlagueSimulation() { super(); }

    public void populate()
    {
        int infectedPopulationSize = (int)(POPULATION_SIZE * (INITIAL_INFECTION_PERCENTAGE / 100.0));

        for(int i = 0; i < infectedPopulationSize; i++) { addAgent(new Host(true)); }

        for(int i = infectedPopulationSize + 1; i < POPULATION_SIZE; i++) { addAgent(new Host(false)); }
    }

    @Override
    public void updateStatistics()
    {
        super.updateStatistics();

        for (Agent agent : worldAgents)
        {
            if (((Host)agent).infected())
            {
                Host neighbor = (Host) getNeighbor(agent, 10);
                if (neighbor != null) { neighbor.setInfected(true); }

                // add a counter for an infected host to have disease die down
                // if ()
            }
        }
    }

    @Override
    public String getStatus()
    {
        int infectedPopulationSize = (int)(POPULATION_SIZE * (INITIAL_INFECTION_PERCENTAGE / 100.0));

        for (int i = 0; i < this.worldAgents.size(); i++)
        {
            if (((Host)worldAgents.get(i)).infected())
                infectedPopulationSize++;
        }

        CURRENT_INFECTED_PERCENTAGE = (int)(((double)infectedPopulationSize) / POPULATION_SIZE);

        return "# of Agents = " + POPULATION_SIZE + "Clock: " + this.clock + "\n% Infected: " + CURRENT_INFECTED_PERCENTAGE;
    }

    public static void main(String[] args)
    {
        AppPanel panel = new PlaguePanel(new PlagueFactory());
        panel.display();
    }
}
