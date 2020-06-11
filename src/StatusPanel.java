import bagel.*;
import bagel.util.Colour;

public class StatusPanel implements Panel{

    private static StatusPanel statusPanel;
    private final Image background;
    private final DrawOptions drawOptions = new DrawOptions();

    private StatusPanel(){
        this.background = new Image(ShadowDefend.getImgPath()+"statuspanel.png");
    }

    public void renderPanel(){
        Font font = new Font(ShadowDefend.getFontPath()+"DejaVuSans-Bold.ttf",smallSize);

        double windowHeight = ShadowDefend.getHeight();
        double height = background.getHeight();
        double width = background.getWidth();
        double offsetY = Window.getHeight()-background.getHeight()/3;
        double offsetX = 20;

        int waveNum = ShadowDefend.getWaveNum();
        double timescale = ShadowDefend.getTimescale();
        String status = ShadowDefend.getStatus();
        int lives = ShadowDefend.getLives();

        background.drawFromTopLeft(0,windowHeight-height);

        font.drawString("Wave: "+waveNum,offsetX,windowHeight-height/3);

        if (timescale > 1){
            font.drawString("Timescale: "+timescale, offsetX+width/5,offsetY,
                    drawOptions.setBlendColour(Colour.GREEN));
        }else{
            font.drawString("Timescale: "+timescale, offsetX+width/5,offsetY);
        }

        font.drawString("Status: "+status,
                offsetX+width/2-font.getWidth("Status: "+status)/2,offsetY);

        font.drawString("Lives: "+lives,
                width-font.getWidth("Lives: "+lives)-offsetX, offsetY);
    }


    public Image getBackground() {
        return background;
    }

    public static StatusPanel getInstance(){
        if (statusPanel == null){
            statusPanel = new StatusPanel();
        }
        return statusPanel;
    }
}
