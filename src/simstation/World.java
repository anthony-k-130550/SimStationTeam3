package simstation;

import mvc.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public abstract class World extends Model {
    public static int SIZE = 500;
    protected int clock = 0;
    protected int alive = 0;
    protected int numAgents = 0;
    protected List<Agent> agents = new LinkedList<Agent>();
    protected ObserverAgent observer;

    public World() {
        super();
        observer = new ObserverAgent();
        observer.setWorld(this);
        populate();
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
        agent.setWorld(this);
    }

    public void startAgents() { //calls start() for each agent in its list of agents
        for (Agent agent : agents) {
            agent.start();
        }
        observer.start();
    }

    public void stopAgents() { //calls stop() for each
        for (Agent agent : agents) {
            agent.stop();
        }
        observer.update(); //make sure the stats are updated before stopping the observer
        observer.stop();
    }

    public void pauseAgents() { //calls pause() for each
        for (Agent agent : agents) {
            agent.pause();
        }
        observer.update(); //make sure the stats are updated before pausing the observer as well
        observer.pause();
    }

    public void resumeAgents() { //calls resume() for each
        for (Agent agent : agents) {
            agent.resume();
        }
        observer.resume();
    }

    public abstract void populate();

    public String getStatus() {
        return "Clock: " + this.clock + ", Alive: " + this.alive + ", Agents: " + this.numAgents;
    }

    public void updateStatistics() {
        this.numAgents = agents.size();
        this.alive = agents.size(); //default implementation
        this.clock++;
    }

    public List<Agent> getAgents() {
        return Collections.unmodifiableList(agents);
    }

    public Agent getNeighbor(Agent caller, int radius) {
        //FIGURE OUT THE X BORDERS
        boolean xWithinBorders = false; //there are two cases, either the borders fit within the screen, or it goes off screen and wraps around
        boolean wrapRight = caller.getX() + radius > World.SIZE;
        boolean wrapLeft = caller.getX() - radius < 0;
        int leftBorder = 0;
        int rightBorder = 0;

        if (wrapRight || wrapLeft) { //leave xWithinBorders as false
            if (wrapRight) {
                leftBorder = caller.getX() - radius;
                rightBorder = (caller.getX() + radius) % World.SIZE; //wrap around
            } else if (wrapLeft) {
                leftBorder = World.SIZE + (caller.getX() - radius); //since caller.getX() - radius is a negative number
                rightBorder = caller.getX() + radius;
            }
        } else {
            xWithinBorders = true;
            leftBorder = caller.getX() - radius;
            rightBorder = caller.getX() + radius;
        }

        //FIGURE OUT THE Y BORDERS
        boolean yWithinBorders = false;
        boolean wrapTop = caller.getY() - radius < 0;
        boolean wrapBottom = caller.getY() + radius > World.SIZE;
        int topBorder = 0;
        int bottomBorder = 0;

        if (wrapTop || wrapBottom) {
            if (wrapTop) {
                topBorder = World.SIZE + (caller.getY() - radius);
                bottomBorder = caller.getY() - radius;
            } else if (wrapBottom) {
                topBorder = caller.getY() - radius;
                bottomBorder = (caller.getY() + radius) % World.SIZE;
            }
        } else {
            yWithinBorders = true;
            topBorder = caller.getY() - radius;
            bottomBorder = caller.getY() + radius;
        }

        int randomIndex = Utilities.rng.nextInt(agents.size() + 1);
        Iterator<Agent> it = agents.iterator();

        //effectively chooses a random Agent from the batch to start
        for (int i = 0; i < randomIndex; i++) {
            Agent agent = it.next();
        }

        Agent neighbor = null;
        Agent temp = null;
        for (int i = 0; i < agents.size(); i++) {
            if (it.hasNext()) {
                temp = it.next();
            } else {
                it = agents.iterator();
                temp = it.next();
            }
            //check to see if temp fits within x borders
            boolean tempXWithinBorders = false;
            if (xWithinBorders) {
                if (temp.getX() > leftBorder && temp.getX() < rightBorder) {
                    tempXWithinBorders = true;
                }
            } else {
                if ((temp.getX() > leftBorder && temp.getX() < World.SIZE)||(temp.getX() > 0 && temp.getX() < rightBorder)) {
                    tempXWithinBorders = true;
                }
            }
            boolean tempYWithinBorders = false;
            if (yWithinBorders) {
                if (temp.getY() > topBorder && temp.getY() < bottomBorder) {
                    tempYWithinBorders = true;
                }
            } else {
                if ((temp.getY() > topBorder && temp.getY() < World.SIZE) || (temp.getY() > 0 && temp.getY() < bottomBorder)) {
                    tempYWithinBorders = true;
                }
            }
            if (tempXWithinBorders && tempYWithinBorders) {
                neighbor = temp;
                return neighbor;
            }
        }
        return null;
    }

    public boolean hasStarted() { //functionality for save and saveAs
        Agent temp = agents.get(0); //get any agent, they are all uniform in terms of starting
        Thread tempThread = temp.getMyThread();

        return (tempThread.getState() != Thread.State.NEW); //returns true if started, false if hasn't
    }

    public void rethread() { //recreate the threads that are lost when saving a model
        for (Agent agent: agents) {
            agent.setMyThread(new Thread(agent));
        }
    }
}
