public class DelayEvent extends Event{

    public DelayEvent(String[] parts){
        super(parts[2],parts[0]);
    }
    public void runEvent(){
        if (!this.getIsFinished()){
            increaseFrameCount(ShadowDefend.getTimescale());
            if (getFrameCount()/ShadowDefend.getFPS() >= getDelay()){
                setIsFinished(true);
            }
        }
    }
}
