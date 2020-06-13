import bagel.Input;
import bagel.util.Point;
import java.util.List;


public class SuperTank extends Tower{
    private static final String NAME = "supertank";
    private static final double RADIUS = 150;
    private static final int DAMAGE = 3;
    private static final double COOL_DOWN = 0.5;
    private static final int SUPER_TANK_PRICE = 600;
    private double frameCount = Integer.MAX_VALUE;
    private final String projectileFile;
    /**
     * Creates a new supertank
     * @param p position of the tank
     */
    public SuperTank(Point p){
        super(p,ShadowDefend.getImgPath()+NAME+".png", RADIUS,DAMAGE,NAME,SUPER_TANK_PRICE);
        projectileFile = ShadowDefend.getImgPath()+NAME+"_projectile.png";
    }


    /**
     * @return the supertank price
     */
    public static int getPrice() {
        return SUPER_TANK_PRICE;
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
     * @return a new instance of the supertank class
     */
    public Tower newTower(Point p){
        return new SuperTank(p);
    }
}
