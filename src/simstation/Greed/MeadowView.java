package simstation.Greed;
import mvc.Model;
import simstation.*;

import java.awt.*;
import java.util.Iterator;

public class MeadowView extends WorldView {
    Color darkGreen = new Color(0,50, 0);
    Color halfFullGreen = new Color(50, 90,20);
    Color halfEmptyGreen = new Color(25,140,0);
    Color lightGreen = new Color(0,255,0);
    Color emptyGreen = new Color(15, 255, 100);
    protected static int DIAM = 5;

    public MeadowView(Model model)
    {
        super(model);
    }

    public void drawAgent(Agent a, Graphics gc)
    {
        Color oldColor = gc.getColor();

        if (a instanceof Cow) {
            Cow c = (Cow)a;
            if (c.getEnergy() > 0) {
                gc.setColor(Color.RED);
            } else {
                gc.setColor(Color.WHITE);
            }
            gc.fillOval(a.getX(), a.getY(), DIAM, DIAM);
        }
        else if (a instanceof Patch) {
            Patch p = (Patch) a;
            int energyLevel = p.getEnergy();
            if (energyLevel == 0) {
                gc.setColor(emptyGreen);
            } else if (energyLevel > 0 && energyLevel <= 25) {
                gc.setColor(lightGreen);
            } else if (energyLevel > 25 && energyLevel <= 50) {
                gc.setColor(halfEmptyGreen);
            } else if (energyLevel > 50 && energyLevel <= 75) {
                gc.setColor(halfFullGreen);
            } else if (energyLevel > 75 && energyLevel <= 100) {
                gc.setColor(darkGreen);
            }
            gc.fillRect(p.getX(), p.getY(), Patch.patchSize, Patch.patchSize);
            gc.setColor(Color.WHITE);
            gc.drawRect(p.getX(), p.getY(), Patch.patchSize, Patch.patchSize);
        }

        gc.setColor(oldColor);
    }
}
