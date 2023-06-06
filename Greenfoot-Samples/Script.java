import java.util.List;
import java.util.ArrayList;

public class Script  
{
    private List<Action> actions;
    
    public Script() {
        actions = new ArrayList<Action>();
    }
    
    public boolean hasNextAction() {
        return actions.size() > 0;
    }
    
    public Action getNextAction() {
        return actions.remove(actions.size() - 1);
    }
}
