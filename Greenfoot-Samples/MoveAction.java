/**
 * Simple movement/waypoint action, to move to a specified position.
 */
public class MoveAction extends Action {
    private double x;
    private double y;
    
    public MoveAction(double destinationX, double destinationY) {
        x = destinationX;
        y = destinationY;
    }
    
    @Override
    public boolean run(ScriptedActor actor) {
        return actor.moveTowards(x, y);
    }
}
