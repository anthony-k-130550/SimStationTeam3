package simstation.plague;

import mvc.Model;
import simstation.Agent;
import simstation.World;
import simstation.WorldView;

import java.awt.*;
import java.util.Iterator;

public class PlagueView extends WorldView
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