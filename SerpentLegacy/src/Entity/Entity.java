package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Entity {



    public String name;
    public int positionX, positionY;
    public Rectangle solidArea, attackHitbox;
    public boolean isHitboxVisible = false;

    public int speed;
    public int damage;       // Dano que a entidade pode causar
    public int health;       // Saúde da entidade

    public BufferedImage upStandard, up1, up2, downStandard, down1, down2, leftStandard, left1, rightStandard, right1;
    public BufferedImage  atackLeft1, atackLeft2, atackLeft3, atackLeft4, atackRight1, atackRight2, atackRight3, atackRight4, atackUP1, atackUP2, atackUP3, atackUP4, atackDown1, atackDown2, atackDown3, atackDown4;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Construtor
    public Entity(String name, int positionX, int positionY, int speed, int damage, int health) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.speed = speed;
        this.damage = damage;
        this.health = health;
    }



    public BufferedImage getSprite(String imagePath) {
        BufferedImage image = null;
        try {
            // Carrega a imagem do caminho fornecido
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // Método auxiliar para ajuste da posição
    public void setAdjustedPosition(int adjustX, int adjustY) {
        this.positionX += adjustX;
        this.positionY += adjustY;
    }


    // Métodos adicionais (se necessário)
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;

    }
    public void attack(Entity target) {
        target.takeDamage(damage);
    }
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getCorrectPositionX(int positionX, int valueCorrect) {
        return positionX + valueCorrect;
    }

    public int getCorrectPositionY(int positionY, int valueCorrect){
        return positionY + valueCorrect;
    }
}