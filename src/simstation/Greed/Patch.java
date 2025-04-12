package simstation.Greed;
import simstation.*;

public class Patch extends Agent {
    protected int energy = 100;
    public static int growBackRate = 1;
    public static int patchSize = 10; //i might have to change this to non-static according to UML

    public synchronized void update() {
        while (this.energy >= 100) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        this.energy = Math.min(this.energy + growBackRate, 100);
        notifyAll();
        this.world.changed();
    }

    public synchronized void eatMe(Cow cow, int amt) {
        while (this.energy < amt) {
            try {
                cow.setEnergy(cow.getEnergy()-((Meadow)world).getWaitPenalty());
                if (cow.getEnergy() <= 0) {
                    cow.stop();
                    notifyAll();
                    this.world.changed();
                    return;
                } else {
                    wait();
                }
            } catch (InterruptedException e) {

            }
        }
        this.energy = this.energy - amt;
        cow.setEnergy(Math.min(cow.getEnergy() + amt, 100));
        notifyAll();
        this.world.changed();
    }

    public synchronized int getEnergy() {
        return energy;
    }
}
