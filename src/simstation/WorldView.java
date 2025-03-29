package simstation;

import mvc.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class WorldView extends View
{
    protected static int DIAM = 10;

    public WorldView(Model model)
    {
        super(model);
        // TODO
    }

    public void paintComponent(Graphics gc)
    {
        Iterator<Agent> iter = this.model.getWorldAgents().iterator();

        while (iter.hasNext()) // loops through the list
        {
            this.drawAgent(iter.next(), gc);
        }
    }

    public void drawAgent(Agent a, Graphics gc)
    {
        Color oldColor = gc.getColor();
        gc.setColor(Color.RED);
        gc.drawOval(a.getX(), a.getY(), DIAM, DIAM);
        gc.setColor(oldColor);
    }
}
