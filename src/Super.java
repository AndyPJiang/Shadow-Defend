import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A super slicer
 */

public class Super extends Slicer {
    private static final String NAME = "superslicer";
    private static final double SPEED = 1.5;
    private static final int HEALTH = 1;
    private static final int REWARD = 15;
    private static final int PENALTY = 2;
    /**
     * Create new super slicer. By default a slicer follows path of polyline from the beginning
     * @param polyline The polyline that the slicer must traverse
     */
    public Super(List<Point> polyline){
        super(polyline,NAME,SPEED, HEALTH, REWARD, PENALTY,
                ShadowDefend.getImgPath()+"superslicer.png");
    }

    /**
     * Create new regular slicer. We can specify a specific starting point
     * for the slicer to start at. This is used to spawn new slicers
     * at the position that its parent slicer was eliminated
     * @param polyline The polyline that the slicer must traverse
     * @param start position for slicer to start at
     * @param targetInd the point in polyline that we are targeting
     */
    public Super(List<Point> polyline,Point start, int targetInd){
        super(polyline,NAME,SPEED, HEALTH, REWARD, PENALTY,
                ShadowDefend.getImgPath()+"superslicer.png",start,targetInd);
    }


    /**
     * Spawn new child slicers when eliminated. Spawn it at the position that it was eliminated.
     */
    public void spawnOnDeath(){
        int spawnNum = 2;
        List<Point> polyline = getPolyline();
        int targetInd = getTargetInd();
        Point start = getCenter();
        List<Slicer> newSlicers = new ArrayList<>();
        for(int i=0;i<spawnNum;i++){
            // spawn child slicer at the current location of parent slicer
            newSlicers.add(new Regular(polyline,start,targetInd));
        }
        ShadowDefend.addSlicer(newSlicers);
    }

    /**
     * @param polyline The polyline that the slicer must traverse
     * @return new a new super slicer
     */
    public Slicer newSlicer(List<Point> polyline){
        return new Super(polyline);
    }

}
