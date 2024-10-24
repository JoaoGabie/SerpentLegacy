package Objects;

import Main.GamePanel;


import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;

    public void draw(Graphics2D g2, GamePanel gp) {
        if (gp.positionX >= 0 && gp.positionX < gp.limitWidth && gp.positionY >= 0 && gp.positionY < gp.limitHeight) {
            g2.drawImage(image, gp.positionX, gp.positionY, gp.tileSize, gp.tileSize, null);
        }
    }
}