package simstation.Greed;
import simstation.*;

public class Cow extends MobileAgent {
    private int energy = 100;
    public static int greediness = 25;
    private Patch patch;

    public synchronized void update() throws IllegalArgumentException {
        if (!(world instanceof Meadow)) {
            throw new IllegalArgumentException("World must be instance of Meadow");
        }
        Meadow m = (Meadow) world;

        if (this.energy <= 0) {
            this.stop();
            this.world.changed();
        }

        while (patch.getEnergy() < greediness) { //not enough grass for the cow
            if (this.energy > m.getMoveEnergy()) { //move if cow can't
                heading = Heading.random();
                this.move(Patch.patchSize);
                this.energy -= m.getMoveEnergy();
                this.patch = m.getPatch(this.xc, this.yc);
            } else { //wait if cow can't
                try {
                    this.energy -= m.getWaitPenalty();
                    wait();
                } catch (InterruptedException e) {

                }
            }
        }

        this.patch.eatMe(this, Cow.greediness);
        System.out.println("This energy: " + this.energy);
        System.out.println("Patch: " + this.patch.getEnergy());
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {

        }

        notify();
        this.world.changed();
    }

    public synchronized int getEnergy() {
        return energy;
    }
    public synchronized void setEnergy(int energy) {
        this.energy = energy;
    }
    public synchronized void setPatch(Patch patch) {
        this.patch = patch;
    }
}
