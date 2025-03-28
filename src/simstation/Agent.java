package simstation;

import java.io.Serializable;
import java.util.Random;

import mvc.Utilities;

public abstract class Agent implements Runnable, Serializable {
    private int xc;
    private int yc;
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

    public void start() {
        myThread.start();
    }

    public void stop() {
        stopped = true;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public abstract void update();

    public void run() {
        while (!paused && !stopped) {
            update();
            try {
                Thread.sleep(20); // be cooperative
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void join() throws InterruptedException {
        if (myThread != null) {myThread.join();}
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
