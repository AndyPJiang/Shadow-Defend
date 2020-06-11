import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

public class BuyPanel implements Panel{
    private static BuyPanel buyPanel = null;
    private final Image background;
    private final Image tankImg;
    private final Image superTankImg;
    private final Image airplaneImg;
    private final DrawOptions drawOptions = new DrawOptions();

    private final double center;
    private final double width;

    private final int OFFSET_X = 64;
    private final int OFFSET_Y = 10;
    private final int OFFSET_KEYBIND = 25;
    private final int OFFSET_MONEY = 200;
    private final int OFFSET_Y_MONEY = 65;
    private final int GAP = 120;

    private final Point tankCenter;
    private final Point superTankCenter;
    private final Point airplaneCenter;

    private final int tankPrice;
    private final int superTankPrice;
    private final int airplanePrice;


    private BuyPanel(){
        String imgPath = ShadowDefend.getImgPath();
        this.background = new Image(imgPath+"buypanel.png");
        this.tankImg = new Image(imgPath+"tank.png");
        this.superTankImg = new Image(imgPath+"supertank.png");
        this.airplaneImg = new Image(imgPath+"airsupport.png");
        center = background.getHeight()/2;
        width = background.getWidth();
        tankCenter = new Point(OFFSET_X,center-OFFSET_Y);
        superTankCenter = new Point(OFFSET_X+GAP,center-OFFSET_Y);
        airplaneCenter= new Point(OFFSET_X + 2 * GAP,center- OFFSET_Y);
        this.tankPrice = ShadowDefend.getTankPrice();
        this.superTankPrice = ShadowDefend.getSuperTankPrice();
        this.airplanePrice = ShadowDefend.getAirplanePrice();
    }

    public void renderPanel(){
        String fontPath = ShadowDefend.getFontPath();
        Font font = new Font(fontPath+"DejaVuSans-Bold.ttf",mediumSize);

        double tankWidth = tankImg.getWidth();
        double superTankWidth = superTankImg.getWidth();
        double airplaneWidth = airplaneImg.getWidth();

        background.drawFromTopLeft(0,0);
        tankImg.draw(tankCenter.x,tankCenter.y);
        superTankImg.draw(superTankCenter.x,superTankCenter.y);
        airplaneImg.draw(airplaneCenter.x,airplaneCenter.y);

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

        Font fontKeyBind = new Font(fontPath+"DejaVuSans-Bold.ttf",smallSize);
        fontKeyBind.drawString(
                "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale",
                width/2-fontKeyBind.getWidth("Key binds"), center-OFFSET_KEYBIND);


        Font fontMoney = new Font(fontPath+"DejaVuSans-Bold.ttf",largeSize);
        fontMoney.drawString("$"+ShadowDefend.getMoney(),
                width-OFFSET_MONEY, OFFSET_Y_MONEY);
    }


    public String getTowerType(Input input){
        Point p = input.getMousePosition();
        Rectangle rect = tankImg.getBoundingBoxAt(tankCenter);
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
        return "";
    }

    public Image getBackground() {
        return background;
    }

    public static BuyPanel getInstance(){
        if (buyPanel == null){
            buyPanel = new BuyPanel();
        }
        return buyPanel;
    }
}
