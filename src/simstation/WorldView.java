package simstation;

import mvc.*;
import java.awt.*;
import java.util.*;

public class WorldView extends View
{
    protected static int DIAM = 10;

    public WorldView(Model model)
    {
        super(model);
    }

    public void paintComponent(Graphics gc)
    {
        super.paintComponent(gc);
        World w = (World)this.model;
        Iterator<Agent> iter = w.getAgents().iterator();

        // loops through the list
        while (iter.hasNext()) { this.drawAgent(iter.next(), gc); }
    }

    public void drawAgent(Agent a, Graphics gc)
    {
        Color oldColor = gc.getColor();

        gc.setColor(Color.RED);
        gc.fillOval(a.getX(), a.getY(), DIAM, DIAM);

        gc.setColor(oldColor);
    }
}
