/**
 * Template superclass for all action classes.
 * Create a subclass of this to define a new type of action.
 * Override the run method to specify what your action makes an actor do.
 */
public class Action  
{
    /**
     * Makes actor perform the corresponding action.
     * @params actor The actor to perform the action.
     * @returns True if action is complete.
     */
    public boolean run(ScriptedActor actor) {
        return true;
    }
}
