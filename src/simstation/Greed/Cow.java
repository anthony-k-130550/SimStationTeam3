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
            Meadow m = (Meadow) this.world;

            if (patch.getEnergy() < this.greediness) { //not enough grass for the cow
                if (this.energy > m.getMoveEnergy()) { //move if cow can (has energy)
                    this.heading = Heading.random();
                    this.move(Patch.patchSize);
                    this.energy -= m.getMoveEnergy();
                    this.patch = m.getPatch(this.xc, this.yc);
                    notify();
                    this.world.changed();
                } else { //wait if cow can't
                    this.energy -= m.getWaitPenalty();
                    this.world.changed();
                    if (this.getEnergy() <= 0) { //check for death
                        notify();
                        this.world.changed();
                        this.stop();
                    }
                }
            } else {
                this.patch.eatMe(this, Cow.greediness);
                notify();
                this.world.changed();
            }
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
