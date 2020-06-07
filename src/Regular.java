import bagel.*;
import bagel.util.Point;
import org.lwjgl.system.CallbackI;

import java.util.List;

public class Regular extends Slicer{
    private final int spawnNum = 0;

    public Regular(List<Point> polyline){
        super(polyline,2,1,2,1,"res/images/slicer.png");
    }
    public Regular(List<Point> polyline,Point start, int targetInd){
        super(polyline,2,1,2,1,"res/images/slicer.png",start);
        this.setTargetInd(targetInd);
    }

    public void spawnOnDeath(){}
}
