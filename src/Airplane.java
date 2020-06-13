import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;


/**
 * Airsupport Defence
 */

public class Airplane extends Tower{
    private final double SPEED = 3;
    private static final double RADIUS = 200;
    private static final int DAMAGE = 500;
    private static final int PLANE_PRICE = 500;


    // maximum and minimum time to wait between successive drops
    private final int MAX_DROP_TIME = 2;
    private final int MIN_DROP_TIME = 1;
    // how long to wait to drop the next explosive
    private double dropTime;
    private final boolean isVertical;
    private double frameCount = 0.0;
    private final double windowWidth;
    private final double windowHeight;


    /**
     * creates a new airplane
     * @param p the position of the airplane
     */
    public Airplane(Point p){
        super(p,ShadowDefend.getImgPath()+"airsupport.png",
                RADIUS, DAMAGE,"airsupport", PLANE_PRICE);
        // randomly choose drop time between 0 and 3 seconds
        dropTime = Math.random() * (MAX_DROP_TIME-MIN_DROP_TIME) + MIN_DROP_TIME;
        this.isVertical = ShadowDefend.getAirplaneIsVertical();
        windowWidth = ShadowDefend.getWidth();
        windowHeight = ShadowDefend.getHeight();
    }

    /**
     * @return the airplane price
     */
    public static int getPrice() {
        return PLANE_PRICE;
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
            // randomly choose drop time between 1 and 2 seconds again
            dropTime = Math.random() * (MAX_DROP_TIME-MIN_DROP_TIME) + MIN_DROP_TIME;
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

    /**
     * @param p position of the airplane
     * @return a new instance of the airplane class
     */
    public Tower newTower(Point p){
        boolean orientation = ShadowDefend.getAirplaneIsVertical();
        // spawn airplane outside of window
        int start = -100;
        Airplane plane;

        if (!orientation){
            plane = new Airplane(new Point(start,p.y));
        }else{
            plane = new Airplane(new Point(p.x,start));
        }

        // alternate between flight directions
        ShadowDefend.setAirplaneIsVertical(!orientation);
        return plane;
    }
}
