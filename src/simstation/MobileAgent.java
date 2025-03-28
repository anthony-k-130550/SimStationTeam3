package simstation;

public abstract class MobileAgent extends Agent {
    Heading heading;

    public MobileAgent() {
        super();
    }

    public void update() {
        Heading newHeading = Heading.random();
        this.move(15);
        world.changed();
    }
    public void turn(Heading dir) {
        heading = dir;
    }
    public void move(int steps) {
        switch (heading) {
            case NORTH:
                if (this.getY() - steps < 0) {
                    this.setY(World.SIZE + (this.getY() - steps));
                } else {
                    this.setY(this.getY() - steps);
                }
                break;

            case SOUTH:
                if (this.getY() + steps > World.SIZE) {
                    this.setY((this.getY() + steps) % World.SIZE);
                } else {
                    this.setY(this.getY() + steps);
                }
                break;

            case WEST:
                if (this.getX() - steps < 0) {
                    this.setX(World.SIZE + (this.getX() - steps));
                } else {
                    this.setX(this.getX() - steps);
                }
                break;

            case EAST:
                if (this.getX() + steps > World.SIZE) {
                    this.setX((this.getX() + steps) % World.SIZE);
                } else {
                    this.setX(this.getX() + steps);
                }
                break;
        }
        System.out.println(this.getAgentName() + " moved " + "( " + this.getX() + ", " + this.getY() + ")");
    }
}
