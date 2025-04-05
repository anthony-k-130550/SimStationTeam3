package simstation.Greed;
import simstation.*;

public class Patch extends Agent {
    protected int energy = 100;
    public static int growBackRate = 20;
    public static int patchSize = 10; //i might have to change this to non-static according to UML

    public synchronized void update() {
        while (energy >= 100) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        energy = Math.min(energy + growBackRate, 100);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {

        }
        notify();
        this.world.changed();
    }

    public synchronized void eatMe(Cow cow, int amt) {
        this.energy = Math.max (this.energy - amt, 0);
        cow.setEnergy(Math.min(cow.getEnergy() + amt, 100));
        this.world.changed();
    }

    public synchronized int getEnergy() {
        return energy;
    }
}
