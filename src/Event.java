public abstract class Event {
    /**
     * A Wave Event
     */

    private double frameCount = 0.0;
    private boolean isFinished = false;
    private final double delay;
    private final int waveNum;

    /**
     * Create a new wave event
     * @param delay the delay time
     * @param waveNum the current wave number
     */
    public Event(String delay, String waveNum){
        // convert milliseconds to seconds
        this.delay = Double.parseDouble(delay)/1000;
        this.waveNum = Integer.parseInt(waveNum);
    }

    /**
     * process the wave event
     */
    public abstract void runEvent();

    /**
     * @return frame count
     */
    public double getFrameCount() {
        return frameCount;
    }

    /**
     * Set the frame count to a target frame count
     * @param target target frame count
     */
    public void setFrameCount(double target) {
        frameCount = target;
    }

    /**
     * Increase frame count
     * @param inc amount to increase by
     */
    public void increaseFrameCount(double inc) {
        frameCount += inc;
    }

    /**
     * @return wave number of wave event
     */
    public int getWaveNum(){
        return waveNum;
    }

    /**
     * @return the time delay bewteen spawn events
     */
    public double getDelay() {
        return delay;
    }

    /**
     * @return whether wave event has finished
     */
    public boolean getIsFinished() {
        return isFinished;
    }

    /**
     * Update whether wave event has finished
     * @param bool state of wave event
     */
    public void setIsFinished(boolean bool) {
        isFinished = bool;
    }
}
