import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * An apex slicer
 */

public class Apex extends Slicer{

    /**
     * Create new apex slicer. By default a slicer follows path of polyline from the beginning
     * @param polyline The polyline that the slicer must traverse
     */
    public Apex(List<Point> polyline){
        super(polyline,0.75,25,150,16,
                ShadowDefend.getImgPath()+"apexslicer.png");
    }

    /**
     * Create new apex slicer. We can specify a specific starting point
     * for the slicer to start at. This is used to spawn new slicers
     * at the position that its parent slicer was eliminated
     * @param polyline The polyline that the slicer must traverse
     * @param start position for slicer to start at
     * @param targetInd the point in polyline that we are targeting
     */
    public Apex(List<Point> polyline,Point start, int targetInd){
        super(polyline,0.75,25,150,16,
                ShadowDefend.getImgPath()+"apexslicer.png",start);
        this.setTargetInd(targetInd);
    }


    /**
     * Spawn new child slicers when eliminated
     */
    public void spawnOnDeath(){
        int spawnNum = 4;
        List<Point> polyline = getPolyline();
        int targetInd = getTargetInd();
        Point start = getCenter();
        List<Slicer> newSlicers = new ArrayList<>();
        for(int i=0;i<spawnNum;i++){
            newSlicers.add(new Mega(polyline,start,targetInd));
        }
        ShadowDefend.addSlicer(newSlicers);
    }

}
