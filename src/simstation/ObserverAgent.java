package simstation;

public class ObserverAgent extends Agent {
    public void update() {
        world.updateStatistics();
    }
}
