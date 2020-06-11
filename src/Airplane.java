import bagel.Input;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;

public class Airplane extends Tower{
    private final double SPEED = 3;
    private double dropTime;
    private final boolean isVertical;
    private double frameCount = 0.0;
    private final double windowWidth;
    private final double windowHeight;

    public Airplane(Point p,boolean isVertical){
        super(p,ShadowDefend.getImgPath()+"airsupport.png",
                200,500,"airsupport");
        dropTime = Math.random() * 3;
        this.isVertical = isVertical;
        windowWidth = ShadowDefend.getWidth();
        windowHeight = ShadowDefend.getHeight();
    }

    public void attack(Input input){
        frameCount+=ShadowDefend.getTimescale();
        if (frameCount/ShadowDefend.getFPS()>=dropTime && airplaneInRange()){
            addProjectile(new Explosive(getCenter()));
            dropTime = Math.random() * 3;
            frameCount = 0.0;
        }
        super.updateProjectiles(input);
    }

    private boolean airplaneInRange(){
        if (getCenter().x > windowWidth || getCenter().y > windowHeight){
            return false;
        }
        return true;
    }

    @Override
    public void render(Input input){
        Vector2 dx;
        if (isVertical){
            dx = new Vector2(0,SPEED * ShadowDefend.getTimescale());
            super.setAngle(Math.PI);
        }else{
            dx = new Vector2(SPEED * ShadowDefend.getTimescale(),0);
            super.setAngle(Math.PI/2);
        }
        super.move(dx);
        super.render(input);

        List<Projectile> projectiles = getProjectiles();
        if (!airplaneInRange() && projectiles.isEmpty()){
            setHasFinished(true);
        }
    }
}
