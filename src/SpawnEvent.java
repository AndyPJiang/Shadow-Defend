import bagel.util.Point;
import java.util.List;

public class SpawnEvent extends Event{
    private final String enemyType;
    private final int totalSpawnNum;
    private boolean hasStarted = false;
    private int spawnNum = 0;

    public SpawnEvent(String[] parts){
        super(parts[4],parts[0]);
        this.totalSpawnNum = Integer.parseInt(parts[2]);
        enemyType = parts[3];
    }

    public void runEvent(){
        if (!hasStarted){
            ShadowDefend.addSlicer(enemyType,1);
            hasStarted = true;
            spawnNum++;
        }
        if (!getIsFinished()){
            increaseFrameCount(ShadowDefend.getTimescale());
            if (getFrameCount()/ShadowDefend.getFPS() >= getDelay()){
                ShadowDefend.addSlicer(enemyType,1);
                setFrameCount(0.0);
                spawnNum++;
                if (spawnNum>=totalSpawnNum){
                    this.setIsFinished(true);
                    spawnNum = 0;
                }
            }
        }

    }
}
