/**
 * A spawn event
 */

public class SpawnEvent extends Event{
    private final String enemyType;
    private final int totalSpawnNum;
    private boolean hasStarted = false;
    private int spawnNum = 0;

    /**
     * Create a new spawn event
     * @param parts specifies the time to delay, wave number,
     * type of slicer to spawn and the number of slicers to spawn
     */
    public SpawnEvent(String[] parts){
        super(parts[4],parts[0]);
        this.totalSpawnNum = Integer.parseInt(parts[2]);
        enemyType = parts[3];
    }

    /**
     * process the event, spawn slicers after time delay
     */
    public void runEvent(){
        if (!hasStarted){
            // spawn the first slicer
            ShadowDefend.addSlicer(enemyType,1);
            hasStarted = true;
            spawnNum++;
        }
        if (!getIsFinished()){
            increaseFrameCount(ShadowDefend.getTimescale());
            if (getFrameCount()/ShadowDefend.getFPS() >= getDelay()){
                // spawn a slicer with a constant delay
                ShadowDefend.addSlicer(enemyType,1);
                setFrameCount(0.0);
                spawnNum++;
                if (spawnNum>=totalSpawnNum){
                    // all slicers have been spawn, set flag
                    this.setIsFinished(true);
                    spawnNum = 0;
                }
            }
        }

    }
}
