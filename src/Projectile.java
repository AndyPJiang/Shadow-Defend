import bagel.Input;
import bagel.util.Point;

/**
 * A projectile
 */

public abstract class Projectile extends Sprite{
    private boolean hasFinished = false;
    private final int damage;

    /**
     * Create a new projectile
     * @param projectileImg the path to the image of the projectile
     * @param position the position of the projectile where it was fired
     * @param damage the damage the projectile does to each enemy slicer
     */
    public Projectile(String projectileImg, Point position, int damage){
        super(position,projectileImg);
        this.damage = damage;
    }

    /**
     * @return whether projectile has hit its target
     */
    public boolean getHasFinished() {
        return hasFinished;
    }

    /**
     * Set whether projectile has hit its target
     * @param hasFinished if projectile has hit
     */
    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    /**
     * @return the damage the projectile does
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Render the projectile
     * @param input The current mouse/keyboard state
     */
    public void render(Input input){
        super.update(input);
    }
}
