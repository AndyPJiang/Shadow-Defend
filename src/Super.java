import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Super extends Slicer {

    public Super(List<Point> polyline){
        super(polyline,1.5,1,15,2,
                ShadowDefend.getImgPath()+"superslicer.png");
    }
    public Super(List<Point> polyline,Point start, int targetInd){
        super(polyline,1.5,1,15,2,
                ShadowDefend.getImgPath()+"superslicer.png",start);
        this.setTargetInd(targetInd);
    }

    public void spawnOnDeath(){
        int spawnNum = 2;
        List<Point> polyline = getPolyline();
        int targetInd = getTargetInd();
        Point start = getCenter();
        List<Slicer> newSlicers = new ArrayList<>();
        for(int i=0;i<spawnNum;i++){
            newSlicers.add(new Regular(polyline,start,targetInd));
        }
        ShadowDefend.addSlicer(newSlicers);
    }
}
