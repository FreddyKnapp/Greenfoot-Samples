public class MoveAction extends Action {
    private int x;
    private int y;
    
    public MoveAction(int destinationX, int destinationY) {
        x = destinationX;
        y = destinationY;
    }
    
    @Override
    public void run(ScriptedActor actor) {
        actor.moveTowards(x, y);
    }
}
