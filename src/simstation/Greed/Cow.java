package simstation.Greed;
import simstation.*;

public class Cow extends MobileAgent {
    private int energy = 100;
    public static int greediness = 25;
    private Patch patch;

    public void update() throws IllegalArgumentException {
        if (!(world instanceof Meadow)) {
            throw new IllegalArgumentException("World must be instance of Meadow");
        }
        Meadow m = (Meadow) this.world;

        while (this.energy > m.getMoveEnergy() && this.patch.getEnergy() < Cow.greediness) { //moves while cow has enough energy and this patch doesn't have enough
            this.energy -= m.getMoveEnergy();
            if (this.energy <= 0) {
                this.stop();
                this.world.changed();
            }
            this.heading = Heading.random();
            this.move(Patch.patchSize);
            this.patch = m.getPatch(this.xc, this.yc);
            this.world.changed();
        }
        this.patch.eatMe(this, Cow.greediness); //eats regardless, so the wait penalty can apply
    }

    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public void setPatch(Patch patch) {
        this.patch = patch;
    }
}
