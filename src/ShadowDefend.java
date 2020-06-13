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
    // settings and paths
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private static final String IMG_PATH = "res/images/";
    private static final String MAP_PATH = "res/levels/";
    private static final String FONT_PATH = "res/fonts/";
    private static final double FPS = 60;

    // game presets
    private static final double INIT_TIMESCALE = 1;
    private static final double MAX_TIMESCALE = 5;
    private static final int INIT_MONEY = 500;
    private static final int INIT_WAVENUM = 1;
    private static final int INIT_LIVES = 25;
    private static final int INIT_LEVEL = 1;
    private static final String INIT_STATUS = "Awaiting Start";
    private static final boolean INIT_PLANE_ORIENTATION = false;

    // initialise variables to game presets
    private static int level = INIT_LEVEL;
    private static double timescale = INIT_TIMESCALE;
    private static int money = INIT_MONEY;
    private static int waveNum = INIT_WAVENUM;
    private static int lives = INIT_LIVES;
    private static boolean airplaneIsVertical = INIT_PLANE_ORIENTATION;
    // stores the statuses of the game as a stack. The top of the stack is the current status
    private static final List<String> status = new ArrayList<>();
    // stores all slicers that are currently in game
    private static final List<Slicer> slicers = new ArrayList<>();
    // stores all towers that are currently in game
    private static final List<Tower> towers = new ArrayList<>();
    // a list of all tower types in the game
    private static final List<Tower> allTowers = new ArrayList<>();
    private static final List<Slicer> allSlicers = new ArrayList<>();

    private static TiledMap map;
    private static List<Point> polyline;


    // reward at end of each wave event
    private final int ENDWAVE_REWARD_SCALE = 100;
    private final int ENDWAVE_REWARD = 150;

    // Single instance classes
    private final Wave wave;
    private final StatusPanel statusPanel;
    private final BuyPanel buyPanel;
    private BuyTower buyTower;

    // dummies to keep track of game state
    private boolean hasStarted = false;
    private boolean isBuying = false;
    private boolean gameHasEnded = false;

    /**
     * Creates a new instance of the ShadowDefend game
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        map = new TiledMap(MAP_PATH+level+".tmx");
        polyline = map.getAllPolylines().get(0);
        // create an instance of the wave class
        this.wave = Wave.getInstance();
        // create an instance of the buy panel and status panel class
        this.statusPanel = StatusPanel.getInstance();
        this.buyPanel = BuyPanel.getInstance();
        // push to the top of the stack the initial game status
        status.add(INIT_STATUS);

        // add an instance of all the tower and slicer types
        allTowers.add(new Tank(new Point(0,0)));
        allTowers.add(new SuperTank(new Point(0,0)));
        allTowers.add(new Airplane(new Point(0,0)));
        allSlicers.add(new Regular(polyline));
        allSlicers.add(new Super(polyline));
        allSlicers.add(new Mega(polyline));
        allSlicers.add(new Apex(polyline));
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
        for (Slicer s : allSlicers){
            if (slicerType.equals(s.getName())){
                for (int i=0;i<num;i++){
                    slicers.add(s.newSlicer(polyline));
                }
            }
        }
    }

    /**
     * add new slicers to the game. This method is used when spawning child slicers on elimination
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
        for (Tower t : allTowers){
            if (towerType.equals(t.getName())){
                towers.add(t.newTower(p));
                subtractMoney(t.getTowerPrice());
            }
        }
    }

    /**
     * reset the game state after wave has finished
     */
    private void resetGame(){
        level++;
        try{
            // try to load a new map. If possible, reset game state
            map = new TiledMap(MAP_PATH+level+".tmx");
            polyline = map.getAllPolylines().get(0);
            towers.clear();
            slicers.clear();
            wave.resetWave();
            money = INIT_MONEY;
            lives = INIT_LIVES;
            timescale = INIT_TIMESCALE;
            waveNum = INIT_WAVENUM;
            airplaneIsVertical = INIT_PLANE_ORIENTATION;
            isBuying = false;
            status.clear();
            status.add(INIT_STATUS);
        }
        catch (Exception e){
            // if no more maps, game is finished
            status.add("Winner!");
            gameHasEnded = true;
        }
        isBuying = false;
        hasStarted = false;
    }

    /**
     * @return current wave number
     */
    public static int getWaveNum() {
        return waveNum;
    }

    /**
     * @return the number of lives
     */
    public static int getLives() {
        return lives;
    }

    /**
     * @return the current game status
     */
    public static String getStatus() {
        return status.get(status.size()-1);
    }

    /**
     * @return the frames per second
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
     * Add money
     * @param mon amount of money to add
     */
    public static void addMoney(int mon) {
        money += mon;
    }

    /**
     * Subtract money
     * @param mon amount of money to subtract
     */
    public static void subtractMoney(int mon) {
        money -= mon;
    }

    /**
     * @return current amount of money left
     */
    public static int getMoney() {
        return money;
    }

    /**
     * @return the array of towers currently in game
     */
    public static List<Tower> getTowers() {
        return towers;
    }

    /**
     * @return the array of slicers currently in game
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
     * @return the path to the directory of images
     */
    public static String getImgPath() {
        return IMG_PATH;
    }

    /**
     * @return the path to the directory of maps
     */
    public static String getMapPath() {
        return MAP_PATH;
    }

    /**
     * @return the path to the directory of fonts
     */
    public static String getFontPath() {
        return FONT_PATH;
    }

    /**
     * @return window height
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * @return window width
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * @return is airplane orientation is vertical
     */
    public static boolean getAirplaneIsVertical() {
        return airplaneIsVertical;
    }

    /**
     * Set new airplane orientation
     * @param airplaneIsVertical new airplane orientation
     */
    public static void setAirplaneIsVertical(boolean airplaneIsVertical) {
        ShadowDefend.airplaneIsVertical = airplaneIsVertical;
    }
    /**
     * Update the state of the game, potentially reading from input
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        // draw the map
        map.draw(0,0,0,0,WIDTH,HEIGHT);

        // only accept key press inputs if game has not finished
        if (!gameHasEnded){
            // handle timescale key presses
            if (input.wasPressed(Keys.L)) {
                increaseTimescale();
            }
            if (input.wasPressed(Keys.K)) {
                decreaseTimescale();
            }

            // Handle starting game and buying key presses only if game is still in progress
            if (input.wasPressed(Keys.S) && !hasStarted && !isBuying) {
                // start wave only if player is not buying
                status.add("Wave In Progress");
                hasStarted = true;
                wave.setIsFinished(false);
            }
            if (input.wasPressed(MouseButtons.LEFT) && !isBuying){
                String towerSelected = buyPanel.getTowerType(input);
                // if tower was selected then initialise the process to buy a tower
                if (!towerSelected.isEmpty()){
                    buyTower = BuyTower.getInstance(towerSelected,map);
                    isBuying = true;
                    status.add("Placing");
                }
            }else if (input.wasPressed(MouseButtons.LEFT) && isBuying){
                // buy the selected tower if the position is legal
                if (buyTower.buy(input)){
                    // change the status if tower was bought
                    status.remove(status.size()-1);
                    isBuying = false;
                }
            }else if (input.wasPressed(MouseButtons.RIGHT) && isBuying){
                // cancel buying
                status.remove(status.size()-1);
                isBuying = false;
            }
        }

        // If in the process of buying
        if (isBuying){
            buyTower.followMouse(input);
        }

        // process wave event
        wave.progressWave();

        // Update all towers, attack enemies if possible. Remove if they are finished
        for (int i=towers.size()-1;i>=0;i--){
            Tower t = towers.get(i);
            t.render(input);
            t.attack(input);
            if (t.getHasFinished()){
                towers.remove(i);
            }
        }

        // Update all slicers, and remove them if they've finished
        for (int i=slicers.size()-1;i>=0;i--){
            Slicer slicer = slicers.get(i);
            slicer.update(input);
            // if slicer reaches the end, incur a penalty
            if (slicer.getIsFinished()){
                lives -= slicer.getPenalty();
                // exit game if no lives left
                if (lives<=0){
                    gameHasEnded = true;
                    Window.close();
                    break;
                }
                slicers.remove(i);
            }else if (slicer.getHealth()<=0){
                // if slicer was eliminated by tower, give a reward
                slicer.spawnOnDeath();
                slicers.remove(i);
                addMoney(slicer.getReward());
            }
            // if current wave event has finished, stop game and wait for key press to start next wave
            if (wave.getIsFinished() && slicers.isEmpty()){
                // award player with money at end of each wave event
                ShadowDefend.addMoney(ENDWAVE_REWARD+waveNum*ENDWAVE_REWARD_SCALE);
                hasStarted = false;
                // if player is still in the process of buying, don't remove that status
                if (isBuying){
                    status.remove(status.size()-2);
                }else{
                    status.remove(status.size()-1);
                }
                // Reset game if entire wave is finished
                if (wave.getIsWaveFinished()){
                    resetGame();
                }
            }
        }
        // Render the panels last so that go in front of everything else
        buyPanel.renderPanel();
        statusPanel.renderPanel();
    }
}
