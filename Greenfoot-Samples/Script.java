import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class to store list of actions for a ScripteActor object to perform.
 */
public class Script  
{
    private Queue<Action> actions;
    
    public Script() {
        actions = new LinkedList<Action>();
    }
    
    public void add(Action action) {
        actions.add(action);
    }
    
    public boolean hasNextAction() {
        return actions.size() > 0;
    }
    
    public Action getNextAction() {
        return actions.remove();
    }
}
