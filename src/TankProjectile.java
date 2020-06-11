import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * A tank projectile
 */

public class TankProjectile extends Projectile{
    private final Slicer target;
    private final int SPEED = 10;

    /**
     * Fire a new tank projectile
     * @param target  slicer the projectile is targeting
     * @param projectileImg the path to the projectile image as a string
     * @param pos the position that the projectile is fired from
     * @param damage the damage the projectile does to a slicer
     */
    public TankProjectile(Slicer target, String projectileImg, Point pos, int damage){
        super(projectileImg,pos, damage);
        this.target = target;
    }

    /**
     * Update the position of the projectile. The projectile travels until it hits
     * the target slicer.
     * @param input The current mouse/keyboard state
     */
    @Override
    public void render(Input input){
        // find the target of the projectile
        Vector2 dest = target.getCenter().asVector();
        // find the current position of the projectile
        Vector2 current = getCenter().asVector();
        // find the distance between the target and current position
        Vector2 distance = dest.sub(current);
        // move the projectile in the direction of the of the target, taking into account the timescale and speed
        super.move(distance.normalised().mul(SPEED * ShadowDefend.getTimescale()));
        super.render(input);

        // Decrease target slicer health if it gets hit by projectile
        if (target.getRect().intersects(getCenter())){
            target.decreaseHealth(getDamage());
            // flag has hit
            setHasFinished(true);
        }
    }
}
