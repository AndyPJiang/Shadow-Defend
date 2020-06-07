import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.map.TiledMap;
import bagel.util.Point;
import java.util.List;

public class BuyTower {
    private static BuyTower buyTower = null;
    private String selectedTower;
    private Image towerImage;
    private TiledMap map;

    private BuyTower(String selectedTower, TiledMap map) {
        this.selectedTower = selectedTower;
        this.map = map;
        this.towerImage = new Image("res/images/" + selectedTower + ".png");
    }

    public static BuyTower getInstance(String selectedTower, TiledMap map) {
        if (buyTower == null) {
            buyTower = new BuyTower(selectedTower, map);
        } else {
            buyTower.selectedTower = selectedTower;
            buyTower.towerImage = new Image("res/images/" + selectedTower + ".png");
            buyTower.map = map;
        }
        return buyTower;
    }

    public String getSelectedTower() {
        return selectedTower;
    }

    public boolean canPlace(Point p) {
        // make sure cursor is still in range of the window
        if (p.x < 0 || p.x > Window.getWidth() || p.y < 0 || p.x > Window.getHeight()) {
            return false;
        }

        boolean isBlocked = false, isIntersecting = false;
        BuyPanel buyPanel = BuyPanel.getInstance();
        StatusPanel statusPanel = StatusPanel.getInstance();

        isBlocked = map.getPropertyBoolean((int) p.x, (int) p.y, "blocked", false);

        List<Tower> towers = ShadowDefend.getTowers();
        for (Tower t : towers) {
            if (t.getRect().intersects(p)) {
                isIntersecting = true;
            }
        }
        if (p.y <= buyPanel.getBackground().getHeight()) {
            isIntersecting = true;
        }
        if (p.y >= Window.getHeight() - statusPanel.getBackground().getHeight()) {
            isIntersecting = true;
        }
        return !(isBlocked || isIntersecting);
    }


    public void followMouse(Input input) {
        Point p = input.getMousePosition();
        if (canPlace(p)) {
            towerImage.draw(p.x, p.y);
        }
    }

    public boolean buy(Input input) {
        Point pos = input.getMousePosition();
        if (buyTower.canPlace(input.getMousePosition())) {
            ShadowDefend.addTower(getSelectedTower(), pos);
            return true;
        }
        return false;
    }
}