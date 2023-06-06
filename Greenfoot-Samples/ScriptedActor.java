import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScriptedActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScriptedActor extends SmoothMover
{
    private double speed = 2;
    private Script script;
    
    public void act() {
        moveTowards(300, 300);
    }
    
    public void addScript(Script script) {
        this.script = script;
    }
    
    public boolean checkCompleteAction() {
        return false;
    }
    
    public boolean continueAction() {
        if (!checkCompleteAction()) {
        
        }
        
        return false;
    }
    
    
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
