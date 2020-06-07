import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.ArrayList;
import java.util.List;

public abstract class Projectile extends Sprite{
    private boolean hasFinished = false;
    private final int damage;

    public Projectile(String projectileImg, Point position, int damage){
        super(position,projectileImg);
        this.damage = damage;
    }

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

    public boolean getHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public int getDamage() {
        return damage;
    }

    public void render(Input input){
        super.update(input);
    }
}
