import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;


/**
 * Airsupport Defence
 */

public class Airplane extends Tower{
    private final double SPEED = 3;
    // how long to wait to drop the next explosive
    private double dropTime;
    private final boolean isVertical;
    private double frameCount = 0.0;
    private final double windowWidth;
    private final double windowHeight;


    /**
     * creates a new airplane
     * @param p the position of the airplane
     * @param isVertical whether the airplane will fly vertical or horizontal
     */
    public Airplane(Point p,boolean isVertical){
        super(p,ShadowDefend.getImgPath()+"airsupport.png",
                200,500,"airsupport");
        // randomly choose drop time between 0 and 3 seconds
        dropTime = Math.random() * 3;
        this.isVertical = isVertical;
        windowWidth = ShadowDefend.getWidth();
        windowHeight = ShadowDefend.getHeight();
    }

    /**
     * Attack enemy slicers. The airplane drops explosives that deal
     * damage to all enemies within a given radius
     * @param input The current mouse/keyboard state
     */
    public void attack(Input input){
        frameCount+=ShadowDefend.getTimescale();
        if (frameCount/ShadowDefend.getFPS()>=dropTime && airplaneInRange()){
            // create a new explosive if wait time is over
            addProjectile(new Explosive(getCenter()));
            // randomly choose drop time between 0 and 3 seconds again
            dropTime = Math.random() * 3;
            frameCount = 0.0;
        }

        // update each explosive
        super.updateProjectiles(input);
    }


    /**
     * @return if airplane is outside the window
     */
    private boolean airplaneInRange(){
        if (getCenter().x > windowWidth || getCenter().y > windowHeight){
            return false;
        }
        return true;
    }


    /**
     * Updates the position of the airplane and renders it.
     * The airplane flies in a straight line either horizontally or vertically
     * @param input The current mouse/keyboard state
     */
    @Override
    public void render(Input input){
        Vector2 dx;
        if (isVertical){
            // plane flies vertically, so the x position doesn't change
            dx = new Vector2(0,SPEED * ShadowDefend.getTimescale());
            super.setAngle(Math.PI);
        }else{
            // plane flies horizontally, so the y position doesn't change
            dx = new Vector2(SPEED * ShadowDefend.getTimescale(),0);
            super.setAngle(Math.PI/2);
        }
        super.move(dx);
        super.render(input);

        // airplane has finished if it has flown out of the window and all explosives have detonated
        List<Projectile> projectiles = getProjectiles();
        if (!airplaneInRange() && projectiles.isEmpty()){
            setHasFinished(true);
        }
    }
}
