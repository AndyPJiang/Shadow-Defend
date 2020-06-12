import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 * A wave of slicers
 */

public class Wave {
    private static Wave wave = null;
    private int curEvent = 0;
    private boolean isFinished = true;
    private boolean waveFinished = false;
    private static final List<Event> events = new ArrayList<>();

    /**
     * Create a new wave by reading input from a text file. Visibility is private as there will only be
     * one instance of this class
     */
    private Wave() {
        String mapPATH = ShadowDefend.getMapPath();
        // read in wave events and store them in a list.
        // This block of code is taken from the lecture 8 slide 30
        try (BufferedReader br = new BufferedReader(new FileReader(mapPATH+"waves.txt"))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] parts = text.split(",");
                // check if its a spawn or delay event
                if (parts[1].equals("spawn") ){
                    events.add(new SpawnEvent(parts));
                }else{
                    events.add(new DelayEvent(parts));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return an instance of the Wave class. If an instance was created already, return it,
     * otherwise create a new one.
     */
    public static Wave getInstance(){
        if (wave == null){
            wave = new Wave();
        }
        return wave;
    }

    /**
     * Continue the current wave event. If finished, move on to the next wave event
     */
    public void progressWave(){
        // if current wave event is still in progress, run the event
        if (!isFinished && curEvent<events.size()){
            ShadowDefend.setWaveNum(events.get(curEvent).getWaveNum());
            events.get(curEvent).runEvent();

            // set flags if wave event has finished
            if (events.get(curEvent).getIsFinished()){
                curEvent++;
                if (curEvent>=events.size()){
                    waveFinished = true;
                    isFinished = true;
                }
                else if(events.get(curEvent).getWaveNum() != ShadowDefend.getWaveNum()){
                    this.isFinished = true;
                }
            }
        }
    }

    /**
     * reset and start spawning slicers from the first wave event again
     */
    public void resetWave(){
        this.isFinished = true;
        waveFinished = false;
        curEvent = 0;
        for (Event event : events) {
            event.setIsFinished(false);
        }
    }

    /**
     * @return whether the entire wave has finished
     */
    public boolean getIsWaveFinished() {
        return waveFinished;
    }

    /**
     * @return whether current wave event has finished
     */
    public boolean getIsFinished() {
        return isFinished;
    }

    /**
     * Set the current state of the wave event
     * @param bool whether the wave event has finished
     */
    public void setIsFinished(boolean bool) {
        this.isFinished = bool;
    }
}
