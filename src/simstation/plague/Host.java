package simstation.plague;

import mvc.Command;
import mvc.Model;
import mvc.Utilities;
import simstation.Heading;
import simstation.MobileAgent;
import simstation.World;

import java.awt.*;

class Host extends MobileAgent
{
    private boolean infected;
    private Color color;
    private int timeBeforeCured;
    private boolean shouldDie;
    private boolean isDead;

    public Host(World world, boolean infected)
    {
        super(world);

        this.shouldDie = false;
        this.isDead = false;

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

    public boolean isDead() { return isDead; }

    public void setIsDead(boolean isDead) { this.isDead = isDead; }

    public void setTimeBeforeCured(int timeBeforeCured) { this.timeBeforeCured = timeBeforeCured; }

    @Override
    public void update()
    {
        if (!this.isDead()) // true if movement and infection should even be calculated
        {
            heading = Heading.random();
            int steps = Utilities.rng.nextInt(10) + 1;
            move(steps);

            if (this.infected) // true if this host is infected
            {
                if (this.timeBeforeCured > 0) // true if this host has not been fully healed
                {
                    Host neighbor = (Host)this.world.getNeighbor(this, 15);

                    if (neighbor != null && !neighbor.isDead() && !neighbor.infected()) // true if there is a neighbor to infect
                    {
                        if (Utilities.rng.nextInt(100) < PlagueSimulation.VIRULENCE
                            && Utilities.rng.nextInt(100) >= PlagueSimulation.RESISTANCE) // true if the virus is able to infect
                        {
                            neighbor.setInfected(true);
                        }
                    }

                    this.timeBeforeCured--;
                }
                else if (this.shouldDie) // true if the timer is up and fatality is on
                {
                    this.color = Color.BLACK;
                    this.isDead = true;
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
