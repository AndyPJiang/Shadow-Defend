import bagel.Input;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;

public class Airplane extends Tower{
    private final double speed = 3;
    private double dropTime;
    private boolean isVertical;
    private double frameCount = 0.0;
    private final String explosiveFile = "res/images/explosive.png";
    private final double windowWidth;
    private final double windowHeight;

    public Airplane(Point p,boolean isVertical){
        super(p,"res/images/airsupport.png",
                200,500,"airsupport");
        dropTime = Math.random() * 3;
        this.isVertical = isVertical;
        windowWidth = Window.getWidth();
        windowHeight = Window.getHeight();
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
            dx = new Vector2(0,speed * ShadowDefend.getTimescale());
            super.setAngle(Math.PI);
        }else{
            dx = new Vector2(speed * ShadowDefend.getTimescale(),0);
            super.setAngle(Math.PI/2);
        }
        super.move(dx);
        super.render(input);

        List<Projectile> projectiles = getProjectiles();
        if (!airplaneInRange() && projectiles.isEmpty()){
            setHasFinished(true);
            System.out.println("finished");
        }
    }
}
