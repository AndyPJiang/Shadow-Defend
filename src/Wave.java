import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class Wave {
    private static Wave wave = null;
    private int curEvent = 0;
    private boolean isFinished = true;
    private boolean waveFinished = false;
    private static final List<Event> events = new ArrayList<>();

    private Wave() {
        String mapPATH = ShadowDefend.getMapPath();
        try (BufferedReader br = new BufferedReader(new FileReader(mapPATH+"waves.txt"))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] parts = text.split(",");
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
    public static Wave getInstance(){
        if (wave == null){
            wave = new Wave();
        }
        return wave;
    }

    public void progressWave(){
        if (!isFinished && curEvent<events.size()){
            ShadowDefend.setWaveNum(events.get(curEvent).getWaveNum());
            events.get(curEvent).runEvent();

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
    public void resetWave(){
        this.isFinished = true;
        waveFinished = false;
        curEvent = 0;
        for (Event event : events) {
            event.setIsFinished(false);
        }
    }

    public boolean getIsWaveFinished() {
        return waveFinished;
    }
    public boolean getIsFinished() {
        return isFinished;
    }
    public void setIsFinished(boolean bool) {
        this.isFinished = bool;
    }
}
