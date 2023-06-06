import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Basic Actor class that can follow a script.
 * @see Script.
 * Create subclass if you want to extend this.
 */
public class ScriptedActor extends SmoothMover
{
    // TODO: Implement, pause/resume/restart functionalities.
    
    private double speed = 1;
    private Script script;
    private int step;
    private Action action;
    
    public void act() {
        continueScript();
    }
    
    /**
     * Sets script to actor to follow.
     */
    public void setScript(Script script) {
        this.script = script;
        step = 0;
    }
    
    /**
     * Continues or starts script.
     * @returns True if script is finished, false otherwise.
     */
    public boolean continueScript() {
        if (script == null) {
            return true;
        }
        
        if (step >= script.steps()) {
            return true;
        }
        
        if (action == null) {
            action = script.getAction(step);
        }
        
        boolean actionComplete = action.run(this);
        
        if (actionComplete) {
            action = null;
            step++;
        }
        
        return false;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    /**
     * Moves towards position at actor's speed.
     * @return True if position reached, false otherwise.
     */
    public boolean moveTowards(double x, double y) {
        if (distanceTo(x, y) <= speed) {
            setLocation(x, y);
            return true;
        }
        
        int rotation = getRotation();
        turnTowards((int) x, (int) y);
        move(speed);
        setRotation(rotation);
        return false;
    }
    
    private double distanceTo(double x, double y) {
        double diffX = getX() - x;
        double diffY = getY() - y;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }
}
