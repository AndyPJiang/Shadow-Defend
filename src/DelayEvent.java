public class DelayEvent extends Event{

    public DelayEvent(String[] parts){
        super(parts[2],parts[0]);
    }
    public void runEvent(){
        if (!this.getIsFinished()){
            this.increaseFrameCount(ShadowDefend.getTimescale());
            if (this.getFrameCount()/ShadowDefend.getFPS()>=this.getDelay()){
                this.setIsFinished(true);
            }
        }
    }
}
