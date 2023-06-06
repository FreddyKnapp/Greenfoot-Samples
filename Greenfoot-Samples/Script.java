import java.util.ArrayList;
import java.util.List;

/**
 * Class to store list of actions for a ScripteActor object to perform.
 */
public class Script  
{
    private List<Action> actions;
    
    public Script() {
        actions = new ArrayList<Action>();
    }
    
    public void add(Action action) {
        actions.add(action);
    }
    
    /**
     * Number of steps (i.e. actions) in this script.
     * @return Number of steps (actions).
     */
    public int steps() {
        return actions.size();
    }
    
    /**
     * Returns action at specified step number.
     * @param step Step number.
     * @returns Action.
     */
    public Action getAction(int step) {
        if (step < 0 || step >= steps())
            throw new IndexOutOfBoundsException(step);
        
        return actions.get(step);
    }
}
