import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A tower
 */

public abstract class Tower extends Sprite{
    private final double radius;
    private final int damage;
    private final List<Projectile> projectiles;
    private final String name;
    private final int price;
    private boolean hasFinished = false;

    /**
     * Create a new tower
     * @param p position of the tower
     * @param imageFile path to the tower image as a string
     * @param radius effect radius of the tower
     * @param damage the damage the tower does to a slicer
     * @param name the type of tower as a string
     */
    public Tower(Point p, String imageFile, double radius, int damage, String name, int price) {
        super(p,imageFile);
        this.radius = radius;
        this.damage = damage;
        this.projectiles = new ArrayList<>();
        this.name = name;
        this.price = price;
    }

    /**
     * abstract method to attack enemies
     * @param input The current mouse/keyboard state
     */
    public abstract void attack(Input input);


    /**
     * @param p position of the tower
     * @return abstract method to return a new instance of the class
     */
    public abstract Tower newTower(Point p);



    /**
     * @return the radius of the tower
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @return the damage of the tower
     */
    public int getDamage() {
        return damage;
    }

    /**
     * add a new projectile
     * @param p the projectile to add
     */
    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }

    /**
     * @return all the projectiles currently firing
     */
    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * Render the tower
     * @param input The current mouse/keyboard state
     */
    public void render(Input input){
        super.update(input);
    }

    /**
     *
     * @return whether tower has finished. This only applies to airsupport
     * when it flies out of the window
     */
    public boolean getHasFinished() {
        return hasFinished;
    }

    /**
     * Set whether tower has finished
     * @param hasFinished whether tower has finished
     */
    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    /**
     * @return price to purchase the tower
     */
    public int getTowerPrice() {
        return price;
    }

    /**
     * Update the position of all the tower's projectiles.
     * @param input The current mouse/keyboard state
     */
    public void updateProjectiles(Input input){
        for (int i=projectiles.size()-1;i>=0;i--){
            Projectile p = projectiles.get(i);
            p.render(input);
            if (p.getHasFinished()){
                projectiles.remove(i);
            }
        }
    }

    /**
     * @return name of tower as a string
     */
    public String getName() {
        return name;
    }

    /**
     * @param p the position of the tower
     * @param radius the attack radius of the tower
     * @return all the slicers that are in attack range of a tower
     */
    public static List<Slicer> findTargets(Point p, double radius){
        List<Slicer> targets = new ArrayList<>();
        List<Slicer> slicers = ShadowDefend.getSlicers();
        for (Slicer s : slicers){
            if (p.distanceTo(s.getCenter())<=radius){
                targets.add(s);
            }
        }
        return targets;
    }
}
