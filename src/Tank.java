import bagel.*;
import bagel.util.Point;
import java.util.List;

/**
 * Tank Defence
 */
public class Tank extends Tower{
    private static final String NAME = "tank";
    private static final double RADIUS = 100;
    private static final int DAMAGE = 1;
    private static final double COOL_DOWN = 1;
    private static final int TANK_PRICE = 250;
    private double frameCount = Integer.MAX_VALUE;
    private final String projectileFile;

    /**
     * Creates a new tank
     * @param p position of the tank
     */
    public Tank(Point p){
        super(p,ShadowDefend.getImgPath()+NAME+".png",RADIUS,DAMAGE,NAME,TANK_PRICE);
        this.projectileFile = ShadowDefend.getImgPath()+NAME+"_projectile.png";
    }


    /**
     * @return the tank price
     */
    public static int getPrice() {
        return TANK_PRICE;
    }

    /**
     * Attack enemy slicers. The tank fires a projectile at a target slicer.
     * It follows the slicer until it hits.
     * @param input The current mouse/keyboard state
     */
    public void attack(Input input){
        frameCount+=ShadowDefend.getTimescale();
        Point currentLoc = getCenter();
        if (frameCount/ShadowDefend.getFPS()>=COOL_DOWN){
            List<Slicer> targets = findTargets(currentLoc,getRadius());
            if (targets.size()>0){
                Slicer target = targets.get(0);
                Point targetLoc = target.getCenter();
                addProjectile(new TankProjectile(target,projectileFile,currentLoc,getDamage()));
                super.setAngle(Math.atan2(targetLoc.y-currentLoc.y, targetLoc.x-currentLoc.x)+Math.PI/2);
                frameCount = 0.0;
            }
        }
        super.updateProjectiles(input);
    }

    /**
     * @param p position of the tower
     * @return a new instance of the tank class
     */
    public Tower newTower(Point p){
        return new Tank(p);
    }
}
