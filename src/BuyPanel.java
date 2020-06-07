import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

public class BuyPanel{
    private static BuyPanel buyPanel = null;
    private final Image background;
    private final Image tankImg;
    private final Image superTankImg;
    private final Image airplaneImg;
    private final DrawOptions drawOptions = new DrawOptions();

    private final double center;
    private final double width;

    private final int offsetX = 64;
    private final int offsetY = 10;
    private final int offsetKeyBind = 25;
    private final int offsetMoney = 200;
    private final int offsetYMoney = 65;
    private final int gap = 120;

    private final Point tankCenter;
    private final Point superTankCenter;
    private final Point airplaneCenter;

    private final int tankPrice;
    private final int superTankPrice;
    private final int airplanePrice;


    private BuyPanel(){
        this.background = new Image("res/images/buypanel.png");
        this.tankImg = new Image("res/images/tank.png");
        this.superTankImg = new Image("res/images/supertank.png");
        this.airplaneImg = new Image("res/images/airsupport.png");
        center = background.getHeight()/2;
        width = background.getWidth();
        tankCenter = new Point(offsetX,center-offsetY);
        superTankCenter = new Point(offsetX+gap,center-offsetY);
        airplaneCenter= new Point(offsetX + 2 * gap,center- offsetY);
        this.tankPrice = ShadowDefend.getTankPrice();
        this.superTankPrice = ShadowDefend.getSuperTankPrice();
        this.airplanePrice = ShadowDefend.getAirplanePrice();
    }

    public void renderPanel(){
        Font font = new Font("res/fonts/DejaVuSans-Bold.ttf",25);

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

        Font fontKeyBind = new Font("res/fonts/DejaVuSans-Bold.ttf",15);
        fontKeyBind.drawString(
                "Key binds:\n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale",
                width/2-fontKeyBind.getWidth("Key binds"), center-offsetKeyBind);


        Font fontMoney = new Font("res/fonts/DejaVuSans-Bold.ttf",50);
        fontMoney.drawString("$"+ShadowDefend.getMoney(),
                width-offsetMoney, offsetYMoney);
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
