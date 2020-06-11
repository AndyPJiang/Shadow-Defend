import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;

public abstract class Slicer extends Sprite{
    private final DrawOptions options = new DrawOptions();   // used to set rotation when drawing slicer
    private final List<Point> polyline;
    private int targetInd = 1;  // current point in polylines
    private boolean isFinished = false;
    private final double speed;
    private int health;
    private final int reward;
    private final int penalty;


    public Slicer(List<Point> polyline,double speed,
                  int health, int reward, int penalty, String imageFile){
        super(polyline.get(0), imageFile);
        this.polyline = polyline;
        this.speed = speed;
        this.health = health;
        this.reward = reward;
        this.penalty = penalty;
    }
    public Slicer(List<Point> polyline,double speed,
                  int health, int reward, int penalty,String imageFile, Point start){
        super(start,imageFile);
        this.polyline = polyline;
        this.speed = speed;
        this.health = health;
        this.reward = reward;
        this.penalty = penalty;
    }

    public int getHealth() {
        return health;
    }
    public void decreaseHealth(int health) {
        this.health -= health;
    }
    public int getPenalty(){
        return penalty;
    }
    public boolean getIsFinished() {
        return isFinished;
    }
    public abstract void spawnOnDeath();
    public int getReward() {
        return reward;
    }
    public int getTargetInd() {
        return targetInd;
    }
    public void setTargetInd(int targetInd) {
        this.targetInd = targetInd;
    }

    public List<Point> getPolyline() {
        return polyline;
    }

    @Override
    public void update(Input input) {
        if (isFinished) {
            return;
        }
        // Obtain where we currently are, and where we want to be
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetInd);
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Check if we are close to the target point
        if (magnitude < speed * ShadowDefend.getTimescale()) {
            // Check if we have reached the end
            if (targetInd == polyline.size() - 1) {
                isFinished = true;
                return;
            } else {
                // Make our focus the next point in the polyline
                targetInd += 1;
            }
        }
        // Move towards the target point
        // We do this by getting a unit vector in the direction of our target, and multiplying it
        // by the speed of the slicer (accounting for the timescale)
        super.move(distance.normalised().mul(speed * ShadowDefend.getTimescale()));
        // Update current rotation angle to face target point
        setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update(input);
    }



}
