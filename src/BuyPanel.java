import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;


/**
 * The panel that displays the towers available for purchase,
 *  a list of key binds, and the money the player currently has
 */

public class BuyPanel implements Panel{
    // class instance
    private static BuyPanel buyPanel = null;

    private final DrawOptions drawOptions = new DrawOptions();
    // images of the towers and panel background
    private final Image background;
    private final Image tankImg;
    private final Image superTankImg;
    private final Image airplaneImg;

    private final double center;
    private final double width;

    // some parameters for rendering
    private final int OFFSET_X = 64;
    private final int OFFSET_Y = 10;
    private final int OFFSET_KEYBIND = 25;
    private final int OFFSET_MONEY = 200;
    private final int OFFSET_Y_MONEY = 65;
    private final int GAP = 120;

    // position where these tower images will be rendered
    private final Point tankCenter;
    private final Point superTankCenter;
    private final Point airplaneCenter;

    private final int tankPrice;
    private final int superTankPrice;
    private final int airplanePrice;


    /**
     * Creates a new buy panel. Visibility is private as there will only be
     * one instance of this class
     */
    private BuyPanel(){
        String imgPath = ShadowDefend.getImgPath();
        this.background = new Image(imgPath+"buypanel.png");
        this.tankImg = new Image(imgPath+"tank.png");
        this.superTankImg = new Image(imgPath+"supertank.png");
        this.airplaneImg = new Image(imgPath+"airsupport.png");
        center = background.getHeight()/2;
        width = background.getWidth();

        // calculate the centers of each tower image
        tankCenter = new Point(OFFSET_X,center-OFFSET_Y);
        superTankCenter = new Point(OFFSET_X+GAP,center-OFFSET_Y);
        airplaneCenter= new Point(OFFSET_X + 2 * GAP,center- OFFSET_Y);
        this.tankPrice = Tank.getPrice();
        this.superTankPrice = SuperTank.getPrice();
        this.airplanePrice = Airplane.getPrice();
    }


    /**
     * Render the panel
     */
    public void renderPanel(){
        String fontPath = ShadowDefend.getFontPath();
        String fontFile = "DejaVuSans-Bold.ttf";
        Font font = new Font(fontPath+fontFile,mediumFont);

        double tankWidth = tankImg.getWidth();
        double superTankWidth = superTankImg.getWidth();
        double airplaneWidth = airplaneImg.getWidth();

        // draw the background
        background.drawFromTopLeft(0,0);
        // draw the tower images
        tankImg.draw(tankCenter.x,tankCenter.y);
        superTankImg.draw(superTankCenter.x,superTankCenter.y);
        airplaneImg.draw(airplaneCenter.x,airplaneCenter.y);

        // draw the prices of each tower underneath the tower image, colour if green if
        // players has enough money to purchase it, red otherwise
        if (ShadowDefend.getMoney()>= tankPrice){
            font.drawString("$"+tankPrice, tankCenter.x -tankWidth/2,
                    tankCenter.y+50, drawOptions.setBlendColour(Colour.GREEN));
        }else{
            font.drawString("$"+tankPrice, tankCenter.x -tankWidth/2,
                    tankCenter.y + 50,drawOptions.setBlendColour(Colour.RED));
        }
        if (ShadowDefend.getMoney()>= superTankPrice){
            font.drawString("$"+superTankPrice, superTankCenter.x -superTankWidth/2,
                    superTankCenter.y+50,drawOptions.setBlendColour(Colour.GREEN));
        }else{
            font.drawString("$"+superTankPrice, superTankCenter.x - superTankWidth/2,
                    superTankCenter.y+50,drawOptions.setBlendColour(Colour.RED));
        }
        if (ShadowDefend.getMoney()>= airplanePrice){
            font.drawString("$"+airplanePrice, airplaneCenter.x -airplaneWidth/2,
                    airplaneCenter.y +50,drawOptions.setBlendColour(Colour.GREEN));
        }else{
            font.drawString("$"+airplanePrice, airplaneCenter.x -airplaneWidth /2,
                    airplaneCenter.y+50,drawOptions.setBlendColour(Colour.RED));
        }

        // draw the key binds
        Font fontKeyBind = new Font(fontPath+fontFile,smallFont);
        fontKeyBind.drawString(
                "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale",
                width/2-fontKeyBind.getWidth("Key binds"), center-OFFSET_KEYBIND);


        // draw the money that the player has
        Font fontMoney = new Font(fontPath+fontFile,largeFont);
        fontMoney.drawString("$"+ShadowDefend.getMoney(),
                width-OFFSET_MONEY, OFFSET_Y_MONEY);
    }



    /**
     * Gets the type of tower that the user clicked on to purchase, if the player
     * has enough money to buy it
     * @param input The current mouse/keyboard state
     * @return returns the tower type as a string if one was clicked, otherwise an empty string
     */
    public String getTowerType(Input input){
        Point p = input.getMousePosition();
        Rectangle rect = tankImg.getBoundingBoxAt(tankCenter);
        // find which tower the player has clicked. Return the tower type if player has
        // enough money to buy it
        if (rect.intersects(p)){
            return ShadowDefend.getMoney()>=tankPrice ? "tank" : "" ;
        }
        rect = superTankImg.getBoundingBoxAt(superTankCenter);
        if (rect.intersects(p)){
            return ShadowDefend.getMoney()>=superTankPrice ? "supertank" : "";
        }
        rect = airplaneImg.getBoundingBoxAt(airplaneCenter);
        if (rect.intersects(p)){
            return ShadowDefend.getMoney()>=airplanePrice ? "airsupport" : "";
        }
        // return empty string if no tower was clicked
        return "";
    }

    /**
     * @return the background image of the buy panel
     */
    public Image getBackground() {
        return background;
    }

    /**
     * @return an instance of the BuyPanel class. If an instance was created already, return it,
     * otherwise create a new one.
     */
    public static BuyPanel getInstance(){
        if (buyPanel == null){
            buyPanel = new BuyPanel();
        }
        return buyPanel;
    }
}
