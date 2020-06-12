import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;
import java.util.List;

/**
 * Support operations to buy a new defence tower
 */

public class BuyTower {
    // class instance
    private static BuyTower buyTower = null;
    private String selectedTower;
    private Image towerImage;
    private TiledMap map;

    /**
     * Create a new BuyTower. Visibility is private as there will only be
     * one instance of this class
     * @param selectedTower the tower that was selected by player to buy
     * @param map map of the game
     */
    private BuyTower(String selectedTower, TiledMap map) {
        this.selectedTower = selectedTower;
        this.map = map;
        this.towerImage = new Image(ShadowDefend.getImgPath() + selectedTower + ".png");
    }


    /**
     * @param selectedTower the tower selected by the player as a string
     * @param map the game map, used to check for blocked tiles
     * @return an instance of the BuyTower class. If an instance was created already, return it,
     * otherwise create a new one.
     */
    public static BuyTower getInstance(String selectedTower, TiledMap map) {
        if (buyTower == null) {
            // create a new instance
            buyTower = new BuyTower(selectedTower, map);
        } else {
            // reset the instance variables
            buyTower.selectedTower = selectedTower;
            buyTower.towerImage = new Image(ShadowDefend.getImgPath() + selectedTower + ".png");
            buyTower.map = map;
        }
        return buyTower;
    }


    /**
     * @return the tower that player is buying as a string
     */
    public String getSelectedTower() {
        return selectedTower;
    }

    /**
     * @param p the tower position
     * @return if the tower can be placed at the current position. Can only be placed
     * if it doesn't intersect with other towers and the panels, and if the position is
     * not blocked
     */
    public boolean canPlace(Point p) {
        // make sure cursor is still in range of the window, so program doesn't crash
        if (p.x < 0 || p.x >= Window.getWidth() || p.y < 0 || p.y >= Window.getHeight()) {
            return false;
        }
        boolean isBlocked;
        boolean isIntersecting = false;
        BuyPanel buyPanel = BuyPanel.getInstance();
        StatusPanel statusPanel = StatusPanel.getInstance();

        // check if current position is a blocked tile
        isBlocked = map.getPropertyBoolean((int) p.x, (int) p.y, "blocked", false);

        // check if position overlaps with another tower
        List<Tower> towers = ShadowDefend.getTowers();
        for (Tower t : towers) {
            if (t.getRect().intersects(p)) {
                isIntersecting = true;
            }
        }

        // check if position overlaps with the panels
        if (p.y <= buyPanel.getBackground().getHeight()) {
            isIntersecting = true;
        }
        if (p.y >= Window.getHeight() - statusPanel.getBackground().getHeight()) {
            isIntersecting = true;
        }

        return !(isBlocked || isIntersecting);
    }


    /**
     * Renders the image of the tower at the player's current mouse position
     * @param input The current mouse/keyboard state
     */
    public void followMouse(Input input) {
        Point p = input.getMousePosition();
        // only draw an image of the tower when hovering over a position where the tower can be placed
        if (canPlace(p)) {
            towerImage.draw(p.x, p.y);
        }
    }

    /**
     * @param input The current mouse/keyboard state
     * @return whether player placed a tower at the current mouse position
     */
    public boolean buy(Input input) {
        Point pos = input.getMousePosition();
        // if able to place the tower at the current mouse position, buy the tower
        if (canPlace(input.getMousePosition())) {
            ShadowDefend.addTower(getSelectedTower(), pos);
            return true;
        }
        return false;
    }
}