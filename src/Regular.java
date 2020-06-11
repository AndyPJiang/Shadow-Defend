import bagel.util.Point;
import java.util.List;


/**
 * A regular slicer
 */
public class Regular extends Slicer{

    /**
     * Create new regular slicer. By default a slicer follows path of polyline from the beginning
     * @param polyline The polyline that the slicer must traverse
     */
    public Regular(List<Point> polyline){
        super(polyline,2,1,2,1,ShadowDefend.getImgPath()+"slicer.png");
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
        super(polyline,2,1,2,1,ShadowDefend.getImgPath()+"slicer.png",start);
        this.setTargetInd(targetInd);
    }


    /**
     * Spawn new child slicers when eliminated
     */
    public void spawnOnDeath(){}
}
