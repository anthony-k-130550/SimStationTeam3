package simstation;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

import mvc.Utilities;

public abstract class Agent implements Runnable, Serializable {
    protected int xc;
    protected int yc;
    private boolean paused = false;
    private boolean stopped = false;
    private String agentName;
    transient protected Thread myThread;
    protected World world;

    public Agent() {
        myThread = new Thread(this);

        //random x,y, and names
        this.xc = Utilities.rng.nextInt(World.SIZE) + 1;
        this.yc = Utilities.rng.nextInt(World.SIZE) + 1;
        agentName = new String ("Agent-("+xc+", "+yc+")");
    }

    public Agent(World world) {
        myThread = new Thread(this);
        this.world = world;

        //random x,y, and names
        this.xc = Utilities.rng.nextInt(World.SIZE) + 1;
        this.yc = Utilities.rng.nextInt(World.SIZE) + 1;
        agentName = new String ("Agent-("+xc+", "+yc+")");
    }

    public void start() {
        myThread.start();
    }

    public void stop() {
        stopped = true;
    }

    public synchronized void pause() {
        paused = true;
        notify();
    }

    public synchronized void resume() {
        paused = false;
        notify();
    }

    public abstract void update();

    public void run() {
        while (!stopped) {
            synchronized (this) {
                while (paused) { //I'm using synchronization here because I'm waiting for the paused condition to change
                    try {
                        wait();
                    } catch (InterruptedException e) {

                    }
                }
            }

            update();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {

            }
        }
    }

    //getters
    public int getX() {
        return xc;
    }
    public void setX(int x) {
        this.xc = x;
    }
    public int getY() {
        return yc;
    }
    public void setY(int y) {
        this.yc = y;
    }
    public String getAgentName() {
        return agentName;
    }
    public Thread getMyThread() {
        return myThread;
    }
    public boolean getPaused() {
        return paused;
    }
    public boolean getStopped() {
        return stopped;
    }
    public void setWorld(World newWorld) {
        this.world = newWorld;
    }
}
