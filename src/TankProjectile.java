import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

public class TankProjectile extends Projectile{
    private final Slicer target;
    private final int SPEED = 10;

    public TankProjectile(Slicer target, String projectileImg, Point pos, int damage){
        super(projectileImg,pos, damage);
        this.target = target;
    }

    @Override
    public void render(Input input){
        Vector2 dest = target.getCenter().asVector();
        Vector2 current = getCenter().asVector();
        Vector2 distance = dest.sub(current);
        super.move(distance.normalised().mul(SPEED * ShadowDefend.getTimescale()));
        super.render(input);
        if (target.getRect().intersects(getCenter())){
            target.decreaseHealth(getDamage());
            setHasFinished(true);;
        }
    }
}
