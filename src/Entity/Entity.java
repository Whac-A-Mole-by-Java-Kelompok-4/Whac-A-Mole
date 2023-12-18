package Entity;
import Tools.ImageHandler;
import Tools.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity implements ImageHandler {
    private int xKoordinat,yKoordinat;
    private int speed;
    private int entitySize;

    Entity(int x, int y, int speed){
        this.xKoordinat = x;
        this.yKoordinat = y;
        this.speed = speed;
        this.entitySize=96;
    }
    @Override
    public BufferedImage setup(String path){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(path));
            image = UtilityTool.scaleImage(image, getEntitySize(), getEntitySize());
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public int getXKoordinat() {
        return xKoordinat;
    }
    public int getYKoordinat() {
        return yKoordinat;
    }
    public int getSpeed() {
        return speed;
    }
    public int getEntitySize() {
        return entitySize;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }





}
