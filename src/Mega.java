import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Mega extends Slicer{

    public Mega(List<Point> polyline){
        super(polyline,1.5,2,10,4,"res/images/megaslicer.png");
    }
    public Mega(List<Point> polyline,Point start, int targetInd){
        super(polyline,1.5,2,10,4,"res/images/megaslicer.png",start);
        this.setTargetInd(targetInd);
    }
    public void spawnOnDeath(){
        int spawnNum = 2;

        List<Point> polyline = getPolyline();
        int targetInd = getTargetInd();
        Point start = getCenter();
        List<Slicer> newSlicers = new ArrayList<>();
        for(int i=0;i<spawnNum;i++){
            newSlicers.add(new Super(polyline,start,targetInd));
        }
        ShadowDefend.addSlicer(newSlicers);
    }
}
