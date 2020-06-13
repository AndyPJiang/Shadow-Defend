import bagel.util.Point;
import java.util.List;

/**
 * A regular slicer
 */
public class Regular extends Slicer{
    private static final String NAME = "slicer";
    private static final double SPEED = 2;
    private static final int HEALTH = 1;
    private static final int REWARD = 2;
    private static final int PENALTY = 1;

    /**
     * Create new regular slicer. By default a slicer follows path of polyline from the beginning
     * @param polyline The polyline that the slicer must traverse
     */
    public Regular(List<Point> polyline){
        super(polyline, NAME, SPEED, HEALTH, REWARD, PENALTY,
                ShadowDefend.getImgPath()+"slicer.png");
    }

    /**
     * Create new regular slicer. We can specify a specific starting point
     * for the slicer to start at. This is used to spawn new slicers
     * at the position that its parent slicer was eliminated
     * @param polyline The polyline that the slicer must traverse
     * @param start position for slicer to start at
     * @param targetInd the point in polyline that we are targeting
     */
    public Regular(List<Point> polyline,Point start, int targetInd){
        super(polyline, NAME, SPEED, HEALTH, REWARD, PENALTY,
                ShadowDefend.getImgPath()+"slicer.png",start,targetInd);
    }


    /**
     * Spawn new child slicers when eliminated. A regular slicer does not spawn
     * any child slicers on elimination thus the function is empty.
     */
    public void spawnOnDeath(){}

    /**
     * @param polyline The polyline that the slicer must traverse
     * @return new a new regular slicer
     */
    public Slicer newSlicer(List<Point> polyline){
        return new Regular(polyline);
    }
}
