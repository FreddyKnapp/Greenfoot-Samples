import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Character that can slip off the edge of a platform.
 */
public class SlippingSteamMan extends SteamMan
{
    public SlippingSteamMan(String left, String right, String jump)
    {
        super(left, right, jump);
    }
    
    @Override
    /**
     * Uses getObjectsAtOffset instead of isStandingOnObject to detect whether standing on a platform.
     */
    protected void applyGravity()
    {
        // Assume is in air.
        isInAir = true;
        int height = getImage().getHeight();
        
        // Check whether standing on any objects.
        List<Platform> objects = getObjectsAtOffset(0, height/2, Platform.class);
        for (Platform object : objects)
        {
            // Stop falling.
            deltaY = 0;
            // Not in air.
            isInAir = false;
            // Adjust position to standing exactly on top of object.
            // FIX: Sink in by 1 pixel, so that next time we can detect that character is touching object and gravity is not applied.
            setLocation(getX(), object.getY() - object.getImage().getHeight()/2 - getImage().getHeight()/2 + 1);
            
        }
        
        if (isInAir)    // only true if not touching or standing on any objects.
        {
            // Apply gravity.
            deltaY += gravity;
        }
    }
}
