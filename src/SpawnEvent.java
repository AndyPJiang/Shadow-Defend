/**
 * A spawn event
 */

public class SpawnEvent extends Event{
    private final String slicerType;
    private final int totalSpawnNum;
    private boolean hasStarted = false;
    private int spawnCount = 0;

    /**
     * Create a new spawn event
     * @param parts specifies the time to delay, wave number,
     * type of slicer to spawn and the number of slicers to spawn
     */
    public SpawnEvent(String[] parts){
        super(parts[4],parts[0]);
        this.totalSpawnNum = Integer.parseInt(parts[2]);
        slicerType = parts[3];
    }


    /**
     * process the event, spawn slicers after time delay
     */
    public void runEvent(){
        // always spawn 1 slicer after each time delay interval
        int numberToSpawn = 1;
        if (!hasStarted){
            // spawn the first slicer
            ShadowDefend.addSlicer(slicerType,numberToSpawn);
            hasStarted = true;
            spawnCount++;
        }
        if (!getIsFinished()){
            increaseFrameCount(ShadowDefend.getTimescale());
            if (getFrameCount()/ShadowDefend.getFPS() >= getDelay()){
                // spawn a slicer with a constant delay
                ShadowDefend.addSlicer(slicerType,1);
                setFrameCount(0.0);
                spawnCount++;
            }
        }
        if (spawnCount>=totalSpawnNum){
            // all slicers have been spawn, set flag
            this.setIsFinished(true);
            spawnCount = 0;
        }

    }
}
