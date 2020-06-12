import bagel.Input;
import bagel.util.Point;
import java.util.List;

/**
 * An explosive projectile fired by airsupport
 */

public class Explosive extends Projectile{
    // time to wait before explosive explodes
    private final int DETONATION_TIME = 2;
    private double frameCount = 0.0;
    private final double RADIUS = 200;

    /**
     * Drop a new explosive on the map
     * @param pos the position on the map to drop it at
     */
    public Explosive(Point pos){
        super(ShadowDefend.getImgPath()+"explosive.png",pos, 500);
    }

    /**
     * Deal damage to surrounding slicers
     */
    private void detonate(){
        // find all slicers that are in range, and decrease their health
        List<Slicer> targets = Tower.findTargets(getCenter(),RADIUS);
        for (Slicer s : targets){
            s.decreaseHealth(getDamage());
        }

    }

    /**
     * Render the explosive until it detonates
     * @param input The current mouse/keyboard state
     */
    @Override
    public void render(Input input){
        super.render(input);
        frameCount+=ShadowDefend.getTimescale();
        if (frameCount/ShadowDefend.getFPS()>=DETONATION_TIME){
            // explosive detonates after a delay. Detonate once delay is up
            detonate();
            // flag has detonated
            setHasFinished(true);
            frameCount = 0.0;
        }
    }


}
