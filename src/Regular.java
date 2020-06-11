import bagel.util.Point;
import java.util.List;

public class Regular extends Slicer{
    private final int spawnNum = 0;

    public Regular(List<Point> polyline){
        super(polyline,2,1,2,1,ShadowDefend.getImgPath()+"slicer.png");
    }
    public Regular(List<Point> polyline,Point start, int targetInd){
        super(polyline,2,1,2,1,ShadowDefend.getImgPath()+"slicer.png",start);
        this.setTargetInd(targetInd);
    }

    public void spawnOnDeath(){}
}
