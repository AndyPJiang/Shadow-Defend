import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

/**
 * ShadowDefend, a tower defence game.
 */
public class ShadowDefend extends AbstractGame {
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private static final String IMG_PATH = "res/images/";
    private static final String MAP_PATH = "res/levels/";
    private static final String FONT_PATH = "res/fonts/";
    private static final double INIT_TIMESCALE = 1;
    private static final double MAX_TIMESCALE = 20;
    private static final int INIT_MONEY = 500;
    private static final int INIT_WAVENUM = 1;
    private static final int INIT_LIVES = 25;
    private static final String INIT_STATUS = "Awaiting Start";
    private static final double FPS = 60;

    private boolean hasStarted = false;
    private boolean isBuying = false;
    private static boolean airplaneIsVertical = false;

    private static int level = 1;
    private static double timescale = INIT_TIMESCALE;
    private static int money = INIT_MONEY;
    private static int waveNum = INIT_WAVENUM;
    private static final List<String> status = new ArrayList<>();
    private static int lives = INIT_LIVES;

    private static final List<Slicer> slicers = new ArrayList<>();
    private static final List<Tower> towers = new ArrayList<>();

    private static TiledMap map;
    private static List<Point> polyline;

    private static final int tankPrice = 250;
    private static final int superTankPrice = 600;
    private static final int airplanePrice = 500;

    private final Wave wave;
    private final StatusPanel statusPanel;
    private final BuyPanel buyPanel;
    private BuyTower buyTower;
    /**
     * Creates a new instance of the ShadowDefend game
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        map = new TiledMap(MAP_PATH+level+".tmx");
        polyline = map.getAllPolylines().get(0);
        this.wave = Wave.getInstance();
        this.statusPanel = StatusPanel.getInstance();
        this.buyPanel = BuyPanel.getInstance();
        status.add(INIT_STATUS);
    }

    /**
     * The entry-point for the game
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) throws Exception {
        new ShadowDefend().run();
    }


    /**
     * add new slicers to the game
     * @param slicerType the type of slicer to spawn
     * @param num the number of slicers to spawn
     */
    public static void addSlicer(String slicerType, int num){
        if (slicerType.equals("slicer")){
            for (int i=0;i<num;i++){
                slicers.add(new Regular(polyline));
            }
        }else if (slicerType.equals("superslicer")){
            for (int i=0;i<num;i++){
                slicers.add(new Super(polyline));
            }
        }else if (slicerType.equals("megaslicer")){
            for (int i=0;i<num;i++){
                slicers.add(new Mega(polyline));
            }
        }else{
            for (int i=0;i<num;i++){
                slicers.add(new Apex(polyline));
            }
        }
    }

    /**
     * add new slicers to the game
     * @param newSlicers a list a slicers to add
     */
    public static void addSlicer(List<Slicer> newSlicers){
        slicers.addAll(newSlicers);
    }

    /**
     * add a new tower to the game
     * @param towerType the type of tower to add
     * @param p the position to place the tower at
     */
    public static void addTower(String towerType, Point p){
        if (towerType.equals("tank")){
            towers.add(new Tank(p,"tank",100,1,1));
            addMoney(-tankPrice);
        }else if (towerType.equals("supertank")){
            towers.add(new Tank(p,"supertank",150,0.5,3));
            addMoney(-superTankPrice);
        }else{
            if (airplaneIsVertical){
                towers.add(new Airplane(new Point(p.x,0),true));
            }else{
                towers.add(new Airplane(new Point(0,p.y),false));
            }
            airplaneIsVertical = !airplaneIsVertical;
            addMoney(-airplanePrice);
        }
    }


    /**
     * reset the game state after wave has finished
     */
    private void resetGame(){
        level++;
        try{
            map = new TiledMap(MAP_PATH+level+".tmx");
            polyline = map.getAllPolylines().get(0);
            towers.clear();
            slicers.clear();
            wave.resetWave();
            hasStarted = false;
            money = INIT_MONEY;
            lives = INIT_LIVES;
            timescale = INIT_TIMESCALE;
            waveNum = INIT_WAVENUM;
            airplaneIsVertical = false;
        }
        catch (Exception e){
            status.add("Winner!");
        }
    }
    /**
     * Return current wave number
     */
    public static int getWaveNum() {
        return waveNum;
    }
    /**
     * Return the price of a tank
     */
    public static int getTankPrice() {
        return tankPrice;
    }
    /**
     * Return the price of a supertank
     */
    public static int getSuperTankPrice() {
        return superTankPrice;
    }
    /**
     * Return the price of airsupport
     */
    public static int getAirplanePrice() {
        return airplanePrice;
    }

    /**
     * Return the number of lives
     */
    public static int getLives() {
        return lives;
    }

    /**
     * Return the current game status
     */
    public static String getStatus() {
        return status.get(status.size()-1);
    }

    /**
     * Return the frames per second
     */
    public static double getFPS() {
        return FPS;
    }

    /**
     * Update the current wave number
     * @param num new wave number
     */
    public static void setWaveNum(int num){
        waveNum = num;
    }

    /**
     * Update money
     * @param mon amount of money to add
     */
    public static void addMoney(int mon) {
        money += mon;
    }

    /**
     * Return current amount of money left
     */
    public static int getMoney() {
        return money;
    }

    /**
     * Return the array of towers currently in game
     */
    public static List<Tower> getTowers() {
        return towers;
    }

    /**
     * Return the array of slicers currently in game
     */
    public static List<Slicer> getSlicers() {
        return slicers;
    }

    /**
     * Return the timescale
     */
    public static double getTimescale() {
        return timescale;
    }

    /**
     * Increases the timescale
     */
    private void increaseTimescale() {
        if (timescale < MAX_TIMESCALE) {
            timescale++;
        }
    }
    /**
     * Decrease the timescale
     */
    private void decreaseTimescale() {
        if (timescale > INIT_TIMESCALE) {
            timescale--;
        }
    }

    /**
     * Return the path to the directory of images
     */
    public static String getImgPath() {
        return IMG_PATH;
    }

    /**
     * Return the path to the directory of maps
     */
    public static String getMapPath() {
        return MAP_PATH;
    }

    /**
     * Return the path to the directory of fonts
     */
    public static String getFontPath() {
        return FONT_PATH;
    }

    /**
     * Return window height
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * Return window width
     */
    public static int getWidth() {
        return WIDTH;
    }


    /**
     * Update the state of the game, potentially reading from input
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        map.draw(0,0,0,0,WIDTH,HEIGHT);

        if (input.wasPressed(Keys.S) && !hasStarted && !isBuying) {
            status.add("Wave In Progress");
            hasStarted = true;
            wave.setIsFinished(false);
        }

        if (input.wasPressed(Keys.L)) {
            increaseTimescale();
        }
        if (input.wasPressed(Keys.K)) {
            decreaseTimescale();
        }
        if (input.wasPressed(MouseButtons.LEFT) && !isBuying){
            String towerSelected = buyPanel.getTowerType(input);
            if (!towerSelected.isEmpty()){
                buyTower = BuyTower.getInstance(towerSelected,map);
                isBuying = true;
                status.add("Placing");
            }
        }else if (input.wasPressed(MouseButtons.LEFT) && isBuying){
            if (buyTower.buy(input)){
                status.remove(status.size()-1);
                isBuying = false;
            }
        }else if (input.wasPressed(MouseButtons.RIGHT) && isBuying){
            status.remove(status.size()-1);
            isBuying = false;
        }

        if (isBuying){
            buyTower.followMouse(input);
        }

        wave.progressWave();

        for (int i=towers.size()-1;i>=0;i--){
            Tower t = towers.get(i);
            t.render(input);
            t.attack(input);
            if (t.getHasFinished()){
                towers.remove(i);
            }
        }

        for (int i=slicers.size()-1;i>=0;i--){
            Slicer slicer = slicers.get(i);
            slicer.update(input);
            if (slicer.getIsFinished()){
                lives -= slicer.getPenalty();
                slicers.remove(i);
            }else if (slicer.getHealth()<=0){
                slicer.spawnOnDeath();
                slicers.remove(i);
                addMoney(slicer.getReward());
            }
            if (wave.getIsFinished() && slicers.isEmpty()){
                ShadowDefend.addMoney(150+waveNum*100);
                hasStarted = false;
                if (isBuying){
                    status.remove(status.size()-2);
                }else{
                    status.remove(status.size()-1);
                }
                if (wave.getIsWaveFinished()){
                    resetGame();
                }
            }
        }
        buyPanel.renderPanel();
        statusPanel.renderPanel();
    }
}
