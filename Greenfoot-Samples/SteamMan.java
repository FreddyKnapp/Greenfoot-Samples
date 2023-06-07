import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Steam Man player character.
 */
public class SteamMan extends Actor
{
    /**
     * Constants for amount to sink into solid objects by.
     */
    private static final int X_SINK_AMOUNT = 3;
    private static final int Y_SINK_AMOUNT = 1;
    
    /**
     * Animation codes. Used to keep track of which animation is currently running.
     */
    private static final int ANIMATION_IDLE = 0;
    private static final int ANIMATION_RUN = 1;
    private static final int ANIMATION_JUMP = 2;
    private static final int ANIMATION_ATTACK = 3;
    
    /**
     * Movement variables.
     */
    protected double gravity = 1;
    protected int deltaX = 0;
    protected int deltaY = 0;
    private int speedX = 5;
    private int jumpSpeed = 20;
    protected boolean isInAir = true;
    private boolean isFacingRight = true;
    
    /**
     * Animation variables.
     */
    private int animationInterval = 5;  // Number of frames between animation images.
    private int frameCounter = 0;       // Frame counter. For animations.
    private int currentAnimation = ANIMATION_IDLE; // Used to keep track of which animation is currently running.
    
    /**
     * Static images for this character.
     */
    private static GreenfootImage[][] imagesRun;
    private static GreenfootImage[][] imagesIdle;
    private static GreenfootImage[][] imagesJump;
    private static GreenfootImage[][] imagesAttack;
    private static boolean imagesAreLoaded = false;     // Whether images have been loaded into the class.
    
    /**
     * Movement keys
     */
    private String leftKey;
    private String rightKey;
    private String jumpKey;
    
    public SteamMan(String left, String right, String jump)
    {
        loadImages();
  
        leftKey = left;
        rightKey = right;
        jumpKey = jump;
    }
    
    /**
     * Act - do whatever the SteamMan wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        movementControls();
        applyGravity();
        collisions();
        animate();
    }
    
    /**
     * Key controls and movement updates.
     */
    private void movementControls()
    {
        deltaX = 0;
        
        if (Greenfoot.isKeyDown(leftKey))
        {
            deltaX = deltaX - speedX;
        }
        
        if (Greenfoot.isKeyDown(rightKey))
        {
            deltaX = deltaX + speedX;
        }
        
        if (!isInAir && Greenfoot.isKeyDown(jumpKey))   // Jump.
        {
            deltaY = -jumpSpeed;
        }
        
        // Remember whether facing right or left.
        if (deltaX > 0)
        {
            isFacingRight = true;
        }
        else if (deltaX < 0)
        {
            isFacingRight = false;
        }
        
        // Update position.
        setLocation(getX() + deltaX, getY() + deltaY);
    }
    
    /**
     * Check whether standing on objects, and apply gravity.
     */
    protected void applyGravity()
    {
        // Assume is in air.
        isInAir = true;
        
        if (deltaY >= 0)    // if falling (as opposed to rising)
        {
            // Check whether standing on any objects.
            List<Platform> objects = getIntersectingObjects(Platform.class);
            for (Platform object : objects)
            {
                if (isStandingOnObject(object))
                {
                    // Stop falling.
                    deltaY = 0;
                    // Not in air.
                    isInAir = false;
                    // Adjust position to standing exactly on top of object.
                    // FIX: Sink in by 1 pixel, so that next time we can detect that character is touching object and gravity is not applied.
                    setLocation(getX(), object.getY() - object.getImage().getHeight()/2 - getImage().getHeight()/2 + Y_SINK_AMOUNT);
                    break;
                }
            }
        }
        
        if (isInAir)    // only true if not touching or standing on any objects.
        {
            // Apply gravity.
            deltaY += gravity;
        }
    }
    
    /**
     * Checks whether above top of specified object.
     * Sinking into object by at most Y_SINK_LIMIT is counts as standing on top of it.
     */
    private boolean isStandingOnObject(Actor object)
    {
        int height = getImage().getHeight();
        int bottomY = getY() + height / 2;
        int objectHeight = object.getImage().getHeight();
        int objectTopY = object.getY() - objectHeight / 2;
        
        //return bottomY <= objectTopY + Y_SINK_LIMIT_FACTOR * deltaY + Y_SINK_AMOUNT
        return bottomY - deltaY <= objectTopY + Y_SINK_AMOUNT;
    }
    
    /**
     * Checks collisions.
     */
    private void collisions()
    {
        int width = getImage().getWidth();
        int leftX = getX() - width / 2;
        int rightX = getX() + width / 2;
        
        List<SolidObject> objects = getIntersectingObjects(SolidObject.class);
        for (SolidObject object : objects)
        {
            if (isStandingOnObject(object))
            {
                continue;   // Standing behaviour is handled by gravity.
            }
            
            // Otherwise,
            int objectWidth = object.getImage().getWidth();
            int objectLeftX = object.getX() - objectWidth / 2;
            int objectRightX = object.getX() + objectWidth / 2;
            if (getX() < object.getX())    // To the left of touching object.
            {
                setLocation(objectLeftX - width/2 + X_SINK_AMOUNT, getY());   // Sink into object left edge.
            }
            else if (getX() > object.getX())  // To the right of touching object.
            {
                setLocation(objectRightX + width/2 - X_SINK_AMOUNT, getY());  // Sink into object right edge.
            }
        }
    }
    
    /**
     * Animates according to state.
     */
    private void animate()
    {
        if (isInAir)    // if in air, either jumping or falling
        {
            animate(imagesJump, ANIMATION_JUMP, false);
        }
        else if (deltaX != 0) // if not in air but is moving
        {
            animate(imagesRun, ANIMATION_RUN);
        }
        else // otherwise, not moving at all
        {
            animate(imagesIdle, ANIMATION_IDLE);
        }
    }
    
    /**
     * Animates by looping specified images.
     * If facing right, imageSets[0] will be used, otherwise imageSets[1].
     */
    private void animate(GreenfootImage[][] imageSets, int animationCode)
    {
        animate(imageSets, animationCode, true);
    }
    
    /**
     * Animates using specified images.
     * If facing right, imageSets[0] will be used, otherwise imageSets[1].
     * @param imageSets     - animation images to be used
     * @param animationCode - animation code for the specified animation images.
     * @param loop          - whether to loop the images.
     */
    private void animate(GreenfootImage[][] imageSets, int animationCode, boolean loop)
    {        
        GreenfootImage[] images = isFacingRight ? imageSets[0] : imageSets[1];
        
        if (currentAnimation != animationCode)
        {
            // Remember current animation and reset frame counter.
            currentAnimation = animationCode;
            frameCounter = 0;
        }
        
        if (frameCounter >= images.length * animationInterval)  // If a whole loop of the animation has completed.
        {
            if (!loop)
            {
                // Non-looping animation is completed. Do nothing.
                return;
            }
            
            // Otherwise, reset frame counter to restart animation.
            frameCounter = 0;
        }
        
        if (frameCounter % animationInterval == 0)  // If it's time to switch to next animation image.
        {
            setImage(images[frameCounter/animationInterval]);
        }
        
        frameCounter++;
    }
    
    /**
     * Load static images used for animations.
     */
    private void loadImages()
    {
        if (imagesAreLoaded)
        {
            return;
        }

        imagesRun = new GreenfootImage[2][6];
        imagesIdle = new GreenfootImage[2][4];
        imagesJump = new GreenfootImage[2][6];
        imagesAttack = new GreenfootImage[2][6];
        
        for (int i = 0; i < imagesRun[0].length; i++)
        {
            imagesRun[0][i] = new GreenfootImage("3 SteamMan/Run/tile" + i + ".png");
            imagesRun[1][i] = new GreenfootImage("3 SteamMan/Run/tile" + i + ".png");
            imagesRun[1][i].mirrorHorizontally();
        }
        for (int i = 0; i < imagesIdle[0].length; i++)
        {
            imagesIdle[0][i] = new GreenfootImage("3 SteamMan/Idle/tile" + i + ".png");
            imagesIdle[1][i] = new GreenfootImage("3 SteamMan/Idle/tile" + i + ".png");
            imagesIdle[1][i].mirrorHorizontally();
        }
        for (int i = 0; i < imagesJump[0].length; i++)
        {
            imagesJump[0][i] = new GreenfootImage("3 SteamMan/Jump/tile" + i + ".png");
            imagesJump[1][i] = new GreenfootImage("3 SteamMan/Jump/tile" + i + ".png");
            imagesJump[1][i].mirrorHorizontally();
        }
        for (int i = 0; i < imagesAttack[0].length; i++)
        {
            imagesAttack[0][i] = new GreenfootImage("3 SteamMan/Attack2/tile" + i + ".png");
            imagesAttack[1][i] = new GreenfootImage("3 SteamMan/Attack2/tile" + i + ".png");
            imagesAttack[1][i].mirrorHorizontally();
        }
        
        imagesAreLoaded = true;
    }
}
