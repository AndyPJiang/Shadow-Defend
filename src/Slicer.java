import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;

/**
 * Adapted and extended code from Project 1 Solution by Rohyl.
 * A slicer
 */


public abstract class Slicer extends Sprite{
    private final DrawOptions options = new DrawOptions();   // used to set rotation when drawing slicer
    private final List<Point> polyline;
    private int targetInd = 1;  // current point in polylines
    private boolean isFinished = false;
    private final double speed;
    private int health;
    private final int reward;
    private final int penalty;


    /**
     * Create a new slicer. By default a slicer follows path of polyline from the beginning
     * @param polyline polyline The polyline that the slicer must traverse
     * @param speed speed at which the slicer moves
     * @param health health of the slicer
     * @param reward reward if slicer is eliminated
     * @param penalty penalty if slicer reaches end
     * @param imageFile the path to the slicer image as a string
     */
    public Slicer(List<Point> polyline,double speed,
                  int health, int reward, int penalty, String imageFile){
        super(polyline.get(0), imageFile);
        this.polyline = polyline;
        this.speed = speed;
        this.health = health;
        this.reward = reward;
        this.penalty = penalty;
    }

    /**
     * Creates a new slicer. We can specify a specific starting point
     * for the slicer to start at.This is used to spawn new slicers
     * at the position that its parent slicer was eliminated
     * @param polyline polyline The polyline that the slicer must traverse
     * @param speed speed at which the slicer moves
     * @param health health of the slicer
     * @param reward reward if slicer is eliminated
     * @param penalty penalty if slicer reaches end
     * @param imageFile the path to the slicer image as a string
     * @param start starting position of the slicer
     */
    public Slicer(List<Point> polyline, double speed, int health, int reward,
                  int penalty, String imageFile, Point start, int targetInd){
        super(start,imageFile);
        this.polyline = polyline;
        this.speed = speed;
        this.health = health;
        this.reward = reward;
        this.penalty = penalty;
        this.targetInd = targetInd;
    }

    /**
     * @return the health of the slicer
     */
    public int getHealth() {
        return health;
    }

    /**
     * Decrease health
     * @param health amount to decrease by
     */
    public void decreaseHealth(int health) {
        this.health -= health;
    }

    /**
     * @return the penalty of the slicer
     */
    public int getPenalty(){
        return penalty;
    }

    /**
     * @return whether slicer has reached the end
     */
    public boolean getIsFinished() {
        return isFinished;
    }

    /**
     * @return the reward of the slicer
     */
    public int getReward() {
        return reward;
    }

    /**
     * @return the target point of the slicer in polylines
     */
    public int getTargetInd() {
        return targetInd;
    }

    /**
     * Set the target point of the slicer in polylines
     * @param index of target point in polylines
     */
    public void setTargetInd(int targetInd) {
        this.targetInd = targetInd;
    }

    /**
     * @return the polylines
     */
    public List<Point> getPolyline() {
        return polyline;
    }

    /**
     * spawns child slicers on elimination. This is an abstract method
     */
    public abstract void spawnOnDeath();

    /**
     * Updates the current state of the slicer. The slicer moves towards its next target point in
     * the polyline at its specified movement rate.
     * @param input The current mouse/keyboard state
     */
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
