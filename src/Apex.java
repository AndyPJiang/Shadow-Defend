import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * An apex slicer
 */

public class Apex extends Slicer{
    private static final String NAME = "apexslicer";
    private static final double SPEED = 0.75;
    private static final int HEALTH = 25;
    private static final int REWARD = 150;
    private static final int PENALTY = 16;

    /**
     * Create new apex slicer. By default a slicer follows path of polyline from the beginning
     * @param polyline The polyline that the slicer must traverse
     */
    public Apex(List<Point> polyline){
        super(polyline,NAME,SPEED, HEALTH, REWARD, PENALTY,
                ShadowDefend.getImgPath()+"apexslicer.png");
    }

    /**
     * Create new apex slicer. We can specify a specific starting point
     * for the slicer to start at. This is used to spawn new slicers
     * at the position that its parent slicer was eliminated. This
     * constructor is not used in this game but is here for consistency
     * across all slicers.
     * @param polyline The polyline that the slicer must traverse
     * @param start position for slicer to start at
     * @param targetInd the point in polyline that we are targeting
     */
    public Apex(List<Point> polyline, Point start, int targetInd){
        super(polyline, NAME, SPEED, HEALTH, REWARD, PENALTY,
                ShadowDefend.getImgPath()+"apexslicer.png",start, targetInd);
    }

    /**
     * Spawn new child slicers when eliminated. Spawn it at the position that it was eliminated.
     */
    public void spawnOnDeath(){
        int spawnNum = 4;
        List<Point> polyline = getPolyline();
        int targetInd = getTargetInd();
        Point start = getCenter();

        List<Slicer> newSlicers = new ArrayList<>();
        for(int i=0;i<spawnNum;i++){
            // spawn child slicer at the current location of parent slicer
            newSlicers.add(new Mega(polyline,start,targetInd));
        }
        ShadowDefend.addSlicer(newSlicers);
    }

    /**
     * @param polyline The polyline that the slicer must traverse
     * @return new a new apex slicer
     */
    public Slicer newSlicer(List<Point> polyline){
        return new Apex(polyline);
    }

}
