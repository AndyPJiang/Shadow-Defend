/**
 * A delay wave event
 */

public class DelayEvent extends Event{
    /**
     * Creates a new delay event
     * @param parts specifies the time to delay and the wave number
     */
    public DelayEvent(String[] parts){
        super(parts[2],parts[0]);
    }

    /**
     * Wait the delay time
     */
    public void runEvent(){
        // increment frame count until it reaches the delay time
        if (!this.getIsFinished()){
            increaseFrameCount(ShadowDefend.getTimescale());
            if (getFrameCount()/ShadowDefend.getFPS() >= getDelay()){
                setIsFinished(true);
            }
        }
    }
}
