public abstract class Event {
    private double frameCount = 0.0;
    private boolean isFinished = false;
    private final double delay;
    private final int waveNum;

    public Event(String delay, String waveNum){
        this.delay = Double.parseDouble(delay)/1000;
        this.waveNum = Integer.parseInt(waveNum);
    }
    public abstract void runEvent();

    public boolean getIsFinished() {
        return isFinished;
    }
    public void setIsFinished(boolean bool) {
        isFinished = bool;
    }
    public double getFrameCount() {
        return frameCount;
    }
    public void setFrameCount(double target) {
        frameCount = target;
    }
    public void increaseFrameCount(double inc) {
        frameCount += inc;
    }
    public int getWaveNum(){
        return waveNum;
    }
    public double getDelay() {
        return delay;
    }
}
