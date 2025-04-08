package simstation.Greed;
import mvc.*;
import simstation.*;

public class Meadow extends World {
    private int waitPenalty = 5;
    private int moveEnergy = 10;
    private int numCows = 50;
    private int dim = SIZE/(Patch.patchSize);
    Patch[][] patches = new Patch[dim][dim];

    public Meadow() {
        super();
        populate();
    }

    public void populate() {
        this.agents.clear(); //i added this just in case we need to repopulate after some slider adjustments

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                Patch temp = new Patch();
                temp.setX(i*Patch.patchSize);
                temp.setY(j*Patch.patchSize);
                patches[i][j] = temp;
                addAgent(temp);
            }
        }

        for (int i = 0; i < numCows; i++) {
            Cow temp = new Cow();
            temp.setX(Utilities.rng.nextInt(SIZE));
            temp.setY(Utilities.rng.nextInt(SIZE));
            temp.setPatch(this.getPatch(temp.getX(), temp.getY()));
            addAgent(temp);
        }

        changed();
    }

    public Patch getPatch(int xc, int yc) {
        if (xc >= SIZE) {
            xc = xc % SIZE;
        }
        if (yc >= SIZE) { //avoid out of bounds error when 500/10 = 50, even tho there is no index 50
            yc = yc % SIZE;
        }
        int i = xc/Patch.patchSize;
        int j = yc/Patch.patchSize;
        return patches[i][j];
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public void setMoveEnergy(int moveEnergy) {
        this.moveEnergy = moveEnergy;
    }

    public int getWaitPenalty() {
        return waitPenalty;
    }

    public static void main(String[] args) {
        AppPanel panel = new GreedSimulationPanel(new GreedFactory());
        panel.display();
    }
}
