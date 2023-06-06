import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        
        Script script = new Script();
        script.add(new MoveAction(50, 50));
        script.add(new MoveAction(50, 350));
        script.add(new MoveAction(350, 350));
        script.add(new MoveAction(350, 50));
        script.add(new MoveAction(50, 50));
        
        ScriptedActor actor = new ScriptedActor();
        actor.setSpeed(7.0312);
        actor.setScript(script);
        
        addObject(actor, 0, 0);
    }
}
