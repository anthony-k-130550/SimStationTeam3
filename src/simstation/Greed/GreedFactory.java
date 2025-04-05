package simstation.Greed;
import simstation.*;
import mvc.*;

public class GreedFactory extends WorldFactory{
    public Model makeModel() {
        return new Meadow();
    }
    public View makeView(Model model) { return new MeadowView(model); }
    public String getTitle() {
        return "Greed";
    }
}
