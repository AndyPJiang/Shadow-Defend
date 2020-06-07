import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Apex extends Slicer{

    public Apex(List<Point> polyline){
        super(polyline,0.75,25,150,16,"res/images/apexslicer.png");
    }
    public Apex(List<Point> polyline,Point start, int targetInd){
        super(polyline,0.75,25,150,16,"res/images/apexslicer.png",start);
        this.setTargetInd(targetInd);
    }

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
