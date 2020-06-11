import bagel.*;
import bagel.util.Point;
import java.util.List;

public class Tank extends Tower{
    private final double coolDown;
    private double frameCount = Integer.MAX_VALUE;
    private final String projectileFile;

    public Tank(Point p,String name,int radius, double coolDown, int damage){
        super(p,ShadowDefend.getImgPath()+name+".png",radius,damage,name);
        projectileFile = ShadowDefend.getImgPath()+name+"_projectile.png";
        this.coolDown = coolDown;
    }

    public void attack(Input input){
        frameCount+=ShadowDefend.getTimescale();
        Point currentLoc = getCenter();
        if (frameCount/ShadowDefend.getFPS()>=coolDown){
            List<Slicer> targets = Projectile.findTargets(currentLoc,getRadius());
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
