import bagel.*;
import bagel.util.Point;
import java.util.List;

/**
 * Tank Defence
 */
public class Tank extends Tower{
    private final double coolDown;
    private double frameCount = Integer.MAX_VALUE;
    private final String projectileFile;

    /**
     * Creates a new tank/supertank
     * @param p position of the tank
     * @param name the type of the tank as a string
     * @param radius the effect radius
     * @param coolDown cool down between consecutive attacks
     * @param damage the damage of each projectile it fires to each enemy slicer
     */
    public Tank(Point p,String name,int radius, double coolDown, int damage){
        super(p,ShadowDefend.getImgPath()+name+".png",radius,damage,name);
        projectileFile = ShadowDefend.getImgPath()+name+"_projectile.png";
        this.coolDown = coolDown;
    }

    /**
     * Attack enemy slicers. The tank fires a projectile at a target slicer.
     * It follows the slicer until it hits.
     * @param input The current mouse/keyboard state
     */
    public void attack(Input input){
        frameCount+=ShadowDefend.getTimescale();
        Point currentLoc = getCenter();
        if (frameCount/ShadowDefend.getFPS()>=coolDown){
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
}
