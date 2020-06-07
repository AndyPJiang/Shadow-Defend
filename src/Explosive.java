import bagel.Input;
import bagel.util.Point;

import java.util.List;

public class Explosive extends Projectile{

    private int detonationTime = 2;
    private double frameCount = 0.0;
    private final double radius = 200;
    public Explosive(Point pos){
        super("res/images/explosive.png",pos, 500);
    }

    private void detonate(){
        List<Slicer> targets = findTargets(getCenter(),radius);
        for (Slicer s : targets){
            s.decreaseHealth(getDamage());
        }

    }

    @Override
    public void render(Input input){
        super.render(input);
        frameCount+=ShadowDefend.getTimescale();
        if (frameCount/ShadowDefend.getFPS()>=detonationTime){
            detonate();
            setHasFinished(true);
            frameCount = 0.0;
        }
    }


}
