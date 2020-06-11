import bagel.Input;
import bagel.util.Point;
import java.util.List;

public class Explosive extends Projectile{

    private final int DETONATION_TIME = 2;
    private double frameCount = 0.0;
    private final double RADIUS = 200;
    public Explosive(Point pos){
        super(ShadowDefend.getImgPath()+"explosive.png",pos, 500);
    }

    private void detonate(){
        List<Slicer> targets = findTargets(getCenter(),RADIUS);
        for (Slicer s : targets){
            s.decreaseHealth(getDamage());
        }

    }

    @Override
    public void render(Input input){
        super.render(input);
        frameCount+=ShadowDefend.getTimescale();
        if (frameCount/ShadowDefend.getFPS()>=DETONATION_TIME){
            detonate();
            setHasFinished(true);
            frameCount = 0.0;
        }
    }


}
