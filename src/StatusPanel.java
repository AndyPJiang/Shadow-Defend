import bagel.*;
import bagel.util.Colour;

/**
 * The panel that displays the state of the game
 */

public class StatusPanel implements Panel{

    private static StatusPanel statusPanel;
    private final Image background;
    private final DrawOptions drawOptions = new DrawOptions();

    /**
     * Creates a new status panel. Visibility is private as there will only be
     * one instance of this class
     */
    private StatusPanel(){
        this.background = new Image(ShadowDefend.getImgPath()+"statuspanel.png");
    }

    /**
     * Render the panel
     */
    public void renderPanel(){
        Font font = new Font(ShadowDefend.getFontPath()+"DejaVuSans-Bold.ttf",smallFont);

        double windowHeight = ShadowDefend.getHeight();
        double height = background.getHeight();
        double width = background.getWidth();
        double offsetY = Window.getHeight()-background.getHeight()/3;
        double offsetX = 20;

        int waveNum = ShadowDefend.getWaveNum();
        double timescale = ShadowDefend.getTimescale();
        String status = ShadowDefend.getStatus();
        int lives = ShadowDefend.getLives();

        // draw the background image
        background.drawFromTopLeft(0,windowHeight-height);


        // draw the wave number
        font.drawString("Wave: "+waveNum,offsetX,windowHeight-height/3);

        // draw timescale green if it is greater than 1, white if it is 1
        if (timescale > 1){
            font.drawString("Timescale: "+timescale, offsetX+width/5,offsetY,
                    drawOptions.setBlendColour(Colour.GREEN));
        }else{
            font.drawString("Timescale: "+timescale, offsetX+width/5,offsetY);
        }

        // draw the game status
        font.drawString("Status: "+status,
                offsetX+width/2-font.getWidth("Status: "+status)/2,offsetY);

        // draw the lives left
        font.drawString("Lives: "+lives,
                width-font.getWidth("Lives: "+lives)-offsetX, offsetY);
    }


    /**
     * @return the background image of the status panel
     */
    public Image getBackground() {
        return background;
    }

    /**
     * @return an instance of the StatusPanel class. If an instance was created already, return it,
     * otherwise create a new one.
     */
    public static StatusPanel getInstance(){
        if (statusPanel == null){
            statusPanel = new StatusPanel();
        }
        return statusPanel;
    }
}
